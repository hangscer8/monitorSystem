package nathan.actor

import nathan.monitorSystem.Protocols.AgentMachineEntity
import nathan.monitorSystem.Protocols._

object Protocol {

  trait DaoAction

  case class StoreAgentMachineAction(agentMachineEntity: AgentMachineEntity) extends DaoAction

  case class StoreCPUPercAction(cPUPercEntity: CPUPercEntity) extends DaoAction

  case class StoreMEMEntityAction(memEntity: MEMEntity) extends DaoAction

  case class StoreSWAPEntityAction(swapEntity: SWAPEntity) extends DaoAction

  case class StoreLoadAvgEntityAction(loadAvgEntity: LoadAvgEntity) extends DaoAction

  case class StoreFileUsageEntityAction(fileUsageEntity: FileUsageEntity) extends DaoAction

  case class StoreNetInfoEntityAction(netInfoEntity: NetInfoEntity) extends DaoAction

}
