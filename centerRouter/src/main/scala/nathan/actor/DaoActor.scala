package nathan.actor

import akka.actor.Actor
import nathan.actor.Protocol.{StoreAgentMachineAction, _}
import nathan.dbentity.EntityTable._
import nathan.dbentity.EntityTable.h2.api._
import nathan.util.FutureUtil.FutureOps

class DaoActor extends Actor with DaoActorTrait {
  override def receive: Receive = {
    case action: DaoAction =>
      withDaoAction(action)
    case _ =>
  }
}

trait DaoActorTrait {
  self: Actor =>

  def withDaoAction(action: DaoAction) = action match {
    case StoreAgentMachineAction(agentMachineEntity) =>
      db.run(agentMachines += agentMachineEntity).exec
    case StoreCPUPercAction(cPUPercEntity) =>
      db.run(cpuPercs += cPUPercEntity).exec
    case StoreMEMEntityAction(memEntity) =>
      db.run(mems += memEntity).exec
    case StoreSWAPEntityAction(swapEntity) =>
      db.run(swaps += swapEntity).exec
    case StoreLoadAvgEntityAction(loadAvgEntity) =>
      db.run(loadAvgs += loadAvgEntity).exec
    case StoreFileUsageEntityAction(fileUsageEntity) =>
      db.run(fileUsages += fileUsageEntity).exec
    case StoreNetInfoEntityAction(netInfoEntity) =>
      db.run(netInfos += netInfoEntity).exec
  }
}