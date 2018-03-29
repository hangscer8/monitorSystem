package nathan

import nathan.monitorSystem.Protocols.RegisterReq
import org.scalajs.dom._

import scala.scalajs.js.annotation.JSExport
import nathan.util.implicitUtil._
import org.scalajs.dom.html.{Button, Input}
import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax._
import io.circe.parser.decode
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.ext.Ajax.InputData
import org.scalajs.dom.ext.Ajax.InputData._
import org.scalajs.jquery.jQuery

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

@JSExport
object WebApp {

  val `Content-Type` = "Content-Type"
  val `application/json` = "application/json"

  @JSExport
  def registerService() = { //用户注册
    val userNameInput = document.getElementById("username").as[Input]
    val passwordInput = document.getElementById("password").as[Input]
    val passwordConformInput = document.getElementById("passwordConform").as[Input]
    val registerButton = document.getElementById("registerButton").as[Button]
    val resetButton = document.getElementById("resetButton").as[Button]
    val file = document.getElementById("file").as[File]

    registerButton.onclick = _ => { //用户注册的按钮
      passwordConformInput.value == passwordInput.value match {
        case true =>
          val data = RegisterReq(userNameInput.value, passwordInput.value).asJson.spaces2
          Ajax.post(url = "register", data = data, headers = Map(`Content-Type` -> `application/json`)).map(_.responseText).onComplete(a => window.alert(a.toString))
        //          val formData = new FormData()
        //          formData.append("file", file)
        //          Ajax.post(url = "register/upload", data = InputData.formdata2ajax(formData), headers = Map("enctype" -> "multipart/form-data", "X-Requested-With" -> "XMLHttpRequest", "X-FILENAME" -> "ahahha.kaka")).onComplete(a => println(a.toString))
        //          window.alert(file.size.toString)
        case false => window.alert(s"密码不一致，请确认!")
      }
    }

    resetButton.onclick = _ => { //清除信息
      userNameInput.value = ""
      passwordInput.value = ""
      passwordConformInput.value = ""
    }

  }
}