package nathan.service.login

import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax._
import nathan.monitorSystem.Protocols.UserEntity
import nathan.util.{CommonUtilTrait, Snowflake}
import org.scalajs.dom._
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.ext.Ajax.InputData
import org.scalajs.dom.ext.Ajax.InputData._
import org.scalajs.dom.html.{Button, Form, Image, Input}

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js.annotation.JSExport
import scala.util.{Failure, Success}

@JSExport
object LoginService extends CommonUtilTrait {
  @JSExport
  def loginService(): Unit = {
    val usernameInput = document.getElementById("username").as[Input]
    val passwordInput = document.getElementById("password").as[Input]
    val loginButton = document.getElementById("loginButton").as[Button]
    val resetButton = document.getElementById("resetButton").as[Button]

    resetButton.onclick = _ => {
      usernameInput.value = ""
      passwordInput.value = ""
    }

    loginButton.onclick = _ => {
      val data = Map("username" -> usernameInput.value, "password" -> passwordInput.value).asJson.noSpaces
      Ajax.post(url = "login", data = InputData.str2ajax(data), headers = Map(`Content-Type` -> `application/json`))
        .map(r => decode[Map[String, String]](r.responseText).right.get).map(result => result("code") match {
        case "0000" =>
          window.confirm("登陆成功!，是否转到首页!!") match {
            case true =>
            case false => () //nothing to do
          }
        case "0001" =>
          window.alert("登陆失败!，请检查账户是否存在!!")
      })
    }
  }
}