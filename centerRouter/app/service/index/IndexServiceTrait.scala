package service.index

import util.UtilTrait
import entity.EntityTable._
import h2.api._

trait IndexServiceTrait extends UtilTrait {
  //agent是否健康
  def isAgentHealth(agentId: String): Boolean = {
    val now = System.currentTimeMillis()
    val targetTime = now - 10 * 1000
    //10秒前
    val queryAction1 = alarmEvents.filter(_.created > targetTime)
      .join(alarmRules).on(_.alarmRuleId === _.id)
      .join(agentMachines).on(_._2.agentId === _.agentId)
      .filter(_._2.agentId === agentId).result
    //一分钟内有告警数据则可以任务该agent不健康
    val queryAction2 = agentMachines.filter(_.isOnLine === 0)
      .filter(_.agentId === agentId).map(_.agentId).result
    //或者离线的agent
    val unhealthAgentLit1 = db.run(queryAction1).exe
      .groupBy(_._2).map(_._1.agentId).toList
    val unhealthAgentLit2 = db.run(queryAction2).exe.toList
    val unhealthAgentLit = (unhealthAgentLit1 ++ unhealthAgentLit2).distinct
    !unhealthAgentLit.contains(agentId) //不健康列表里不包含该agentId则为健康
  }
}