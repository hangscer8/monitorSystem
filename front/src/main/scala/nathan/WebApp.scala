package nathan

import org.scalajs.dom

import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._
import nathan.util.implicitDecoder._
import org.scalajs.dom.html

@JSExport
object WebApp {
  @JSExport
  def fun() = {
    val mainbody = div(
      h1("This is my title"),
      div(
        p("This is my first paragraph"),
        p("This is my second paragraph")
      )
    )
    dom.document.body.appendChild(mainbody.render)
    dom.document.body.appendChild(mainbody.render)
    dom.window.setTimeout(()=>{
      dom.document.childNodes.foreach{node=>
        node.parentNode.removeChild(node)
      }
    },3000)
  }
}