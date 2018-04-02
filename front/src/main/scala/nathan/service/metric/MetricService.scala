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
import scala.scalajs.js
import scala.scalajs.js.Date
import scala.scalajs.js.annotation._
import scala.util.{Failure, Success}

@JSExport
object MetricService extends CommonUtilTrait {
  @JSExport
  def cpuService(): Unit = {
    val showChart = js.Dynamic.global.showChart.as[js.Function4[String, String, String, String, Unit]]
    val data =
      s"""
         |[
         |                            {
         |                                type: 'area',
         |                                name: '美元兑欧元',
         |                                data: [
         |                                    [Date.UTC(2015, 4, 20), 0.9014],
         |                                    [Date.UTC(2015, 4, 21), 0.8999],
         |                                    [Date.UTC(2015, 4, 22), 0.9076],
         |                                    [Date.UTC(2015, 4, 24), 0.9098],
         |                                    [Date.UTC(2015, 4, 25), 0.9110],
         |                                    [Date.UTC(2015, 4, 26), 0.9196],
         |                                    [Date.UTC(2015, 4, 27), 0.9170],
         |                                    [Date.UTC(2015, 4, 28), 0.9133],
         |                                    [Date.UTC(2015, 4, 29), 0.9101],
         |                                    [Date.UTC(2015, 4, 31), 0.9126],
         |                                    [Date.UTC(2015, 5, 1), 0.9151],
         |                                    [Date.UTC(2015, 5, 2), 0.8965],
         |                                    [Date.UTC(2015, 5, 3), 0.8871],
         |                                    [Date.UTC(2015, 5, 4), 0.8898],
         |                                    [Date.UTC(2015, 5, 5), 0.8999],
         |                                    [Date.UTC(2015, 5, 7), 0.9004],
         |                                    [Date.UTC(2015, 5, 8), 0.8857]]
         |                            },
         |                            {
         |                                type: 'area',
         |                                name: '美元兑欧元123',
         |                                data: [
         |                                    [Date.UTC(2015, 4, 20), 1.9014],
         |                                    [Date.UTC(2015, 4, 21), 2.8999],
         |                                    [Date.UTC(2015, 4, 22), 3.9076],
         |                                    [Date.UTC(2015, 4, 24), 4.9098],
         |                                    [Date.UTC(2015, 4, 25), 5.9110],
         |                                    [Date.UTC(2015, 4, 26), 6.9196],
         |                                    [Date.UTC(2015, 4, 27), 7.9170],
         |                                    [Date.UTC(2015, 4, 28), 8.9133],
         |                                    [Date.UTC(2015, 4, 29), 9.9101],
         |                                    [Date.UTC(2015, 4, 31), 1.9126],
         |                                    [Date.UTC(2015, 5, 1), 2.9151],
         |                                    [Date.UTC(2015, 5, 2), 3.8965],
         |                                    [Date.UTC(2015, 5, 3), 4.8871],
         |                                    [Date.UTC(2015, 5, 4), 5.8898],
         |                                    [Date.UTC(2015, 5, 5), 6.8999],
         |                                    [Date.UTC(2015, 5, 7), 7.9004],
         |                                    [Date.UTC(2015, 5, 8), 7.8857]]
         |                            }
         |                        ]
       """.stripMargin
    showChart("con", data, "试一试", "汇率adasd")
  }
}