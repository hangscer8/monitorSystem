package nathan

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import nathan.actor.AgentActor
import nathan.monitorSystem.AkkaSystemConst._
import oshi.SystemInfo

object AgentMain extends App {
    val system = ActorSystem(agent_system_name, ConfigFactory.load().withFallback(ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$agent_port")))
    system.actorOf(Props(clazz = classOf[AgentActor]), agent_actor_name)
//  val si = new SystemInfo()
//  val hw = si.getHardware
//  println(hw.getSensors.getFanSpeeds.mkString(",") )
}