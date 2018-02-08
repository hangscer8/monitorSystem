package nathan.service

import com.thoughtworks.binding.Binding.{BindingSeq, Var}
import com.thoughtworks.binding.{dom, _}
import io.circe.parser.decode
import nathan.monitorSystem.AkkaSystemConst._
import nathan.monitorSystem.Protocols.{RegisterReq, UserEntity}
import nathan.util.implicitUtil._
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.html.{Input, _}
import org.scalajs.dom.{document, _}
import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax._
import nathan.monitorSystem.MsgCode._
import nathan.ui.{AddAgentServiceUI, NavBar}
import nathan.util.HttpHeadSupport
import nathan.util.CommonUtil.setAuth

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom.ext.Ajax.InputData._

import scala.concurrent.Future

@JSExport
object IndexService {
  @JSExport
  def render(): Unit = {
    dom.render(document.body, genMainDev)
  }

  @dom def genMainDev: Binding[Div] = {
    <div>
      {NavBar.nav.bind}
    </div>
  }
}
