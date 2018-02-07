package nathan.ui

import com.thoughtworks.binding.{dom, _}
import org.scalajs.dom.html._

import scala.scalajs.js.annotation.JSExport
@JSExport
object AgentServiceUI {
  @dom def addAgentDivComponent: Binding[Div] = { //添加一个Agent的Form组件
    <div class="row">
      <div class="col-md-4 col-md-offset-4 text-center text-info">Add Agent Machine</div>
      <form class="col-md-4 col-md-offset-4">
        <div class="form-group">
          <label for="agentIP" class="control-label ">
            <span class="glyphicon glyphicon-globe"/>
            Agent Ip address
          </label>
          <input type="text"
                 id="agentIP"
                 class="form-control"
                 placeholder="input the ip address of agent machine"/>
        </div>
        <div class="form-group">
          <label for="agentPort" class="control-label">
            <span class="glyphicon glyphicon-screenshot"/>
            Agent Port
          </label>
          <input type="number"
                 id="agentPort"
                 class="form-control"
                 placeholder="input the machine target port"/>
        </div>
        <input type="button"
               value="连接"
               class="col-md-2 col-md-offset-3 btn btn-primary"/>
        <input type="button" value="清除" class="col-md-2 col-md-offset-1 btn btn-warning"/>
      </form>
    </div>
  }

}
