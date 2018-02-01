package nathan.service.cpu

import nathan.dbentity.EntityTable._
import nathan.dbentity.EntityTable.h2.api._
import nathan.monitorSystem.Protocols._

import scala.concurrent.Future

trait CpuPercDao {
  def getCPUPerc(agentId: String, startOPt: Option[Long], endOPt: Option[Long]): Future[Seq[CPUPercEntity]] = {
    val start = startOPt.getOrElse(0L)
    val q = endOPt match {
      case Some(end) => cpuPercs.filter(_.create <= end).filter(_.create >= start).filter(_.agentId === agentId)
      case None => cpuPercs.filter(_.create >= start).filter(_.agentId === agentId)
    }
    db.run(q.result)
  }
}