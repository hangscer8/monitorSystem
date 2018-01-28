package nathan

import java.io.{File, FileOutputStream}
import java.nio.file.Paths

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import nathan.actor.AgentActor
import nathan.monitorSystem.akkaSystemConst._
import nathan.sigarUtil.SigarUtils.isWinos
import org.hyperic.sigar.Sigar
import nathan.sigarUtil.SigarUtils._

object Main extends App {
  val system = ActorSystem(agent_system_name, ConfigFactory.load().withFallback(ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$agent_port")))
  system.actorOf(Props(clazz = classOf[AgentActor]), agent_actor_name)
  
}