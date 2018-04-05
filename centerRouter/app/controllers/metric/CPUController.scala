package controllers.metric

import javax.inject.{Inject, Singleton}
import entity.EntityTable._
import io.circe.generic.auto._
import io.circe.syntax._
import play.api.libs.circe.Circe
import play.api.mvc._
import service.metric.MetricServiceTrait
import util.{ActionContext, UtilTrait}
import entity.EntityTable._
import h2.api._
import nathan.monitorSystem.Protocols._
import util.ExecutorService._
import util.UtilTrait

@Singleton
class CPUController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with MetricServiceTrait with Circe with UtilTrait {
  implicit val code = Codec.utf_8

  def index = ActionContext.imperativelyComplete { ctx =>
    ctx.complete(Ok(views.html.metric.cpu("CPU数据展示")))
  }

  def cpuData = ActionContext.imperativelyComplete(circe.json[Map[String, String]]) { ctx =>
    val seq = db.run(cpuPercSeqDBIO(ctx.request.body("agentId"), ctx.request.body("size").toInt)).exe
    ctx.complete(Ok(seq.asJson.noSpaces).as(JSON))
  }

  def cpuDataTable = ActionContext.imperativelyComplete(circe.json[Map[String, String]]) { ctx =>
    val r = db.run(cpuPercs.filter(_.agentId === ctx.request.body("agentId")).sortBy(_.create.desc).result.head).exe
    ctx.complete(Ok(views.html.metric.cpuTable(r)))
  }
}