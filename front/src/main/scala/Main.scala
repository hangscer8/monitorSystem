package nathan
import entity.Agent
import org.scalajs.dom
import org.scalajs.dom.html
import service.user.AgentService
import util.LoginSupport

import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._
@JSExport
object MainApp extends AgentService with LoginSupport{
  val box=input(
    `type`:="text",
    placeholder:="Type1234 here!"
  ).render
  val output=span.render
  box.onkeyup=(_:dom.Event)=>{
    output.textContent=box.value.toUpperCase
  }
  @JSExport
  def init()={
    setAuth()
  }
  @JSExport
  def hello(target:html.Div)={
    target.appendChild(
      div(
        h1("hello world!"),
        p("Type here and have it capitlized!"),
        div(box),
        div(output)
      ).render
    )
  }
}