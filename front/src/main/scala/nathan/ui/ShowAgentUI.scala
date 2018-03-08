package nathan.ui

import com.thoughtworks.binding.Binding.{Var, Vars}
import com.thoughtworks.binding.{dom, _}
import nathan.monitorSystem.Protocols.{AddAgentReq, AgentMachineEntity}
import org.scalajs.dom.html._
import nathan.util.implicitUtil._
import org.scalajs.dom.raw.Event
import org.scalajs.dom.{Node, window}
import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax._
import nathan.monitorSystem.AkkaSystemConst._

import scala.scalajs.js.annotation.JSExport
import scala.util.Try
import nathan.util.implicitUtil._
import org.scalajs.dom.ext.Ajax
import nathan.monitorSystem.MsgCode._
import nathan.util.{DateShowUtil, HttpHeadSupport}

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js.Date

object ShowAgentUI extends HttpHeadSupport {
  val agents = Vars.empty[AgentMachineEntity]

  @dom def showAgentTableCom: Binding[Node] = {
    {
      window.onload = { e: Event =>
        window.setInterval(() => {
          Ajax.get(url = s"${baseUrl}/${prefix}/agentList", headers = Map(authHead -> window.localStorage.getItem(authHead)))
            .map(resp => resp.responseText).map(decode[Seq[AgentMachineEntity]](_).right.get)
            .map {
              _agents =>
                agents.value.clear()
                agents.value ++= _agents
            }
        }, 1000)
      }
    }
    <table class="table table-hover table-striped">
      <tr class="row">
        <th class="text-center col-md-1 ">ip</th>
        <th class="text-center col-md-1 ">port</th>
        <th class="text-center col-md-1 ">cpu Vendor</th>
        <th class="text-center col-md-1 ">model</th>
        <th class="text-center col-md-1 ">joinedTime</th>
        <th class="text-center col-md-1 ">receivedMsg</th>
      </tr>{for (agent <- agents) yield {
      <tr class="row">
        <td class="text-center col-md-1 ">
          {agent.ip.toString}
        </td>
        <td class="text-center col-md-1 ">
          {agent.akkaPort.toString}
        </td>
        <td class="text-center col-md-1 ">
          {agent.cpuVendor.toString}
        </td>
        <td class="text-center col-md-1 ">
          {agent.model.toString}
        </td>
        <td class="text-center col-md-1 ">
          {DateShowUtil.show(agent.joinedTime)}
        </td>
        <td class="text-center col-md-1 ">
          {agent.sendMsgNum.toString}
        </td>
      </tr>
    }}
    </table>
  }
}
