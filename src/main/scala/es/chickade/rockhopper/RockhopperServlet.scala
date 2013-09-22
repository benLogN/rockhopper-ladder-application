package es.chickade.rockhopper

import org.scalatra._
import scalate.ScalateSupport

class RockhopperServlet extends RockhopperLadderApplicationStack {

  get("/") {
    <p>You sent me nothing!</p>
  }
  get("/:param") {
    <p>You sent me {params("param")}</p>
  }
  
}
