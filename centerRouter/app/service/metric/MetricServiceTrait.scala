package service.metric

import entity.EntityTable._
import h2.api._
import nathan.monitorSystem.Protocols._
import util.ExecutorService._
import util.UtilTrait

import scala.util.Try

trait MetricServiceTrait extends UtilTrait {
  /** *
    * create metric
    */
  def createCPUPercDBIO(cPUPercEntity: CPUPercEntity): DBIO[CPUPercEntity] = {
    for {
      _ <- cpuPercs += cPUPercEntity
    } yield cPUPercEntity
  }

  def createMEMDBIO(mEMEntity: MEMEntity): DBIO[MEMEntity] = {
    for {
      _ <- mems += mEMEntity
    } yield mEMEntity
  }

  def createSWAPDBIO(sWAPEntity: SWAPEntity): DBIO[SWAPEntity] = {
    for {
      _ <- swaps += sWAPEntity
    } yield sWAPEntity
  }

  def createLoadAvgDBIO(loadAvgEntity: LoadAvgEntity): DBIO[LoadAvgEntity] = {
    for {
      _ <- loadAvgs += loadAvgEntity
    } yield loadAvgEntity
  }

  def createFileUsageDBIO(fileUsageEntity: FileUsageEntity): DBIO[FileUsageEntity] = {
    for {
      _ <- fileUsages += fileUsageEntity
    } yield fileUsageEntity
  }

  /** ******/
  def executeMetricAction(io: DBIO[BaseAgentInfo]): Try[BaseAgentInfo] = {
    db.run(io.transactionally.asTry).exe
  }

  def createMetric(info: BaseAgentInfo): Try[BaseAgentInfo] = {
    val io = info match {
      case x: CPUPercEntity => createCPUPercDBIO(x)
      case x: MEMEntity => createMEMDBIO(x)
      case x: SWAPEntity => createSWAPDBIO(x)
      case x: LoadAvgEntity => createLoadAvgDBIO(x)
      case x: FileUsageEntity => createFileUsageDBIO(x)
    }
    executeMetricAction(io)
  }

  def cpuPercSeqDBIO(agentId: String, size: Int): DBIO[Seq[CPUPercEntity]] = {
    cpuPercs.filter(_.agentId === agentId).sortBy(_.create.desc).take(size).result
  }

  def swapSeqDBIO(agentId: String, size: Int): DBIO[Seq[SWAPEntity]] = {
    swaps.filter(_.agentId === agentId).sortBy(_.create.desc).take(size).result
  }

  def memSeqDBIO(agentId: String, size: Int): DBIO[Seq[MEMEntity]] = {
    mems.filter(_.agentId === agentId).sortBy(_.create.desc).take(size).result
  }

  def loadAvgSeqDBIO(agentId: String, size: Int): DBIO[Seq[LoadAvgEntity]] = {
    loadAvgs.filter(_.agentId === agentId).sortBy(_.create.desc).take(size).result
  }

  def fileSeqDBIO(agentId: String, size: Int): DBIO[Seq[FileUsageEntity]] = {
    fileUsages.filter(_.agentId === agentId).sortBy(_.create.desc).take(size).result
  }
}