package nathan.actor

import akka.actor.Actor
import nathan.actor.Protocol.{StoreAgentMachineAction, _}
import nathan.dbentity.EntityTable._
import nathan.dbentity.EntityTable.h2.api._

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
      db.run(agentMachines += agentMachineEntity)
    case StoreCPUPercAction(cPUPercEntity) =>
      db.run(cpuPercs += cPUPercEntity)
    case _ =>
  }
}