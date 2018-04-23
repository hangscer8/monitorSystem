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