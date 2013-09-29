package es.chickade.rockhopper

import org.scalatra._
import scalate.ScalateSupport
import scala.concurrent.{Await, Future, ExecutionContext}
import _root_.akka.actor.ActorSystem
import org.scalatra.FutureSupport
import scala.xml.Node

class RockhopperServlet(system: ActorSystem) extends RockhopperLadderApplicationStack with FutureSupport{

  protected implicit def executor: ExecutionContext = system.dispatcher

  get("/") {
    new AsyncResult { val is =
      Future{
        <p>You sent me nothing</p>
      }
    }
  }

  get("/:param") {
    new AsyncResult { val is =
      Future{
        <p>You sent me {params("param")}</p>
      }
    }
  }
  
}
