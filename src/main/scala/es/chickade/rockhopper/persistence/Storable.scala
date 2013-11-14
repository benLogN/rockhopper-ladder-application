package es.chickade.rockhopper.persistence

import es.chickade.rockhopper.persistence._
import es.chickade.rockhopper.model._
import akka.actor.Actor
import akka.actor.Props
import anorm._
import anorm.SqlParser._
import play.api.Play.current
import play.api._
import play.api.db.DB
import com.typesafe.config.{Config,ConfigFactory}
import java.io.File
import com.lambdaworks.crypto.SCryptUtil

trait Storable{
}
  
class StorageActor extends Actor {
  //TODO: Replace with non-fixed config file location
  val barebonesApplication = new SimpleApplication(new File("/etc/rockhopper.conf")) 
  lazy val scryptN = barebonesApplication.configuration.getInt("password.scrypt.n").getOrElse(16384)
  lazy val scryptR = barebonesApplication.configuration.getInt("password.scrypt.r").getOrElse(8)
  lazy val scryptP = barebonesApplication.configuration.getInt("password.scrypt.p").getOrElse(1)

  override def preStart() {
    Play.start(barebonesApplication)
  }

  override def postStop() {
    Play.stop()
  }
    
  def receive = {
    case u: User if u.userID.isEmpty => {
      DB.withConnection{implicit c =>
        SQL("""
             INSERT INTO users (email, password, displayname,username)
             VALUES ({email},{password},{displayname},{username})
            """).on(
              "email" -> u.email,
              "password" -> SCryptUtil.scrypt(u.password,scryptN,scryptR,scryptP),
              "displayname" -> u.displayName,
              "username" -> u.username
            ).executeInsert()
      }
    }
  }
}     
