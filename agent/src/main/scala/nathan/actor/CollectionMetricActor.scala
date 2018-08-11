package nathan.actor

import akka.actor.{Actor, ActorLogging, ActorRef, Terminated}
import nathan.ec.OS
import nathan.monitorSystem.akkaAction.AgentActorJoinCenter

import scala.concurrent.duration._

class CollectionMetricActor extends Actor with ActorLogging {

  import context.dispatcher

  override def receive: Receive = {
    case ("peertoAgentActor", agentId: String, peertoAgentActor: ActorRef) =>
      peertoAgentActor ! AgentActorJoinCenter(self, OS.getAgentInfo(agentId))
      context.system.scheduler.schedule(1 seconds, 2 seconds, self, "CPUPerc")
      context.system.scheduler.schedule(2 seconds, 2 seconds, self, "MEM")
      context.system.scheduler.schedule(3 seconds, 2 seconds, self, "SWAP")
      context.system.scheduler.schedule(4 seconds, 2 seconds, self, "LoadAvg")
      context.system.scheduler.schedule(5 seconds, 2 seconds, self, "FileUsage")
      context.watch(peertoAgentActor) //如果与自己接头的actor挂了，也注销自己
      context.become(running(agentId, peertoAgentActor))
  }

  def running(agentId: String, peertoAgentActor: ActorRef): Receive = {
    case "CPUPerc" => peertoAgentActor ! OS.getCPUPerc(agentId)
    case "MEM" => peertoAgentActor ! OS.getMEM(agentId)
    case "SWAP" => peertoAgentActor ! OS.getSwap(agentId)
    case "LoadAvg" => peertoAgentActor ! OS.getLoadAvg(agentId)
    case "FileUsage" => peertoAgentActor ! OS.getFileUsage(agentId)
    case Terminated(`peertoAgentActor`) =>
      log.warning(s"${peertoAgentActor}挂了，本actor即将被销毁")
      context.stop(self)
  }
}