package nathan.service.index

import java.util.Date

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
import org.scalajs.dom.html.{Div, Span}

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

  @JSExport
  def getAgentHealthStatus(agentId: String, spanId: String): Unit = {
    window.setInterval(() => {
      Ajax.get(url = s"/agent/health?agentId=$agentId").map(r => decode[Map[String, String]](r.responseText).right.get)
        .map(result => result("code") match {
          case "0000" => document.getElementById(spanId).textContent = "健康"
          case "0001" => document.getElementById(spanId).textContent = "不健康"
        })
    }, 1000)
  }

  @JSExport
  def getCpuStatus(agentId: String, divId: String): Unit = {
    window.setInterval(() => {
      Ajax.get(url = s"/cpu/health?agentId=$agentId").map(r => decode[Map[String, String]](r.responseText).right.get)
        .map(result => result("code") match {
          case "0000" =>
            val cpuPersOPt = decode[List[CPUPercEntity]](result("entity")).right.get.headOption
            document.getElementById(divId).innerHTML =
              s"""
                 |<div class="progress-bar " role="progressbar" aria-valuemin="0" aria-valuemax="100" style="width:${cpuPersOPt.map(_.combined * 100).map(_.toInt).getOrElse(0.0)}%;" >
                 |   ${cpuPersOPt.map(_.combined * 100).map(_.toInt).getOrElse(0.0)}%
                 |</div>
             """.stripMargin
          case "0001" =>
            val cpuPersOPt = decode[List[CPUPercEntity]](result("entity")).right.get.headOption
            document.getElementById(divId).innerHTML =
              s"""
                 |<div class="progress-bar progress-bar-warning" role="progressbar" aria-valuemin="0" aria-valuemax="100" style="width:${cpuPersOPt.map(_.combined * 100).map(_.toInt).getOrElse(0.0)}%;" >
                 |   ${cpuPersOPt.map(_.combined * 100).map(_.toInt).getOrElse(0.0)}%
                 |</div>
             """.stripMargin
        })
    }, 1000)
  }

  @JSExport
  def getSystemLoadStatus(agentId: String, divId: String, `tableLoad@i`: String): Unit = {
    window.setInterval(() => {
      Ajax.get(url = s"/sysLoad/health?agentId=$agentId").map(r => decode[Map[String, String]](r.responseText).right.get)
        .map(result => result("code") match {
          case "0000" =>
            val coresPerCpu = result("coresPerCpu").toInt
            val loadAvgOPt = decode[List[LoadAvgEntity]](result("entity")).right.get.headOption
            document.getElementById(divId).innerHTML =
              s"""
                 |<div class="progress-bar" role="progressbar" aria-valuemin="0" aria-valuemax="100" style="width:${loadAvgOPt.map(l => l.`1min` / coresPerCpu * 100).getOrElse(0.0)}%;" >
                 |   ${loadAvgOPt.map(l => l.`1min` * 1.0 / coresPerCpu * 100).getOrElse(0.0)}%
                 |</div>
             """.stripMargin
            val tableStr =
              s"""
                 |<table class="table table-striped table-hover">
                 | <tr>
                 |  <td>1minute</td>
                 |  <td>5minute</td>
                 |  <td>15minute</td>
                 | </tr>
                 | <tr><td> ${loadAvgOPt.map(_.`1min`).getOrElse(0.0)} </td>
                 |     <td>${loadAvgOPt.map(_.`5min`).getOrElse(0.0)} </td>
                 |     <td> ${loadAvgOPt.map(_.`15min`).getOrElse(0.0)}</td>
                 | </tr>
                 |</table>
             """.stripMargin
            document.getElementById(`tableLoad@i`).innerHTML = tableStr
          case "0001" =>
            val coresPerCpu = result("coresPerCpu").toInt
            val loadAvgOPt = decode[List[LoadAvgEntity]](result("entity")).right.get.headOption
            document.getElementById(divId).innerHTML =
              s"""
                 |<div class="progress-bar progress-bar-warning" role="progressbar" aria-valuemin="0" aria-valuemax="100" style="width:${loadAvgOPt.map(l => l.`1min` / coresPerCpu * 100).getOrElse(0.0)}%;" >
                 |   ${loadAvgOPt.map(l => l.`1min` * 1.0 / coresPerCpu * 100).getOrElse(0.0)}%
                 |</div>
             """.stripMargin
            val tableStr =
              s"""
                 |<table class="table table-striped table-hover">
                 | <tr>
                 |  <td>1minute</td>
                 |  <td>5minute</td>
                 |  <td>15minute</td>
                 | </tr>
                 | <tr><td> ${loadAvgOPt.map(_.`1min`).getOrElse(0.0)} </td>
                 |     <td>${loadAvgOPt.map(_.`5min`).getOrElse(0.0)} </td>
                 |     <td> ${loadAvgOPt.map(_.`15min`).getOrElse(0.0)}</td>
                 | </tr>
                 |</table>
             """.stripMargin
            document.getElementById(`tableLoad@i`).innerHTML = tableStr
        })
    }, 1000)
  }

  @JSExport
  def getMemStatus(agentId: String, divId: String): Unit = {
    window.setInterval(() => {
      Ajax.get(url = s"/mem/health?agentId=$agentId").map(r => decode[Map[String, String]](r.responseText).right.get)
        .map(result => result("code") match {
          case "0000" =>
            val memEntityOpt = decode[List[MEMEntity]](result("entity")).right.get.headOption
            document.getElementById(divId).innerHTML =
              s"""
                 |<div class="progress-bar " role="progressbar" aria-valuemin="0" aria-valuemax="100" style="width:${memEntityOpt.map(m => m.used / m.total * 100).map(_.toInt).getOrElse(0)}%;" >
                 |   ${memEntityOpt.map(m => m.used / m.total * 100).map(_.toInt).getOrElse(0)}%
                 |</div>
             """.stripMargin
          case "0001" =>
            val memEntityOpt = decode[List[MEMEntity]](result("entity")).right.get.headOption
            document.getElementById(divId).innerHTML =
              s"""
                 |<div class="progress-bar progress-bar-warning" role="progressbar" aria-valuemin="0" aria-valuemax="100" style="width:${memEntityOpt.map(m => m.used / m.total * 100).map(_.toInt).getOrElse(0)}%;" >
                 |   ${memEntityOpt.map(m => m.used / m.total * 100).map(_.toInt).getOrElse(0)}%
                 |</div>
             """.stripMargin
        })
    }, 1000)
  }

  @JSExport
  def getLastHeartBeat(agentId: String, id: String): Unit = {
    window.setInterval(() => {
      Ajax.get(url = s"/agent/lastHeartBeat?agentId=$agentId")
        .map(r => decode[Map[String, String]](r.responseText).right.get)
        .map(result => decode[Seq[Long]](result("entity")).right.get.headOption)
        .map(optLong => optLong.map(timeShow(_))).map(optString => optString match {
        case Some(str) =>
          document.getElementById(id).textContent = str
        case None =>
          document.getElementById(id).textContent = "无心跳"
      })
    }, 1000)
  }

  def timeShow(time: Long): String = {
    val date = new Date(time)
    s"${date.getYear + 1900}.${date.getMonth() + 1}.${date.getDate()}.${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}"
  }
}