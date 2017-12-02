package service.user

import entity.Agent
import io.circe.generic.auto._
import io.circe.syntax._
import org.scalajs.dom._
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.ext.Ajax.InputData
import util.CommonConst._
import util.CommonUtil._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
trait AgentService {
  def agentPost(agent: Agent) = {
    Ajax.post(baseUrl / prefix / "agent", InputData.str2ajax(agent.asJson.spaces2),headers = Map("Content-Type"->"application/json",authHead->DefaultAuth.authValue)).onSuccess { case xhr =>
      console.log(xhr.responseText)
    }
  }
}
