package service

import json.Protocol._
import org.scalajs.dom._
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.ext.Ajax.InputData
import service.Protocol._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
trait AgentService{
  def agentPost(agent: Agent) = {
    Ajax.post(baseUrl / prefix / "agent", InputData.str2ajax("""{"host":"127.0.0.1","port":2552}""")).onSuccess { case xhr =>
      console.log(xhr.responseText)
    }
  }
}
