package nathan.dbentity

import nathan.dbentity.EntityTable._
import nathan.dbentity.EntityTable.h2.api._
import nathan.dbentity.Protocol.{DefaultAuth, UserTypeConst}
import nathan.ec.ExecutorService.ec
import nathan.util.s.com.eoi.util.Snowflake

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.Try

object DbInit {
  def init = {
    val future2 = Seq(users.schema.drop, cpuPercs.schema.drop, agentMachines.schema.drop).foreach { action =>
      println(action.statements.mkString("") + " ,result:" + Try(Await.result(db.run(action), 3 seconds)))
    }
    val futures = Seq(
      users.schema.create >>
        (users += UserEntity(Snowflake.nextId(), "jianghang", "password", UserTypeConst.admin, System.currentTimeMillis(), Some(DefaultAuth.auth))) >>
        cpuPercs.schema.create >> agentMachines.schema.create
    ).map { action =>
      db.run(action.transactionally.asTry)
    }
    println(Await.result(Future.sequence(futures), 10 seconds))
  }
}