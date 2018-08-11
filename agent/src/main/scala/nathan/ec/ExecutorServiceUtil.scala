package nathan.ec

import java.util.concurrent.Executors

import akka.actor.{ActorSystem, Props}
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import nathan.actor.AgentActor

import scala.concurrent.ExecutionContext

object ExecutorServiceUtil {
  implicit val system = ActorSystem(Protocol.agent_system_name,ConfigFactory.load())
  private[this] val ecFixThreadPoll = Executors.newFixedThreadPool(20)
  implicit val ec = ExecutionContext.fromExecutor(ecFixThreadPoll)
  val ma = ActorMaterializer()
  val agentActor=system.actorOf(Props(classOf[AgentActor]),Protocol.agent_actor_name)
}
