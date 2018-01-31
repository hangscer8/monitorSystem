package nathan.actor

import akka.actor.{Actor, ActorRef, ReceiveTimeout}
import nathan.ec.ExecutorService.daoActor
import nathan.monitorSystem.Protocols._
import nathan.actor.Protocol._
import nathan.monitorSystem.AkkaSystemConst

import scala.concurrent.duration._

class PeerToAgentActor extends Actor with AkkaSystemConst{
  override def receive: Receive = {
    case (`agentActorJoined`, agent: ActorRef, agentMachine: AgentMachineEntity) =>
      agent ! (peerToAgentActor, self)
      context.setReceiveTimeout(2.2 seconds)
      context.become(running(agent, agentMachine.agentId))
  }

  def running(agent: ActorRef, agentId: String): Receive = {
    case any: BaseAgentInfo => any match {
      case cPUPercEntity: CPUPercEntity =>
        daoActor ! StoreCPUPercAction(cPUPercEntity)
      case memEntity: MEMEntity =>
        daoActor ! StoreMEMEntityAction(memEntity)
      case swapEntity: SWAPEntity =>
        daoActor ! StoreSWAPEntityAction(swapEntity)
      case loadAvgEntity: LoadAvgEntity =>
        daoActor ! StoreLoadAvgEntityAction(loadAvgEntity)
      case fileUsageEntity: FileUsageEntity =>
        daoActor ! StoreFileUsageEntityAction(fileUsageEntity)
      case netInfoEntity: NetInfoEntity =>
        daoActor ! StoreNetInfoEntityAction(netInfoEntity)
    }
    case ReceiveTimeout =>
      context.parent ! (agentTimeout, agentId)
      context.stop(self)
  }
}