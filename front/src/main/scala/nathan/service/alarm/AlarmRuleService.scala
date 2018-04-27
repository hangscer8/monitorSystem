package nathan.service.alarm

import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax._
import nathan.monitorSystem.Protocols.{AddAgentReq, AlarmRuleEntity}
import nathan.util.{CommonUtilTrait, Snowflake}
import org.scalajs.dom._
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.ext.Ajax.InputData
import org.scalajs.dom.html.{Button, Form, Input, Select}

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js.annotation.JSExport

@JSExport
object AlarmRuleService extends CommonUtilTrait {
  @JSExport
  def showAlarmRule(agentId: String, divId: String): Unit = {
    Ajax.get(url = s"/getAlarmRule?agentId=$agentId")
      .map(r => decode[Map[String, String]](r.responseText).right.get)
      .map(r => decode[Seq[AlarmRuleEntity]](r("entity")).right.get)
      .map { seq =>
        s"""
           |<table class="table table-striped table-condensed table-hover">
           |  <tr><td>规则名</td><td>类型</td><td>阈值</td><td>条件</td><td>操作</td></tr>
           |  ${seq.map { item => val trId = "tr" + scala.util.Random.alphanumeric.take(20).mkString;s"""<tr id="${trId}"><td>${item.title}</td><td>${item.`type`}</td><td>${item.threshold}</td><td>${item.condition}</td><td><button class="btn btn-sm" onclick="nathan.service.alarm.AlarmRuleService().deleteAlarmRuleService('${item.id}','$trId')">删除</button></td></tr>""" }.mkString}
           |</table>
       """.stripMargin
      }.map(tableData => document.getElementById(divId).innerHTML = tableData)
  }

  @JSExport
  def deleteAlarmRuleService(ruleIdStr: String, trId: String): Unit = {
    Ajax.get(url = s"/deleteAlarmRule?ruleId=$ruleIdStr").map(r => decode[Map[String, String]](r.responseText).right.get)
      .map(result => result("code") match {
        case "0000" =>
          val targetNode = document.getElementById(trId)
          val parentNode = targetNode.parentNode
          parentNode.removeChild(targetNode)
      })
  }

  @JSExport
  def alarmRuleService(): Unit = {
    document.getElementById("createAlarmRuleButton").as[Button].onclick = e => {
      e.preventDefault()
      val formElements = document.getElementsByClassName("createAlarmRuleForm").item(0).as[Form].elements
      val title = formElements.namedItem("title").as[Input].value
      val `type` = formElements.namedItem("type").as[Select].value
      val agentId = formElements.namedItem("agentId").as[Select].value
      val threshold = formElements.namedItem("threshold").as[Input].value.toDouble
      val condition = formElements.namedItem("condition").as[Select].value
      val appearTimes = formElements.namedItem("appearTimes").as[Input].value.toInt
      title.isEmpty match {
        case true =>
          window.alert("规则名称不能为空")
          throw new Exception
        case _ =>
      }
      agentId.isEmpty match {
        case true =>
          window.alert("没有选择agent!")
          throw new Exception
        case false =>
      }
      `type`.isEmpty match {
        case true =>
          window.alert("需要选择类型!")
          throw new Exception
        case false =>
      }
      val entity = AlarmRuleEntity(id = Snowflake.nextId(), agentId = agentId,
        title = title, `type` = `type`, threshold, condition, appearTimes = appearTimes)
      Ajax.post(url = "/alarmRule", data = InputData.str2ajax(entity.asJson.noSpaces), headers = Map(`Content-Type` -> `application/json`))
        .map(r => decode[Map[String, String]](r.responseText).right.get)
        .map(result => result("code") match {
          case "0000" => window.alert("提交成功!")
          case _ => window.alert("提交失败!")
        })
    }
  }
}