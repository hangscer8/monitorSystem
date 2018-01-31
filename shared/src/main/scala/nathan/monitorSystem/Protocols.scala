package nathan.monitorSystem

import akka.actor.ActorRef
import nathan.monitorSystem.Protocols.AgentMachineEntity


package Protocols {

  trait BaseAgentInfo

  case class AgentMachineEntity(ip: String, akkaPort: Int, agentId: String) extends BaseAgentInfo

  case class CPUPercEntity(user: Double, sys: Double, _wait: Double, idle: Double, combined: Double, create: Long = System.currentTimeMillis(), agentId: String) extends BaseAgentInfo //user用户使用率 sys系统使用率 _wait当前等待率 idle空闲率 combined 总的使用率

  case class MEMEntity(total: Double, used: Double, create: Long = System.currentTimeMillis(), agentId: String) extends BaseAgentInfo //GB

  case class SWAPEntity(total: Double, used: Double, create: Long = System.currentTimeMillis(), agentId: String) extends BaseAgentInfo //GB

  case class LoadAvgEntity(`1min`: Double, `5min`: Double, `15min`: Double, create: Long = System.currentTimeMillis(), agentId: String) extends BaseAgentInfo //每1分钟 每5分钟 每15分钟的平均负载 0.0-1.0

  case class FileUsageEntity(total: Double, used: Double, create: Long = System.currentTimeMillis(), agentId: String) extends BaseAgentInfo //文件系统 GB

  case class NetInfoEntity(rxBytes: Long, txBytes: Long, create: Long = System.currentTimeMillis(), agentId: String) extends BaseAgentInfo //接收的字节数 发送的字节数

}

object akkaSystemConst {
  val center_port = 2551
  val agent_port = 2552
  val center_system_name = "centerRouterActor"
  val center_actor_name = "centerSystem"
  val dao_actor_name = "daoActor"
  val agent_system_name = "agentSystem"
  val agent_actor_name = "agentActor"
  val agentActorJoined = "agentActor"
  val peerToAgentActor = "peerToAgentActor"
  val agentTimeout = "agentTimeout"
}

object akkaAction {

  trait AkkaEventAction

  case class AgentActorJoinCenter(agentActor: ActorRef, agentMachineEntity: AgentMachineEntity) extends AkkaEventAction

}