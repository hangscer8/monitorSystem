package nathan

import com.thoughtworks.binding.Binding._
import com.thoughtworks.binding._
import org.scalajs.dom.html._
import org.scalajs.dom.document
import scala.scalajs.js.annotation.JSExport
@JSExport
object WebApp {
  case class Contact(name: Var[String], email: Var[String])

  val data = Vars.empty[Contact]
  @JSExport
  def fun() = {
    dom.render(document.body,table)
  }
  @dom
  def table:Binding[Table]={
    <table class="haha">
      sdsa
    </table>
  }
}