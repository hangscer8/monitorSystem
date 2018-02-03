package nathan

import com.thoughtworks.binding.Binding._
import com.thoughtworks.binding._
import nathan.util.implicitDecoder._
import org.scalajs.dom.html._
import org.scalajs.dom.{Event, document}

import scala.scalajs.js.annotation.JSExport

@JSExport
object WebApp {


  @JSExport
  def fun() = {
    dom.render(document.body, genRender())
  }

  @dom
  def genRender(): Binding[Form] = {
    val logs = Vars("Input code:")
    val input = <input type="text"/>.as[Input]
    val submitHandler = { event: Event =>
      event.preventDefault()
      logs.value += input.value
      input.value = ""//我也不知道为什么把这句话去掉后，则编译错误
    }
    <form
    onsubmit={submitHandler}>
      {for (log <- logs) yield
      <div>
        {log}
      </div>}{input}
    </form>
  }
}