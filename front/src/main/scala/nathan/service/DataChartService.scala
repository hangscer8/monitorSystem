package nathan.service

import com.thoughtworks.binding.Binding.{BindingSeq, Var, Vars}
import com.thoughtworks.binding.{dom, _}
import io.circe.parser.decode
import nathan.monitorSystem.AkkaSystemConst._
import nathan.monitorSystem.Protocols.{CPUPercEntity, RegisterReq, UserEntity}
import nathan.util.implicitUtil._
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.html.{Input, _}
import org.scalajs.dom.{document, _}
import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax._
import nathan.monitorSystem.MsgCode._
import nathan.ui.{AddAgentServiceUI, CPUPercUI, NavBar}
import nathan.util.HttpHeadSupport
import nathan.util.CommonUtil.setAuth
import nathan.monitorSystem.AkkaSystemConst._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom.ext.Ajax.InputData._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.concurrent.Future

@JSExport
object DataChartService { //数据报表
  @JSExport
  def render(): Unit = {
    dom.render(document.body, CPUPercUI.cpuChart("asd"))
  }
}