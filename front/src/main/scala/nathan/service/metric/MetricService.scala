package nathan.service.metric

import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax._
import nathan.monitorSystem.Protocols.UserEntity
import nathan.util.{CommonUtilTrait, Snowflake}
import org.scalajs.dom._
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.ext.Ajax.InputData
import org.scalajs.dom.ext.Ajax.InputData._
import org.scalajs.dom.html.{Button, Form, Image, Input}

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js.annotation.JSExport
import scala.util.{Failure, Success}

@JSExport
object MetricService extends CommonUtilTrait {
  @JSExport
  def cpuService(): Unit = {

  }
}