package nathan.service.metric

import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax._
import nathan.monitorSystem.Protocols.{CPUPercEntity, UserEntity}
import nathan.util.{CommonUtilTrait, Snowflake}
import org.scalajs.dom._
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.ext.Ajax.InputData
import org.scalajs.dom.ext.Ajax.InputData._
import org.scalajs.dom.html.{Button, Form, Image, Input}

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js
import scala.scalajs.js.Date
import scala.scalajs.js.annotation._
import scala.util.{Failure, Success}

@JSExport
object MetricService extends CommonUtilTrait {
  @JSExport
  def cpuService(agentid: String): Unit = { //传入agentId 拉取数据 并展示
    val showChart = js.Dynamic.global.showChart.as[js.Function4[String, String, String, String, Unit]]
    Ajax.post(url = "cpu", data = InputData.str2ajax(Map("agentId" -> agentid, "size" -> "500").asJson.noSpaces), headers = Map(`Content-Type` -> `application/json`))
      .map(r => decode[Seq[CPUPercEntity]](r.responseText).right.get)
      .map { seq =>
        showChart("con", genSeries(List(Serie("area", "idle占比", seq.map(c => (c.create, c.idle))), Serie("area", "sys占比", seq.map(c => (c.create, c.sys))), Serie("area", "user占比", seq.map(c => (c.create, c.user))))), "CPU数据图表", "cpu性能指标")
      }
  }
}