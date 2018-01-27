package nathan

import java.io.File
import java.nio.file.Paths

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import nathan.actor.AgentActor
import nathan.monitorSystem.akkaSystemConst._
import nathan.sigarUtil.SigarUtils.isWinos
import org.hyperic.sigar.Sigar

object Main {
  def main(args: Array[String]): Unit = {
    //    val system = ActorSystem(agent_system_name, ConfigFactory.load().withFallback(ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$agent_port")))
    //    system.actorOf(Props(clazz = classOf[AgentActor]), agent_actor_name)
    val sigar: Sigar = {
      val a = this.getClass.getResource("/sigar").getPath
      println(a)
      val path = Paths.get(this.getClass.getResource("/sigar").getPath)
      val sigarLibDir = path.resolve("sigar")
      System.setProperty("java.library.path", System.getProperty("java.library.path") + (if (isWinos()) ";" else ":") + sigarLibDir.toString)
      new Sigar()
    }
    println(sigar.getCpu)
  }
}