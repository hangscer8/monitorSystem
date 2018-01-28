package nathan.actor

import akka.actor.Actor
import nathan.actor.Protocol.{AgentMachineDaoAction, StoreAgentMachineAction}
import nathan.dbentity.EntityTable._
import nathan.dbentity.EntityTable.h2.api._

class DaoActor extends Actor with DaoActorTrait {
  override def receive: Receive = {
    case action: AgentMachineDaoAction =>
      withAgentMachineDaoAction(action)
    case _ =>
  }
}

trait DaoActorTrait {
  self: Actor =>

  import context.dispatcher

  def withAgentMachineDaoAction(action: AgentMachineDaoAction) = action match {
    case StoreAgentMachineAction(agentMachineEntity) =>
      val q = for {
        i <- agentMachines += agentMachineEntity
      } yield i
      db.run(q)
  }
}