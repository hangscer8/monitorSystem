package service.alarm

import entity.EntityTable._
import entity.EntityTable.h2.api._
import nathan.monitorSystem.Protocols.AlarmRuleEntity
import util.ExecutorService._

trait AlarmServiceTrait {
  def createAlarmRuleDBIO(rule: AlarmRuleEntity): DBIO[AlarmRuleEntity] = for {
    _ <- alarmRules += rule
  } yield rule
}