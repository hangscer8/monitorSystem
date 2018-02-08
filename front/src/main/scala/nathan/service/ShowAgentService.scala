package nathan.service

import com.thoughtworks.binding.{Binding, dom}
import nathan.ui.{NavBar, ShowAgentUI}
import org.scalajs.dom.{Node, document}

import scala.scalajs.js.annotation.JSExport

@JSExport
object ShowAgentService {
  @JSExport
  def render(): Unit = {
    dom.render(document.body, genMainDiv)
  }

  @dom def genMainDiv: Binding[Node] = {
    <div>
      {NavBar.nav.bind}{ShowAgentUI.showAgentTableCom.bind}
    </div>
  }
}
