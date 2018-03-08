package nathan.monitorSystem

import akka.actor.ActorRef
import nathan.monitorSystem.Protocols.AgentMachineEntity


package Protocols {

  trait BaseAgentInfo

  case class AgentMachineEntity(ip: String, akkaPort: Int, agentId: String, cpuVendor: String, model: String, sendMsgNum: Long = 0L, joinedTime: Long = System.currentTimeMillis()) extends BaseAgentInfo //CacheSize KB , Model MacBookAir7,2 , Vendor 生产商 , Mhz 频率，sendMsgNum 已经发送的指标信息的数量,joinedTime agent添加时间

  case class CPUPercEntity(user: Double, sys: Double, idle: Double, create: Long = System.currentTimeMillis(), agentId: String) extends BaseAgentInfo //user用户使用率 sys系统使用率 _wait当前等待率 idle空闲率 combined 总的使用率

  case class MEMEntity(total: Double, used: Double, create: Long = System.currentTimeMillis(), agentId: String) extends BaseAgentInfo //GB

  case class SWAPEntity(total: Double, used: Double, create: Long = System.currentTimeMillis(), agentId: String) extends BaseAgentInfo //GB

  case class LoadAvgEntity(`1min`: Double, `5min`: Double, `15min`: Double, create: Long = System.currentTimeMillis(), agentId: String) extends BaseAgentInfo //每1分钟 每5分钟 每15分钟的平均负载 0.0-1.0

  case class FileUsageEntity(total: Double, used: Double, create: Long = System.currentTimeMillis(), agentId: String) extends BaseAgentInfo //文件系统 GB

  case class NetInfoEntity(netSpeed: Long, create: Long = System.currentTimeMillis(), agentId: String) extends BaseAgentInfo //网络速度 KB/S

  case class UserEntity(username: String, password: String, lastActiveTime: Long, auth: Option[String]) //auth->authHead

  case class RegisterReq(userName: String, password: String)

  case class AddAgentReq(ip: String, port: Int)

}

object akkaAction {

  trait AkkaEventAction

  case class AgentActorJoinCenter(agentActor: ActorRef, agentMachineEntity: AgentMachineEntity) extends AkkaEventAction

}

object MsgCode {
  val success = "0000"
  val failure = "0001"
}