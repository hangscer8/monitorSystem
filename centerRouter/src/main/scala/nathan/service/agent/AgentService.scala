package nathan.service.agent

import nathan.dbentity.EntityTable._
import nathan.dbentity.EntityTable.h2.api._
import nathan.monitorSystem.Protocols._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait AgentService extends AgentDao {
  def addAgent(): Unit = {}

  def getAgentList: Future[Seq[AgentMachineEntity]] = {
    db.run(agentMachines.sortBy(_.joinedTime.desc).result)
  }
}