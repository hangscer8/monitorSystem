package controllers.index

import javax.inject._

import play.api.libs.circe.Circe
import play.api.mvc._
import service.index.IndexServiceTrait
import util.{ActionContext}
import entity.EntityTable._
import h2.api._

@Singleton
class IndexController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Circe with IndexServiceTrait {
  def index = ActionContext.imperativelyComplete { ctx =>
    val list = db.run(agentMachines.sortBy(_.lastReceiveMsgTime.desc).result).exe.toList

    //哪些agent是不健康状态的
    val now = System.currentTimeMillis()
    val targetTime = now - 60 * 1000
    val queryAction1 = alarmEvents.filter(_.created > targetTime)
      .join(alarmRules).on(_.alarmRuleId === _.id)
      .join(agentMachines).on(_._2.agentId === _.agentId).result
    //一分钟内有告警数据则可以任务该agent不健康
    val queryAction2 = agentMachines.filter(_.isOnLine === 0).map(_.agentId).result
    //或者离线的agent
    val unhealthAgentLit1 = db.run(queryAction1).exe.groupBy(_._2).map(_._1.agentId).toList
    val unhealthAgentLit2 = db.run(queryAction2).exe.toList
    val unhealthAgentLit = (unhealthAgentLit1 ++ unhealthAgentLit2).distinct

    val someInfos = list.map { itemAgent =>
      val loadAvgOpt = db.run(loadAvgs.filter(_.agentId === itemAgent.agentId).sortBy(_.create.desc).take(1).result.headOption).exe
      val fileUsageOpt = db.run(fileUsages.filter(_.agentId === itemAgent.agentId).sortBy(_.create.desc).take(1).result.headOption).exe
      val memOpt = db.run(mems.filter(_.agentId === itemAgent.agentId).sortBy(_.create.desc).take(1).result.headOption).exe
      (itemAgent.agentId,loadAvgOpt, fileUsageOpt, memOpt)
    }
    ctx.complete(Ok(views.html.index.index("首页", list, unhealthAgentLit, someInfos)))
  }
}