package service.agent

import entity.EntityTable._
import entity.EntityTable.h2.api._
import nathan.monitorSystem.Protocols.AgentMachineEntity
import util.ExecutorService._

trait AgentServiceTrait {

  def createAgentDBIO(agentMachineEntity: AgentMachineEntity): DBIO[AgentMachineEntity] = {
    for {
      _ <- agentMachines += agentMachineEntity
    } yield agentMachineEntity
  }

  def listAgentDBIO: DBIO[Seq[AgentMachineEntity]] = {
    agentMachines.result
  }

  def deleteAgentDBIO(id: Long): DBIO[Int] = {
    agentMachines.filter(_.id === id).delete
  }

  def increaseReceiveMsgNumberDBIO(agentId: String): DBIO[Int] = {
    for {
      oldNumber <- agentMachines.filter(_.agentId === agentId).map(_.sendMsgNum).result.head
      e <- agentMachines.filter(_.agentId === agentId).map(a => (a.sendMsgNum, a.lastReceiveMsgTime)).update((oldNumber + 1, System.currentTimeMillis()))
    } yield e
  }
}