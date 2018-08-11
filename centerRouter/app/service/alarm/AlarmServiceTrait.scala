package service.alarm

import entity.EntityTable._
import entity.EntityTable.h2.api._
import nathan.monitorSystem.Protocols.{AlarmRuleCondition, AlarmRuleEntity, AlarmRuleType}
import util.ExecutorService._
import util.UtilTrait

trait AlarmServiceTrait extends UtilTrait {
  def createAlarmRuleDBIO(rule: AlarmRuleEntity): DBIO[AlarmRuleEntity] = for {
    _ <- alarmRules += rule
  } yield rule

  def createAlarmEvent(rule: AlarmRuleEntity): (Boolean, String) = rule.`type` match {
    case AlarmRuleType.`内存使用告警` =>
      (db.run(mems.filter(_.agentId === rule.agentId).sortBy(_.create.desc).take(rule.appearTimes).result).exe, rule.condition) match {
        case (result, AlarmRuleCondition.`小于`) => (result.forall(_.used < rule.threshold), AlarmRuleType.`内存使用告警`)
        case (result, AlarmRuleCondition.`大于`) => (result.forall(_.used > rule.threshold), AlarmRuleType.`内存使用告警`)
      }
    case AlarmRuleType.`系统负载告警(15min)` =>
      (db.run(loadAvgs.filter(_.agentId === rule.agentId).sortBy(_.create.desc).take(rule.appearTimes).result).exe, rule.condition) match {
        case (result, AlarmRuleCondition.`小于`) => (result.forall(_.`15min` < rule.threshold), AlarmRuleType.`系统负载告警(15min)`)
        case (result, AlarmRuleCondition.`大于`) => (result.forall(_.`15min` > rule.threshold), AlarmRuleType.`系统负载告警(15min)`)
      }
    case AlarmRuleType.`系统负载告警(5min)` =>
      (db.run(loadAvgs.filter(_.agentId === rule.agentId).sortBy(_.create.desc).take(rule.appearTimes).result).exe, rule.condition) match {
        case (result, AlarmRuleCondition.`小于`) => (result.forall(_.`5min` < rule.threshold), AlarmRuleType.`系统负载告警(5min)`)
        case (result, AlarmRuleCondition.`大于`) => (result.forall(_.`5min` > rule.threshold), AlarmRuleType.`系统负载告警(5min)`)
      }
    case AlarmRuleType.`系统负载告警(1min)` =>
      (db.run(loadAvgs.filter(_.agentId === rule.agentId).sortBy(_.create.desc).take(rule.appearTimes).result).exe, rule.condition) match {
        case (result, AlarmRuleCondition.`小于`) => (result.forall(_.`1min` < rule.threshold), AlarmRuleType.`系统负载告警(1min)`)
        case (result, AlarmRuleCondition.`大于`) => (result.forall(_.`1min` > rule.threshold), AlarmRuleType.`系统负载告警(1min)`)
      }
    case AlarmRuleType.`cpu总占用率告警` =>
      (db.run(cpuPercs.filter(_.agentId === rule.agentId).sortBy(_.create.desc).take(rule.appearTimes).result).exe, rule.condition) match {
        case (result, AlarmRuleCondition.`小于`) => (result.forall(_.combined < rule.threshold), AlarmRuleType.`cpu总占用率告警`)
        case (result, AlarmRuleCondition.`大于`) => (result.forall(_.combined > rule.threshold), AlarmRuleType.`cpu总占用率告警`)
      }
    case AlarmRuleType.`文件系统使用告警` =>
      (db.run(fileUsages.filter(_.agentId === rule.agentId).sortBy(_.create.desc).take(rule.appearTimes).result).exe, rule.condition) match {
        case (result, AlarmRuleCondition.`小于`) => (result.forall(_.used < rule.threshold), AlarmRuleType.`文件系统使用告警`)
        case (result, AlarmRuleCondition.`大于`) => (result.forall(_.used > rule.threshold), AlarmRuleType.`文件系统使用告警`)
      }
  }
}