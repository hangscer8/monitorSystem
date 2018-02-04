package nathan

import java.util.Date

import com.thoughtworks.binding.Binding._
import com.thoughtworks.binding._
import nathan.util.implicitUtil._
import org.scalajs.dom.html._
import org.scalajs.dom.{Event, document}

import scala.scalajs.js.annotation.JSExport

@JSExport
object WebApp {

  val count = Var(0)

  @JSExport
  def fun() = {
    dom.render(document.body, genRender())
  }

  @dom
  def status: Binding[String] = {
    val startTime = new Date()
    s"本页面初始化的时间是${startTime.toString}。按钮被点击了${count.bind.toString}次，最后一次按下的时间是${new Date()}"
  }

  @dom
  def genRender(): Binding[Div] = {
    <div>
      {status.bind}<button onclick={event: Event => count.value = count.value + 1}>更新状态</button>
    </div>
  }
}