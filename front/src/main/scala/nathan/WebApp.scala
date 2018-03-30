package nathan

import io.circe.generic.auto._
import io.circe.syntax._
import nathan.monitorSystem.Protocols.{RegisterReq, UserEntity}
import org.scalajs.dom._
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.ext.Ajax.InputData
import org.scalajs.dom.ext.Ajax.InputData._
import org.scalajs.dom.html.{Button, Form, Image, Input}
import nathan.util.CommonUtil._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js.annotation.JSExport
import scala.util.{Failure, Success}
import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax._
import nathan.util.Snowflake

@JSExport
object WebApp {

  val `Content-Type` = "Content-Type"
  val `application/json` = "application/json"

  @JSExport
  def registerService() = { //用户注册
    val userNameInput = document.getElementById("username").as[Input]
    val passwordInput = document.getElementById("password").as[Input]
    val passwordConformInput = document.getElementById("passwordConform").as[Input]
    val aliasInput = document.getElementById("alias").as[Input]
    val emailInput = document.getElementById("email").as[Input]
    val avatarInput = document.getElementById("avatarInput").as[Input]
    val registerButton = document.getElementById("registerButton").as[Button]
    val resetButton = document.getElementById("resetButton").as[Button]

    registerButton.onclick = _ => { //用户注册的按钮
      passwordConformInput.value == passwordInput.value match {
        case true =>
          val newUser = UserEntity(id = Snowflake.nextId(), username = userNameInput.value, password = passwordInput.value, alias = aliasInput.value, email = emailInput.value, lastActiveTime = System.currentTimeMillis())
          val data = newUser.asJson.spaces2
          Ajax.post(url = "register", data = data, headers = Map(`Content-Type` -> `application/json`)).map(_.responseText).onComplete(a => window.alert(a.toString))
        //json序列化
        case false => window.alert(s"密码不一致，请确认!")
      }
    }

    resetButton.onclick = _ => { //清除信息
      userNameInput.value = ""
      passwordInput.value = ""
      passwordConformInput.value = ""
      aliasInput.value = ""
      emailInput.value = ""
      avatarInput.value = ""
    }

    //**************
    val uploadButton = document.getElementById("uploadButton").as[Button] //文件上传样例
    uploadButton.onclick = e => {
      val pictureForm = document.getElementById("pictureForm").as[Form]
      val formData = new FormData(pictureForm)

      Ajax.post(url = "register/upload", data = InputData.formdata2ajax(formData)).onComplete {
        case Success(a) => //json反序列化
          val result = decode[Map[String, String]](a.responseText).right.get
          result("code") match {
            case "0000" =>
              val avatarShowImg = document.getElementById("avatarShowImg").as[Image]
              avatarShowImg.src = s"stream/${result("newFileName")}"
              val avatarInput = document.getElementById("avatarInput").as[Input]
              avatarInput.value = result("newFileName")
            case "0001" =>
              window.alert(result("message"))
          }
        case Failure(ex) => window.alert(ex.getMessage)
      }
      e.preventDefault() //防止页面跳转(对于form里的button，跳转是默认行为)
    }
  }
}