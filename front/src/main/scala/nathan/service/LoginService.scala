package nathan.service

import com.thoughtworks.binding.Binding.Var
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
import nathan.util.CommonUtil.setAuth
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom.ext.Ajax.InputData._

import scala.concurrent.Future

@JSExport
object LoginService extends HttpHeadSupport {

  val isLogin = Var(false)

  @JSExport
  def render() = {
    dom.render(document.body, genMainDiv())
  }

  @dom def genMainDiv(): Binding[Div] = {

    val userNameInput = <input type="text"
                               id="username"
                               class="form-control"
                               placeholder="input a user name"/>.as[Input]
    val passwordInput = <input type="password"
                               id="password"
                               class="form-control"
                               placeholder="input a password"/>.as[Input]
    val loginAction = (_: Event) => {
      (userNameInput.value.nonEmpty && passwordInput.value.nonEmpty) match {
        case true =>
          Ajax.post(url = s"${baseUrl}/${prefix}/user",
            data = UserEntity(userNameInput.value, passwordInput.value, System.currentTimeMillis(), Option(setAuth)).asJson.spaces2
            , headers = headerWithJson).map(_.responseText).map(decode[String](_).right.get)
            .map {
              case `success` =>
                isLogin := true
                window.confirm("登陆成功")
              case _ => window.alert("登陆失败")
            }
        case false =>
          window.alert("用户名或者密码有空值")
      }
    }

    <div class="row">
      <div class="col-md-4 col-md-offset-4 text-center text-info">用户登陆</div>
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
        <input type="button"
               value="登陆"
               onclick={loginAction}
               class="col-md-2 col-md-offset-3 btn btn-primary"/>
        <input type="button"
               onclick={e: Event =>
                 userNameInput.value = ""
                 passwordInput.value = ""}
               value="清除"
               class="col-md-2 col-md-offset-1 btn btn-warning"/>
      </form>
    </div>
  }
}