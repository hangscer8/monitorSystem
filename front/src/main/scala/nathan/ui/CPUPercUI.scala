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
import nathan.util.DateShowUtil

import scala.scalajs.js
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

object CPUPercUI {
  def newChart(datas: js.Array[AnySeries]) = new HighchartsConfig {
    // Chart config
    override val chart = Chart(`type` = "line", animation = false)
    //      override val chart: Cfg[Chart] = Chart()

    // Chart title
    override val title = Title(text = "CPU占用率")

    // X Axis settings
    override val xAxis = js.Array(XAxis(categories = js.Array((1 to 20).map(_.toString): _*)))

    // Y Axis settings
    override val yAxis = js.Array(YAxis(min = -.0, max = 1.0, title = YAxisTitle(text = "CPU占用比")))
    // Series
    override val series = datas
    override val credits = Credits(false)
    override val legend = Legend(layout = "horizontal")
  }

  //流程是，选择左侧栏的agent后，图表才会每秒刷新

  val agents = Vars.empty[AgentMachineEntity] //需要把vars此类变量放在全局

  val agentChose = Var[scala.Option[AgentMachineEntity]](None) //左侧栏选择了哪个agent

  val chartDivId = Var("chartDivId" + scala.util.Random.alphanumeric.take(6).mkString(""))

  val cpuPercList = Var(List.empty[CPUPercEntity]) //不一定非得使用Vars

  val isDynamic = Var(true) //是否实时加载数据 每秒刷新

  val currentIntervalTask = Var[scala.Option[Int]](None) //agent被点击切换时，需要终止当前的循环任务

  val loadSize = Var(20) //加载cpu数据的信息的数量

  @dom def cpuChart(): Binding[Node] = {
    //    var chartDivId = _chartDivId.bind


    window.setTimeout(() => { //加载左侧agent列表
      Ajax.get(url = s"${baseUrl}/${prefix}/agentList", headers = Map(authHead -> window.localStorage.getItem(authHead)))
        .map(resp => resp.responseText).map(decode[Seq[AgentMachineEntity]](_).right.get)
        .map { _agents =>
          agents.value.clear()
          agents.value ++= _agents
        }
    }, 100.0)

    agentChose.bind match {
      case None => //左侧仍然没选择哪个在线的agent

      case Some(targetAgent) => //左侧点击事件，加载此agent收集的cpu相关数据
        isDynamic.bind match {
          case true =>
            //清楚当前的循环任务
            currentIntervalTask.value.map(taskId => window.clearInterval(taskId))
            val taskId = window.setInterval(() => {
              Ajax.get(url = s"${baseUrl}/${prefix}/cpuPerc?agentId=${targetAgent.agentId}&size=${loadSize.value}", headers = Map(authHead -> window.localStorage.getItem(authHead)))
                .map(resp => resp.responseText).map(decode[List[CPUPercEntity]](_).right.get)
                .map { _cpuPercs =>
                  cpuPercList.value = _cpuPercs
                }
            }, 1000)
            currentIntervalTask.value = Some(taskId)
          case false =>
            Ajax.get(url = s"${baseUrl}/${prefix}/cpuPerc?agentId=${targetAgent.agentId}&size=${loadSize.value}", headers = Map(authHead -> window.localStorage.getItem(authHead)))
              .map(resp => resp.responseText).map(decode[List[CPUPercEntity]](_).right.get)
              .map { _cpuPercs =>
                cpuPercList.value = _cpuPercs
              }
        }
    }


    //    isDynamic.bind match {
    //      case true => println("true")
    //      case false =>
    //        window.setTimeout(() => {
    //          val data = js.Array[AnySeries](
    //            SeriesLine(name = "蒋航122", data = js.Array[Double](2, 3, 8), animation = true),
    //            SeriesLine(name = "是这样1221", data = js.Array[Double](10, 15, 7), animation = true)
    //          )
    //          jQuery(s"#${chartDivId.value}").highcharts(newChart(data))
    //        }, 300)
    //    }
    cpuPercList.bind match { //绘制图表
      case Nil =>
      case cpuPercs: List[CPUPercEntity] =>
        window.setTimeout(() => {
          val data = js.Array[AnySeries](
            SeriesLine(name = "用户占用", data = js.Array[Double](cpuPercs.map(_.user): _*), animation = false),
            SeriesLine(name = "空闲", data = js.Array[Double](cpuPercs.map(_.idle): _*), animation = false),
            SeriesLine(name = "系统占用", data = js.Array[Double](cpuPercs.map(_.sys): _*), animation = false)
          )
          jQuery(s"#${chartDivId.value}").highcharts(newChart(data))
        }, 50)
    }

    <div class="row">
      <div class="panel panel-info col-md-3 col-md-offset-0">
        <div class="panel-heading">Agent onLine</div>
        <div class="panel-body">
          <ul class="list-group">
            {for (agent <- agents) yield {
            <li class="list-group-item">
              <a class="btn btn-sm" onclick={e: Event => agentChose.value = Some(agent)}>
                {s"${agent.ip}   ${agent.akkaPort}   ${DateShowUtil.show(agent.joinedTime)}"}
              </a>
            </li>
          }}
          </ul>
        </div>
      </div>
      <div class="panel panel-default col-md-9 col-md-offset-0">
        <div class="panel-heading">cpu数据展示</div>
        <div class="panel-body">
          <div id={chartDivId.bind}>
          </div>
        </div>
      </div>
    </div>
  }
}