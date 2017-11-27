package nathan.ec

import java.util.concurrent.Executors
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import scala.concurrent.ExecutionContext

object ExecutorServiceUtil {
  val system = ActorSystem("centerRouter")
  private[this] val ecFixThreadPoll = Executors.newFixedThreadPool(20)
  implicit val ec = ExecutionContext.fromExecutor(ecFixThreadPoll)
  val ma = ActorMaterializer()
}