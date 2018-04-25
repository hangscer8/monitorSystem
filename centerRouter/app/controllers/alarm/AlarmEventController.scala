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
class AlarmEventController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Circe with AlarmServiceTrait with UtilTrait {
  def index() = ActionContext.imperativelyComplete { ctx =>
    val agentList = db.run(alarmEvents.sortBy(_.created.desc).take(15).result).exe
    ctx.complete(Ok(views.html.alarm.alarmEvent("查看告警信息", agentList)))
  }
}