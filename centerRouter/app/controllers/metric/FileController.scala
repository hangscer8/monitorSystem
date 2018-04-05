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

@Singleton
class FileController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with MetricServiceTrait with Circe with UtilTrait {
  def index() = ActionContext.imperativelyComplete { ctx =>
    ctx.complete(Ok(views.html.metric.file("文件信息")))
  }

  def fileData() = ActionContext.imperativelyComplete(circe.json[Map[String, String]]) { ctx =>
    val seq = db.run(fileSeqDBIO(ctx.request.body("agentId"), ctx.request.body("size").toInt)).exe
    ctx.complete(Ok(seq.asJson.noSpaces).as(JSON))
  }

  def fileDataTable() = ActionContext.imperativelyComplete(circe.json[Map[String, String]]) { ctx =>
    val r = db.run(fileUsages.filter(_.agentId === ctx.request.body("agentId")).sortBy(_.create.desc).result.head).exe
    ctx.complete(Ok(views.html.metric.fileTable(r)))
  }
}