package nathan.service.index

import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax._
import nathan.monitorSystem.Protocols._
import nathan.util.CommonUtilTrait
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.ext.Ajax.InputData

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js
import scala.scalajs.js.annotation._
import org.scalajs.dom._
import org.scalajs.dom.html.Div

import scala.concurrent.Future

@JSExport
object IndexService extends CommonUtilTrait {
  @JSExport
  def indexService() = {
  }

  @JSExport
  def drawPipeChart(agentId: String, divId: String): Unit = {
    val pipeChart = js.Dynamic.global.pipeChart.as[js.Function4[String, String, String, String, Unit]]

    Ajax.post(url = "/cpu", data = InputData.str2ajax(scala.collection.immutable.Map("agentId" -> agentId, "size" -> "1").asJson.noSpaces), headers = Map(`Content-Type` -> `application/json`))
      .map(r => decode[Seq[CPUPercEntity]](r.responseText).right.get)
      .map { resultList =>
        val data = resultList.nonEmpty match {
          case true =>
            val cPuPercEntity = resultList.head
            s"""
               |[
               | ["user",${cPuPercEntity.user}],
               | ["sys",${cPuPercEntity.sys}],
               | ["_wait",${cPuPercEntity._wait}],
               | ["idle",${cPuPercEntity.idle}]
               |]
            """.stripMargin
          case false =>
            s"""
               |[["无数据",1]]
             """.stripMargin
        }
        pipeChart(divId, data, "cpu占用比率", "占比")
      }
  }
}