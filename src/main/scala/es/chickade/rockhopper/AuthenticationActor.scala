import akka.actor.Actor
import akka.actor.Props
import akka.event.Logging
import anorm._
import java.sql.Connection

class AuthenticationActor extends Actor {
  val log = Logging(context.system, this)
  override def preStart() {
    
  }
  def receive = {
    case "test" => log.info("received test")
    case _      => log.info("received unknown message")
  }
}
