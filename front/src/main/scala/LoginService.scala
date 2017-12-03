package nathan

import entity.UserReq
import io.circe.generic.auto._
import io.circe.syntax._
import org.scalajs.dom._
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.ext.Ajax.InputData
import org.scalajs.dom.html.Input
import util.CommonConst.{authHead, baseUrl}
import util.CommonUtil._
import util.HttpHeadSupport

import scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js.annotation.JSExport
import scala.util.Success

@JSExport
object LoginService extends HttpHeadSupport {
  @JSExport
  def setAuth(authOption: Option[String] = None) = authOption match {
    case _: Some[_] =>
      window.localStorage.setItem(authHead, authOption.get)
    case None =>
      window.localStorage.setItem(authHead, scala.util.Random.alphanumeric.take(20).mkString)
  }

  def getAuth = window.localStorage.getItem(authHead)

  @JSExport
  def loginForm(form: html.Form) = {
    val _inputs = form.getElementsByTagName("input")
    val inputs = (0 until _inputs.length).map(_inputs(_).asInstanceOf[Input])
    val username = inputs.filter(_.name == "username").head.value
    val password = inputs.filter(_.name == "password").head.value
    val `type` = inputs.filter(_.name == "type").filter(_.checked == true).head.value
    login(UserReq(username,password,`type`)).onComplete{
      case  Success(xhr)=>
        console.log(xhr.responseText)
    }
  }

  def login(user: UserReq) = {
    Ajax.post(url = baseUrl / "monitorSystem" / "login", data = InputData.str2ajax(user.asJson.spaces2), headers = header)
  }
}
