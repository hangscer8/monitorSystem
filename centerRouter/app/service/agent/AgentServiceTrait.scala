package service.agent

import entity.EntityTable._
import entity.EntityTable.h2.api._
import nathan.monitorSystem.Protocols.AgentMachineEntity
import util.ExecutorService._

trait AgentServiceTrait {

  def createOrSetOnLineAgentDBIO(agentMachineEntity: AgentMachineEntity): DBIO[AgentMachineEntity] = {
    for {
      agentExist <- agentMachines.filter(_.agentId === agentMachineEntity.agentId).exists.result
      _ <- agentExist match {
        case true => setAgentOnLine(agentMachineEntity.agentId) //数据库中存在 ，设置在线
        case false => agentMachines += agentMachineEntity //数据库中没有，新建数据
      }
    } yield agentMachineEntity
  }

  def listAgentDBIO: DBIO[Seq[AgentMachineEntity]] = {
    agentMachines.result
  }
  
  def setAgentOffLine(agentId: String): DBIO[Int] = {
    agentMachines.filter(_.agentId === agentId).map(_.isOnLine).update(0)
  }

  def setAgentOnLine(agentId: String): DBIO[Int] = {
    agentMachines.filter(_.agentId === agentId).map(_.isOnLine).update(1)
  }

  def increaseReceiveMsgNumberDBIO(agentId: String): DBIO[Int] = {
    for {
      oldNumber <- agentMachines.filter(_.agentId === agentId).map(_.sendMsgNum).result.head
      e <- agentMachines.filter(_.agentId === agentId).map(a => (a.sendMsgNum, a.lastReceiveMsgTime)).update((oldNumber + 1, System.currentTimeMillis()))
    } yield e
  }

}