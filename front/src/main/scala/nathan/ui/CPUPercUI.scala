package nathan.ui

import com.highcharts.HighchartsAliases._
import com.highcharts.HighchartsUtils._
import com.highcharts.config._
import com.thoughtworks.binding.Binding.{Var, Vars}
import com.thoughtworks.binding.{dom, _}
import nathan.monitorSystem.AkkaSystemConst.{authHead, baseUrl, prefix}
import nathan.monitorSystem.Protocols.{AgentMachineEntity, CPUPercEntity}
import nathan.ui.ShowAgentUI.agents
import nathan.util.implicitUtil._
import org.scalajs.dom._
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.html._
import org.scalajs.jquery.jQuery
import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax._
import scala.scalajs.js
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

object CPUPercUI {
  def newChart(datas: js.Array[AnySeries]) = new HighchartsConfig {
    // Chart config
    override val chart = Chart(`type` = "line", animation = false)
    //      override val chart: Cfg[Chart] = Chart()

    // Chart title
    override val title = Title(text = "Demo bar chart")

    // X Axis settings
    override val xAxis = js.Array(XAxis(categories = js.Array("Apples", "Bananas", "Oranges")))

    // Y Axis settings
    override val yAxis = js.Array(YAxis(min = -.0, max = 20.0, title = YAxisTitle(text = "Fruit eaten")))
    // Series
    override val series = datas
    override val credits = Credits(false)
    override val legend = Legend(layout = "horizontal")
  }

  val agents = Vars.empty[AgentMachineEntity] //需要把vars此类变量放在全局

  val _chartDivId = Var("chartDivId" + scala.util.Random.alphanumeric.take(6).mkString(""))

  val cpuPercs = Vars.empty[CPUPercEntity]

  val isDynamic = Var(false) //是否实时加载数据

  @dom def cpuChart(): Binding[Node] = {
    var chartDivId = _chartDivId.bind


    window.setTimeout(() => { //加载左侧agent列表
      Ajax.get(url = s"${baseUrl}/${prefix}/agentList", headers = Map(authHead -> window.localStorage.getItem(authHead)))
        .map(resp => resp.responseText).map(decode[Seq[AgentMachineEntity]](_).right.get)
        .map {
          _agents =>
            agents.value.clear()
            agents.value ++= _agents
        }
    }, 300.0)


    isDynamic.bind match {
      case true => println("true")
      case false =>
        window.setTimeout(() => {
          val data = js.Array[AnySeries](
            SeriesLine(name = "蒋航122", data = js.Array[Double](2, 3, 8), animation = true),
            SeriesLine(name = "是这样1221", data = js.Array[Double](10, 15, 7), animation = true)
          )
          jQuery(s"#$chartDivId").highcharts(newChart(data))
        }, 800)
    }


    <div class="row">
      <div class="panel panel-info col-md-3 col-md-offset-0">
        <div class="panel-heading">Agent onLine</div>
        <div class="panel-body">
          <ul class="list-group">
            {for (agent <- agents) yield {
            <li class="list-group-item">
              <a class="btn btn-sm">
                {s"ip:${agent.ip},port:${agent.akkaPort}"}
              </a>
            </li>
          }}
          </ul>
        </div>
      </div>
      <div class="panel panel-default col-md-9 col-md-offset-0">
        <div class="panel-heading">cpu数据展示</div>
        <div class="panel-body">
          <div id={_chartDivId.bind}>
          </div>
        </div>
      </div>
    </div>
  }
}