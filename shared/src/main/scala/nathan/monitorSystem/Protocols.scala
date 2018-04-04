package nathan.monitorSystem

import akka.actor.ActorRef
import nathan.monitorSystem.Protocols.AgentMachineEntity


package Protocols {

  trait BaseAgentInfo {
    val agentId: String
  }

  case class AgentMachineEntity(ip: String, akkaPort: Int, agentId: String, cpuVendor: String, model: String, sendMsgNum: Long = 0L, joinedTime: Long, lastReceiveMsgTime: Long, isOnLine: Int) extends BaseAgentInfo //CacheSize KB , Model MacBookAir7,2 , Vendor 生产商 , Mhz 频率，sendMsgNum 已经发送的指标信息的数量,joinedTime agent添加时间

  case class CPUPercEntity(id: Long, user: Double, sys: Double, _wait: Double, idle: Double, combined: Double, create: Long = System.currentTimeMillis(), agentId: String) extends BaseAgentInfo //user用户使用率 sys系统使用率 _wait当前等待率 idle空闲率 combined 总的使用率

  case class MEMEntity(id: Long, total: Double, used: Double, create: Long = System.currentTimeMillis(), agentId: String) extends BaseAgentInfo //GB

  case class SWAPEntity(id: Long, total: Double, used: Double, create: Long = System.currentTimeMillis(), agentId: String) extends BaseAgentInfo //GB

  case class LoadAvgEntity(id: Long, `1min`: Double, `5min`: Double, `15min`: Double, create: Long = System.currentTimeMillis(), agentId: String) extends BaseAgentInfo //每1分钟 每5分钟 每15分钟的平均负载 0.0-1.0

  case class FileUsageEntity(id: Long, total: Double, used: Double, create: Long = System.currentTimeMillis(), agentId: String) extends BaseAgentInfo //文件系统 GB

  case class UserEntity(id: Long, username: String, password: String, alias: String, email: String, lastActiveTime: Long) //alias别名(显示名)

  case class RegisterReq(userName: String, password: String)

  case class AddAgentReq(ip: String, port: Int)

  case class AlarmRule(id: Long, agentId: String, `type`: String, threshold: Int, unit: String, condition: String)

  //告警规则与agent是多对一关系
  //threshold:阈值
  //condition:lt和gt
  //unit:percent或者number
  //type:cpuAlarm memAlarm swapAlarm loadAvg1MinAlarm loadAvg5MinAlarm loadAvg15MinAlarm

  case class AlarmEvent(id: Long, alarmRuleId: String, message: String, eventValue: Int, unit: String)

}

object akkaAction {

  trait AkkaEventAction

  case class AgentActorJoinCenter(agentActor: ActorRef, agentMachineEntity: AgentMachineEntity) extends AkkaEventAction

}

object MsgCode {
  val success = "0000"
  val failure = "0001"
}