import _root_.akka.actor.{ActorSystem, Props}
import es.chickade.rockhopper._
import es.chickade.rockhopper.persistence._
import org.scalatra._
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {

  val system = ActorSystem()
  val storageActor = system.actorOf(Props[StorageActor])

  override def init(context: ServletContext) {
    context.mount(new RockhopperServlet(system), "/*")
//    context.mount(new PageRetriever(system), "/*")
//    context.mount(new MyActorApp(system, myActor), "/actors/*")
  }

  override def destroy(context:ServletContext) {
    system.shutdown()
  }

}
