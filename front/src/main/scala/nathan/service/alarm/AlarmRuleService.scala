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
      val threshold = formElements.namedItem("threshold").as[Input].value.toDouble
      val condition = formElements.namedItem("condition").as[Select].value
      val appearTimes = formElements.namedItem("appearTimes").as[Input].value.toInt
      //      AlarmRuleEntity(id = Snowflake.nextId(), agentId =)
    }

  }
}