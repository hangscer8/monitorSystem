package nathan.ui

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.{dom, _}
import nathan.monitorSystem.Protocols.AddAgentReq
import org.scalajs.dom.html._
import nathan.util.implicitUtil._
import org.scalajs.dom.raw.Event
import org.scalajs.dom.window
import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax._
import nathan.monitorSystem.AkkaSystemConst._

import scala.scalajs.js.annotation.JSExport
import scala.util.Try
import nathan.util.implicitUtil._
import org.scalajs.dom.ext.Ajax
import nathan.monitorSystem.MsgCode._
import nathan.util.HttpHeadSupport

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

@JSExport
object AddAgentServiceUI extends HttpHeadSupport {
  var ip = "" //这里为什么不需要@dom
  var port = 0

  val isConnect = Var(true)

  //使用@dom注解，使之模块化

  @dom val ipInput: Binding[Input] = <input type="text"
                                            id="agentIP"
                                            onchange={e: Event =>
                                              val target = e.currentTarget.as[Input]
                                              ip = target.value}
                                            class="form-control"
                                            placeholder="input the ip address of agent machine"/>

  @dom val portInput: Binding[Input] = <input type="number"
                                              id="agentPort"
                                              onchange={e: Event =>
                                                val target = e.currentTarget.as[Input]
                                                port = target.value.nonEmpty match {
                                                  case true =>
                                                    Try(target.value.toInt).getOrElse(0)
                                                  case false => 0
                                                }}
                                              class="form-control"
                                              placeholder="input the machine target port"/>
  val connectAgentAction = (e: Event) => {
    (ip.count(ch => ch == '.') >= 3 && port != 0) match {
      case true =>
        Ajax.get(url = s"${baseUrl}/${prefix}/agent?ip=${ip}&port=${port}", headers = Map(`Content-Type` -> `text/plain`, authHead -> window.localStorage.getItem(authHead)))
          .map(resp => resp.responseText)
          .map(decode[String](_).right.get)
          .map {
            case `success` =>
              isConnect := true
              window.confirm("添加成功")
            case other =>
              window.alert(s"$other,添加失败!")
          }
      case false =>
        window.alert("ip地址或者端口填写错误")
    }
  }

  @dom def addAgentDivComponent: Binding[Div] = { //添加一个Agent的Form组件
    <div class="row">
      <div class="col-md-4 col-md-offset-4 text-center text-info">Add Agent Machine</div>
      <form class="col-md-4 col-md-offset-4">
        <div class="form-group">
          <label for="agentIP" class="control-label ">
            <span class="glyphicon glyphicon-globe"/>
            Agent Ip address
          </label>{ipInput.bind}
        </div>
        <div class="form-group">
          <label for="agentPort" class="control-label">
            <span class="glyphicon glyphicon-screenshot"/>
            Agent Port
          </label>{portInput.bind}
        </div>
        <input type="button"
               value="连接"
               onclick={connectAgentAction}
               class="col-md-2 col-md-offset-3 btn btn-primary"/>
      </form>
    </div>
  }

}