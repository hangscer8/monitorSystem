package nathan.actor

import nathan.monitorSystem.Protocols.AgentMachineEntity

object Protocol {

  trait DaoAction

  trait AgentMachineDaoAction extends DaoAction

  case class StoreAgentMachineAction(agentMachineEntity: AgentMachineEntity) extends AgentMachineDaoAction

}
