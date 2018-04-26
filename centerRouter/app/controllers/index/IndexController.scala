package controllers.index

import javax.inject._
import io.circe.generic.auto._
import io.circe.syntax._
import play.api.libs.circe.Circe
import play.api.mvc._
import service.index.IndexServiceTrait
import util.ActionContext
import entity.EntityTable._
import h2.api._
import nathan.monitorSystem.Protocols.AlarmRuleType

@Singleton
class IndexController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Circe with IndexServiceTrait {
  def index = ActionContext.imperativelyComplete { ctx =>
    val list = db.run(agentMachines.sortBy(_.lastReceiveMsgTime.desc).result).exe.toList

    val unhealthAgentLit = db.run(agentMachines.map(_.agentId).result)
      .exe
      .map(agentId => (agentId, isAgentHealth(agentId)))
      .filter(_._2 == false)
      .map(_._1)
      .toList //找出哪些agent是不健康的

    val someInfos = list.map { itemAgent =>
      val cpuPercOpt = db.run(cpuPercs.filter(_.agentId === itemAgent.agentId).sortBy(_.create.desc).take(1).result.headOption).exe
      val loadAvgOpt = db.run(loadAvgs.filter(_.agentId === itemAgent.agentId).sortBy(_.create.desc).take(1).result.headOption).exe
      val fileUsageOpt = db.run(fileUsages.filter(_.agentId === itemAgent.agentId).sortBy(_.create.desc).take(1).result.headOption).exe
      val memOpt = db.run(mems.filter(_.agentId === itemAgent.agentId).sortBy(_.create.desc).take(1).result.headOption).exe
      (itemAgent.agentId, loadAvgOpt, fileUsageOpt, memOpt, cpuPercOpt)
    }
    ctx.complete(Ok(views.html.index.index("首页", list, unhealthAgentLit, someInfos)))
  }

  def agentHealthStatus(agentId: String) = Action {
    isAgentHealth(agentId) match {
      case true => Ok(Map("code" -> "0000").asJson.noSpaces).as(JSON) //健康
      case false => Ok(Map("code" -> "0001").asJson.noSpaces).as(JSON) //不健康
    }
  }

  def cpuHealthStatus(agentId: String) = Action {
    //10秒钟之内存在告警数据
    val now = System.currentTimeMillis()
    val targetTime = now - 10 * 1000
    //10秒前
    val queryAction = alarmEvents.filter(_.created > targetTime).join(alarmRules).on(_.alarmRuleId === _.id).filter(_._2.`type` === AlarmRuleType.`cpu总占用率告警`)
      .join(agentMachines).on(_._2.agentId === _.agentId).filter(_._2.agentId === agentId)
      .exists.result
    val cpuPersListOne = db.run(cpuPercs.sortBy(_.create.desc).take(1).result).exe.toList
    val isHealth = !db.run(queryAction).exe //存在相关告警信息则是不健康的
    Ok(Map("code" -> (if (isHealth) "0000" else "0001"), "entity" -> cpuPersListOne.asJson.noSpaces).asJson.noSpaces).as(JSON)
  }

  def sysLoadHealthStatus(agentId: String) = Action {
    //10秒钟之内存在告警数据
    val now = System.currentTimeMillis()
    val targetTime = now - 10 * 1000
    //10秒前
    val queryAction = alarmEvents.filter(_.created > targetTime).join(alarmRules).on(_.alarmRuleId === _.id).filter(_._2.`type` === AlarmRuleType.`系统负载告警(1min)`)
      .join(agentMachines).on(_._2.agentId === _.agentId).filter(_._2.agentId === agentId)
      .exists.result
    val agentMachineEntity = db.run(agentMachines.filter(_.agentId === agentId).result.head).exe
    val loadAvgListOne = db.run(loadAvgs.sortBy(_.create.desc).take(1).result).exe.toList
    val isHealth = !db.run(queryAction).exe //存在相关告警信息则是不健康的
    Ok(Map("code" -> (if (isHealth) "0000" else "0001"), "coresPerCpu" -> agentMachineEntity.coresPerCpu.toString, "entity" -> loadAvgListOne.asJson.noSpaces).asJson.noSpaces).as(JSON)
  }

  def memStatus(agentId: String) = Action {
    //10秒钟之内存在告警数据
    val now = System.currentTimeMillis()
    val targetTime = now - 10 * 1000
    //10秒前
    val queryAction = alarmEvents.filter(_.created > targetTime).join(alarmRules).on(_.alarmRuleId === _.id).filter(_._2.`type` === AlarmRuleType.`内存使用告警`)
      .join(agentMachines).on(_._2.agentId === _.agentId).filter(_._2.agentId === agentId)
      .exists.result
    val mEmsListOne = db.run(mems.sortBy(_.create.desc).take(1).result).exe.toList
    val isHealth = !db.run(queryAction).exe //存在相关告警信息则是不健康的
    Ok(Map("code" -> (if (isHealth) "0000" else "0001"), "entity" -> mEmsListOne.asJson.noSpaces).asJson.noSpaces).as(JSON)
  }

  def getlastHeartBeat(agentId: String) = Action {
    val beatTime = db.run(agentMachines.filter(_.agentId === agentId).map(_.lastReceiveMsgTime).result).exe
    Ok(Map("code" -> "0000", "entity" -> beatTime.asJson.noSpaces).asJson.noSpaces).as(JSON)
  }
}