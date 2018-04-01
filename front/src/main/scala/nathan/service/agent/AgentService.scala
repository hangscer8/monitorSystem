package nathan.service.agent

import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax._
import nathan.monitorSystem.Protocols.AddAgentReq
import nathan.util.CommonUtilTrait
import org.scalajs.dom._
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.ext.Ajax.InputData
import org.scalajs.dom.html.{Button, Input}

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js.annotation.JSExport

@JSExport
object AgentService extends CommonUtilTrait {
  @JSExport
  def addAgentService() = { //添加一个agent
    val ipInput = document.getElementById("ip").as[Input]
    val portInput = document.getElementById("port").as[Input]
    val addAgentButton = document.getElementById("addAgentButton").as[Button]
    val resetButton = document.getElementById("resetButton").as[Button]

    addAgentButton.onclick = e => {
      val data = AddAgentReq(ip = ipInput.value, port = portInput.value.toInt).asJson.noSpaces
      Ajax.post(url = "addAgent", data = InputData.str2ajax(data), headers = Map(`Content-Type` -> `application/json`))
        .map(r => decode[Map[String, String]](r.responseText).right.get)
        .map(result => result("code") match {
          case "0000" =>
            window.alert(result("successMsg"))
          case "0001" =>
            window.alert(result("errorMsg"))
        })
      e.preventDefault() //按钮的默认事件是刷新页面 需要阻止
    }

    resetButton.onclick = e => {
      ipInput.value = ""
      portInput.value = ""
      e.preventDefault()
    }
  }

  @JSExport
  def showAgentService(): Unit = {

  }
}