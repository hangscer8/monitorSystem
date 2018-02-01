package nathan

import org.scalajs.dom //最好不用使用import dom._

import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._

@JSExport
object WebApp {
  @JSExport
  def fun() = {
    val mainbody = html(
      head(
        script("some script")
      ),
      body(
        h1("This is my title"),
        div(
          p("This is my first paragraph"),
          p("This is my second paragraph")
        )
      )
    )
    println(mainbody.render.innerHTML)
    dom.document.body.innerHTML = mainbody.render.innerHTML
  }
}