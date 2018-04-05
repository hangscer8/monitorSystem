package nathan

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import nathan.actor.AgentActor
import nathan.monitorSystem.AkkaSystemConst._
import nathan.util.AgentConf
import oshi.SystemInfo

object AgentMain extends App {
  val system = ActorSystem(agent_system_name, AgentConf.applicationConf)
  system.actorOf(Props(clazz = classOf[AgentActor]), agent_actor_name)
}