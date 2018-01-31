package nathan.actor

import akka.actor.{Actor, ActorRef}
import com.typesafe.config.ConfigFactory
import nathan.ec.OS
import nathan.monitorSystem.Protocols.AgentMachineEntity
import nathan.monitorSystem.akkaAction.AgentActorJoinCenter
import nathan.monitorSystem.akkaSystemConst._

import scala.concurrent.duration._

class AgentActor extends Actor {

  import context.dispatcher

  val agentId = "myAgent123"

  override def preStart(): Unit = {
    val config = ConfigFactory.load()
    val path = s"akka.tcp://$center_system_name@127.0.0.1:$center_port/user/$center_actor_name"
    context.actorSelection(path) ! AgentActorJoinCenter(self, AgentMachineEntity(config.getString("akka.remote.netty.tcp.hostname"), config.getInt("akka.remote.netty.tcp.port"), agentId))
    context.system.scheduler.schedule(1 seconds, 2 seconds, self, "CPUPerc")
    context.system.scheduler.schedule(2 seconds, 2 seconds, self, "MEM")
    context.system.scheduler.schedule(3 seconds, 2 seconds, self, "SWAP")
    context.system.scheduler.schedule(4 seconds, 2 seconds, self, "LoadAvg")
    context.system.scheduler.schedule(5 seconds, 2 seconds, self, "FileUsage")
    context.system.scheduler.schedule(6 seconds, 2 seconds, self, "NetInfo")
  }

  override def receive: Receive = {
    case (`peerToAgentActor`, peerActor: ActorRef) =>
      context.become(running(peerActor))
  }

  def running(peerActor: ActorRef): Receive = {
    case "CPUPerc" =>
      peerActor ! OS.getCPUPerc(agentId)
    case "MEM" =>
      peerActor ! OS.getMEM(agentId)
    case "SWAP" =>
      peerActor ! OS.getSwap(agentId)
    case "LoadAvg" =>
      peerActor ! OS.getLoadAvg(agentId)
    case "FileUsage" =>
      peerActor ! OS.getFileUsage(agentId)
    case "NetInfo" =>
      peerActor ! OS.getNetInfo(agentId)
  }
}