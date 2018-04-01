package util

import java.util.Date

import service.agent.AgentServiceTrait
import entity.EntityTable._
import nathan.monitorSystem.Protocols.AgentMachineEntity

object HtmlUtil extends AgentServiceTrait with UtilTrait {
  def timeShow(time: Long): String = {
    val date = new Date(time)
    s"${date.getYear + 1900}.${date.getMonth() + 1}.${date.getDate()}.${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}"
  }

  def listOfAgent: Seq[AgentMachineEntity] = {
    db.run(listAgentDBIO).exe
  }
}