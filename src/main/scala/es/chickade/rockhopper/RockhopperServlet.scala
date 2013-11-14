package es.chickade.rockhopper

import org.scalatra._
import scalate.ScalateSupport
import scala.concurrent.{Await, Future, ExecutionContext}
import _root_.akka.actor.ActorSystem
import org.scalatra.FutureSupport
import scala.xml.Node
import org.json4s.{DefaultFormats, Formats}
// Handling support for JSON
import org.scalatra.json._

class RockhopperServlet(system: ActorSystem) extends RockhopperLadderApplicationStack with FutureSupport with JacksonJsonSupport{

  protected implicit val jsonFormats: Formats = DefaultFormats.withBigDecimal
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

  post("/user/:username") {
    new AsyncResult { val is =
      Future{
        <p>{parsedBody \\ "pass"}</p>
      }
    }
  }

}
