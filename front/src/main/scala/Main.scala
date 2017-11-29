package nathan
import org.scalajs.dom
import org.scalajs.dom.html
import service.AgentService
import json.Protocol.Agent

import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._
@JSExport
object MainApp extends AgentService{
  val box=input(
    `type`:="text",
    placeholder:="Type1234 here!"
  ).render
  val output=span.render
  box.onkeyup=(_:dom.Event)=>{
    output.textContent=box.value.toUpperCase
  }
  @JSExport
  def test()={
    agentPost(Agent("127.0.0.1",2552))
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