package nathan.service

import com.thoughtworks.binding.{dom, _}
import io.circe.parser.decode
import nathan.monitorSystem.AkkaSystemConst._
import nathan.monitorSystem.Protocols.{RegisterReq, UserEntity}
import nathan.util.implicitUtil._
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.html.{Input, _}
import org.scalajs.dom.{document, _}
import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax._
import nathan.monitorSystem.MsgCode._
import nathan.util.HttpHeadSupport

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom.ext.Ajax.InputData._

import scala.concurrent.Future

@JSExport
object RegisterService extends HttpHeadSupport {


  @JSExport
  def render() = {
    dom.render(document.body, genMainDiv)
  }

  @dom
  def genMainDiv: Binding[Div] = {
    val userNameInput = <input type="text" id="username" class="form-control" placeholder="input a user name"/>.as[Input]
    val passwordInput = <input type="password" id="password" class="form-control" placeholder="input a password"/>.as[Input]
    val passwordConformInput = <input type="password" id="passwordConform" class="form-control" placeholder="conform the password"/>.as[Input]
    val submithandler: Event => Unit = (event: Event) => {
      passwordConformInput.value == passwordInput.value match {
        case true => List(userNameInput.value, passwordInput.value).forall(_.nonEmpty) match {
          case true => //RegisterAction
            Ajax.get(url = s"${baseUrl}/${prefix}/register/userName/${userNameInput.value}")
              .map(response => decode[Boolean](response.responseText).right.get)
              .flatMap { userNameExits =>
                userNameExits match {
                  case false =>
                    Ajax.post(url = s"${baseUrl}/${prefix}/register", data = RegisterReq(userNameInput.value, passwordInput.value).asJson.spaces2, headers = Map(`Content-Type` -> `application/json`))
                      .map(resp => decode[String](resp.responseText).right.get)
                  case true => Future(failure)
                }
              }.map { code =>
              code match {
                case `success` => window.alert("成功")
                case `failure` => window.alert("失败")
              }
            }
          case false => window.alert("用户名或者密码为空!")
        }
        case false =>
          window.alert("密码不一致!")
      }
    }
    val resetHandler: Event => Unit = (event: Event) => {
      userNameInput.value = ""
      passwordConformInput.value = ""
      passwordInput.value = ""
    }
    val submitButton = <input onclick={submithandler} type="button" value="注册" class="col-md-2 col-md-offset-2 btn btn-primary"/>.as[Input]
    val resetButton = <input onclick={resetHandler} type="button" value="清除" class="col-md-2 col-md-offset-1 btn btn-warning"/>.as[Input]
    <div class="row">
      <div class="col-md-4 col-md-offset-4 text-center text-info">注册用户</div>
      <form class="col-md-4 col-md-offset-4">
        <div class="form-group">
          <label for="username" class="control-label ">
            <span class="glyphicon glyphicon-user"/>
            UserName
          </label>{userNameInput}
        </div>
        <div class="form-group">
          <label for="password" class="control-label">
            <span class="glyphicon glyphicon-sunglasses"/>
            Password
          </label>{passwordInput}
        </div>
        <div class="form-group">
          <label for="passwordConform" class="control-label">
            <span class="glyphicon glyphicon-sunglasses"/>
            Password
          </label>{passwordConformInput}
        </div>{submitButton}{resetButton}
      </form>
    </div>
  }
}