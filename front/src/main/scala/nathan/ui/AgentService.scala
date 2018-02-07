package nathan.ui

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.{dom, _}
import org.scalajs.dom.html._
import nathan.util.implicitUtil._
import org.scalajs.dom.raw.Event

import scala.scalajs.js.annotation.JSExport
import scala.util.Try

@JSExport
object AgentServiceUI {
  var ip = "" //这里为什么不需要@dom
  var port = 0

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
               onclick={e: Event =>
                 println(ip)
                 println(port)}
               class="col-md-2 col-md-offset-3 btn btn-primary"/>
        <input type="button" value="清除" class="col-md-2 col-md-offset-1 btn btn-warning"/>
      </form>
    </div>
  }

}