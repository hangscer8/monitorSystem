package controllers.alarm

import javax.inject._

import entity.EntityTable._
import io.circe.generic.auto._
import io.circe.syntax._
import nathan.monitorSystem.Protocols.AlarmRuleEntity
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, ControllerComponents}
import service.alarm.AlarmServiceTrait
import util.{ActionContext, UtilTrait}
import entity.EntityTable.h2.api._

@Singleton
class AlarmRuleController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Circe with AlarmServiceTrait with UtilTrait {
  def createRule() = ActionContext.imperativelyComplete(circe.json[AlarmRuleEntity]) { ctx =>
    val r = db.run(createAlarmRuleDBIO(ctx.request.body)).exe
    ctx.complete(Ok(Map("code" -> "0000", "entity" -> r.asJson.noSpaces).asJson.noSpaces).as(JSON))
  }

  def index() = ActionContext.imperativelyComplete { ctx =>
    val agentList = db.run(agentMachines.result).exe.toList
    ctx.complete(Ok(views.html.alarm.AlarmRule("创建告警规则", agentList)))
  }

  def showAlarmRuleIndex() = Action { implicit request =>
    Ok(views.html.alarm.showAlarmRule("查看告警规则"))
  }

  def getAlarmRuleAction(agentId: String) = Action { request =>
    val seq = db.run(alarmRules.filter(_.agentId === agentId).result).exe
    Ok(Map("code" -> "0000", "entity" -> seq.asJson.noSpaces).asJson.noSpaces).as(JSON)
  }

  def deleteAlarmRuleAction(ruleId: Long) = Action { request =>
    db.run(alarmEvents.filter(_.alarmRuleId === ruleId).delete).exe
    db.run(alarmRules.filter(_.id === ruleId).delete).exe
    Ok(Map("code" -> "0000").asJson.noSpaces).as(JSON)
  }
}