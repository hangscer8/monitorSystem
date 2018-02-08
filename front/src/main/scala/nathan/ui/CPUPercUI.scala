package nathan.ui

import com.highcharts.HighchartsAliases._
import com.highcharts.HighchartsUtils._
import com.highcharts.config._
import com.thoughtworks.binding.Binding.{Var, Vars}
import com.thoughtworks.binding.{dom, _}
import nathan.monitorSystem.Protocols.CPUPercEntity
import nathan.util.implicitUtil._
import org.scalajs.dom._
import org.scalajs.dom.html._
import org.scalajs.jquery.jQuery

import scala.scalajs.js

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

  @dom def cpuChart(agentId: String): Binding[Node] = {
    val chartDivId = Var("chartDivId:" + scala.util.Random.alphanumeric.take(6).mkString(""))
    val cpuPercs = Vars.empty[CPUPercEntity]
    val isDynamic = Var(false) //是否实时加载数据
    
    isDynamic.bind match {
      case true => println("true")
      case false =>
        window.setTimeout(() => {
          val data = js.Array[AnySeries](
            SeriesLine(name = "蒋航122", data = js.Array[Double](2, 3, 8), animation = false),
            SeriesLine(name = "是这样1221", data = js.Array[Double](10, 15, 7), animation = false)
          )
          jQuery(s"#${chartDivId.value}").highcharts(newChart(data))
        }, 1000)
    }

    <div class="row">
      <div class="panel panel-default col-md-8 col-md-offset-2">
        <div class="panel-heading">cpu数据展示</div>
        <div class="panel-body">
          <div id={chartDivId.bind}>
          </div>
        </div>
      </div>
    </div>
  }
}