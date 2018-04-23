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

  case class AdminEntity(username: String, password: String)

  case class RegisterReq(userName: String, password: String)

  case class AddAgentReq(ip: String, port: Int)

  case class AlarmRuleEntity(id: Long, agentId: String, title: String, `type`: String, threshold: Double, condition: String, appearTimes: Int)

  //告警规则与agent是多对一关系
  //threshold:阈值
  //condition:lt和gt
  //unit:percent或者number
  //appearTimes:连续出现次数 为1时，则每一次超过阈值都会触发告警 ，为2时，需要连续两次触发告警，才会产生一次告警
  //type:cpuAlarm memAlarm swapAlarm loadAvg1MinAlarm loadAvg5MinAlarm loadAvg15MinAlarm

  case class AlarmEventEntity(id: Long, alarmRuleId: Long, message: String, created: Long)

  object AlarmRuleType {
    val `内存使用告警` = "内存告警"
    val `系统负载告警(1min)` = "系统负载告警(1min)"
    val `系统负载告警(5min)` = "系统负载告警(5min)"
    val `系统负载告警(15min)` = "系统负载告警(15min)"
    val `cpu总占用率告警` = "cpu总占用率告警"
  }

  object AlarmRuleCondition {
    val `大于` = "大于"
    val `小于` = "小于"
  }

}

object akkaAction {

  trait AkkaEventAction

  case class AgentActorJoinCenter(agentActor: ActorRef, agentMachineEntity: AgentMachineEntity) extends AkkaEventAction

}

object MsgCode {
  val success = "0000"
  val failure = "0001"
}