package nathan.service.metric

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

@JSExport
object MetricService extends CommonUtilTrait {
  @JSExport
  def cpuService(agentid: String): Unit = { //传入agentId 拉取数据 并展示
    val showLineChart = js.Dynamic.global.showLineChart.as[js.Function4[String, String, String, String, Unit]]
    Ajax.post(url = "cpu", data = InputData.str2ajax(Map("agentId" -> agentid, "size" -> "500").asJson.noSpaces), headers = Map(`Content-Type` -> `application/json`))
      .map(r => decode[Seq[CPUPercEntity]](r.responseText).right.get)
      .map { seq =>
        showLineChart("con", genSeries(List(Serie("area", "idle占比", seq.map(c => (c.create, c.idle))), Serie("area", "cpu使用总占比", seq.map(c => (c.create, c.combined))), Serie("area", "sys占比", seq.map(c => (c.create, c.sys))), Serie("area", "user占比", seq.map(c => (c.create, c.user))))), "CPU数据图表", "cpu性能指标")
      }
  }

  @JSExport
  def swapService(agentid: String): Unit = {
    val showLineChart = js.Dynamic.global.showLineChart.as[js.Function4[String, String, String, String, Unit]]
    Ajax.post(url = "swap", data = InputData.str2ajax(Map("agentId" -> agentid, "size" -> "500").asJson.noSpaces), headers = Map(`Content-Type` -> `application/json`))
      .map(r => decode[Seq[SWAPEntity]](r.responseText).right.get)
      .map { seq =>
        showLineChart("con", genSeries(List(Serie("area", "swap total", seq.map(c => (c.create, c.total))), Serie("area", "swap used", seq.map(c => (c.create, c.used))))), "SWAP数据图表", "swap使用情况")
      }
  }

  @JSExport
  def memService(agentid: String): Unit = {
    val showLineChart = js.Dynamic.global.showLineChart.as[js.Function4[String, String, String, String, Unit]]
    Ajax.post(url = "mem", data = InputData.str2ajax(Map("agentId" -> agentid, "size" -> "500").asJson.noSpaces), headers = Map(`Content-Type` -> `application/json`))
      .map(r => decode[Seq[MEMEntity]](r.responseText).right.get)
      .map { seq =>
        showLineChart("con", genSeries(List(Serie("area", "mem total", seq.map(c => (c.create, c.total))), Serie("area", "mem used", seq.map(c => (c.create, c.used))))), "MEM数据图表", "MEM使用情况")
      }
  }

  @JSExport
  def loadAvgService(agentid: String): Unit = {
    val showLineChart = js.Dynamic.global.showLineChart.as[js.Function4[String, String, String, String, Unit]]
    Ajax.post(url = "loadAvg", data = InputData.str2ajax(Map("agentId" -> agentid, "size" -> "500").asJson.noSpaces), headers = Map(`Content-Type` -> `application/json`))
      .map(r => decode[Seq[LoadAvgEntity]](r.responseText).right.get)
      .map { seq =>
        showLineChart("con", genSeries(List(Serie("area", "1min", seq.map(c => (c.create, c.`1min`))), Serie("area", "5min", seq.map(c => (c.create, c.`5min`))), Serie("area", "15min", seq.map(c => (c.create, c.`15min`))))), "System Load Avg", "系统负载")
      }
  }

  @JSExport
  def fileService(agentid: String): Unit = {
    val showLineChart = js.Dynamic.global.showLineChart.as[js.Function4[String, String, String, String, Unit]]
    Ajax.post(url = "file", data = InputData.str2ajax(Map("agentId" -> agentid, "size" -> "500").asJson.noSpaces), headers = Map(`Content-Type` -> `application/json`))
      .map(r => decode[Seq[FileUsageEntity]](r.responseText).right.get)
      .map { seq =>
        showLineChart("con", genSeries(List(Serie("area", "total", seq.map(c => (c.create, c.total))), Serie("area", "used", seq.map(c => (c.create, c.used))))), "系统存储", "磁盘使用情况")
      }
  }
}