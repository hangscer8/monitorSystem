package nathan

import nathan.ec.ExecutorServiceUtil.{getActorPath, system}
import nathan.ec.{ExecutorServiceUtil, Protocol}

import scala.concurrent.duration._
import scala.util.{Failure, Success}

object Main extends App {
  implicit val ec = ExecutorServiceUtil.ec
  val host = "127.0.0.1"
  system.actorSelection(getActorPath(Protocol.agent_system_name, host, Protocol.agent_port, Protocol.agent_actor_name)).resolveOne(3 seconds).onComplete {
    case Success(agent) =>
      agent ! "hi szy"
    case Failure(ex) =>
      ex.printStackTrace()
  }
}

//jmap -histo:live 2131
// jmap -heap 2131