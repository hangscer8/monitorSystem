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
  import context.dispatcher
  def withDaoAction(action: DaoAction) = action match {
    case StoreAgentMachineAction(agentMachineEntity) =>
      db.run(receiveMetricDBIO(agentMachines += agentMachineEntity, agentMachineEntity.agentId).asTry).exec
    case StoreCPUPercAction(cPUPercEntity) =>
      db.run(receiveMetricDBIO(cpuPercs += cPUPercEntity, cPUPercEntity.agentId).asTry).exec
    case StoreMEMEntityAction(memEntity) =>
      db.run(receiveMetricDBIO(mems += memEntity, memEntity.agentId).asTry).exec
    case StoreSWAPEntityAction(swapEntity) =>
      db.run(receiveMetricDBIO(swaps += swapEntity, swapEntity.agentId).asTry).exec
    case StoreLoadAvgEntityAction(loadAvgEntity) =>
      db.run(receiveMetricDBIO(loadAvgs += loadAvgEntity, loadAvgEntity.agentId).asTry).exec
    case StoreFileUsageEntityAction(fileUsageEntity) =>
      db.run(receiveMetricDBIO(fileUsages += fileUsageEntity, fileUsageEntity.agentId).asTry).exec
    case StoreNetInfoEntityAction(netInfoEntity) =>
      db.run(receiveMetricDBIO(netInfos += netInfoEntity, netInfoEntity.agentId).asTry).exec
  }

  def receiveMetricDBIO(dbio: DBIO[Int], agentId: String): DBIO[Int] = {
    val q = for {
      sendMsgNumOld <- agentMachines.filter(_.agentId === agentId).map(_.sendMsgNum).result.head
      i <- dbio
      _ <- agentMachines.filter(_.agentId === agentId).map(_.sendMsgNum).update(sendMsgNumOld + 1) //更新agent发送指标信息的数量
    } yield i
    q
  }
}