package nathan

import java.util.Date

import com.thoughtworks.binding.Binding._
import com.thoughtworks.binding._
import org.scalajs.dom.document
import org.scalajs.dom.html._

import scala.concurrent.duration._
import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.timers

@JSExport
object WebApp {
  nathan.monitorSystem.MsgCode.failure

  @JSExport
  def fun() = {
    dom.render(document.body, genRender())
  }

  @dom
  def genRender(): Binding[Div] = {
    <div>
      现在时间:
      {now.bind.toString}{introductionDiv.bind}{typedButton.bind}{randomParagraph.bind}{myCustomDiv.bind}
    </div>
  }

  @dom def introductionDiv: Binding[Div] = {
    <div style="font-size:0.8em">
      <h3>Binding.scala的优点</h3>
      <ul>
        <li>简单</li>
        <li>概念少</li>
        <li>功能多</li>
      </ul>
    </div>
  }

  @dom def typedButton: Binding[Button] = {
    <button>按钮</button>
  }

  @dom def randomParagraph: Binding[Paragraph] = {
    <p>生产一个随机数:
      {math.random().toString}
    </p>
  }

  @dom def myCustomDiv: Binding[Div] = {
    <div data:hah="😄asdsa"></div>
  }

  val now = Var(new Date)
  timers.setInterval(1 seconds) {
    now.value = new Date()
  }

}