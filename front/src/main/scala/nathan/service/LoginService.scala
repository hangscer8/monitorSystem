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

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom.ext.Ajax.InputData._

import scala.concurrent.Future

@JSExport
object LoginService {

  val loginActionFlag = Var(0) //0表示未请求

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
      loginActionFlag.value = loginActionFlag.value + 1
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