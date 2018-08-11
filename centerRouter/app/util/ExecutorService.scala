package util

import java.util.concurrent.Executors

import myActor.SupervisorActor
import akka.actor.{ActorSystem, Props}
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import conf.PlayConf
import nathan.monitorSystem.AkkaSystemConst.center_system_name

import scala.concurrent.ExecutionContext

object ExecutorService {
  private val akkaConfigStr =
    s"""
       |akka {
       |  actor {
       |    provider = remote
       |  }
       |  remote {
       |    enabled-transports = ["akka.remote.netty.tcp"]
       |    netty.tcp {
       |      hostname = "10.211.55.2"
       |      port = ${PlayConf.akkaPort}
       |      message-frame-size = 30000000b
       |      send-buffer-size = 30000000b
       |      receive-buffer-size = 30000000b
       |      maximum-frame-size = 30000000b
       |    }
       |  }
       |}
     """.stripMargin
  private val akkaConfig = ConfigFactory.parseString(akkaConfigStr)
  implicit val system = ActorSystem(center_system_name, akkaConfig)
  private[this] val ecFixThreadPoll = Executors.newFixedThreadPool(20)
  implicit val ec = ExecutionContext.fromExecutor(ecFixThreadPoll)
  implicit val mat = ActorMaterializer()
  val supervisorActor = system.actorOf(Props[SupervisorActor], "supervisorActor")
}
