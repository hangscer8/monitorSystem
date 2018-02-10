package nathan.service.cpu

import nathan.dbentity.EntityTable._
import nathan.dbentity.EntityTable.h2.api._
import nathan.monitorSystem.Protocols._

import scala.concurrent.Future

trait CpuPercDao extends CpuPercTrait {
  def getCPUPerc(agentId: String, size: Long): Future[Seq[CPUPercEntity]] = {
    val q = cpuPercs.filter(_.agentId === agentId).sortBy(_.create.desc).take(size).sortBy(_.create.asc)
    db.run(q.result)
  }
}