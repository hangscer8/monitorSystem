package nathan.dbentity

import nathan.dbentity.EntityTable._
import h2.api._
import nathan.dbentity.Protocol.{DefaultAuth, UserTypeConst}
import nathan.ec.ExecutorService.ec
import nathan.util.s.com.eoi.util.Snowflake
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object DbInit {
  def init = {
    val futures = Seq(
      users.schema.create >> (users += UserEntity(Snowflake.nextId(), "jianghang", "password", UserTypeConst.admin, System.currentTimeMillis(), Some(DefaultAuth.auth)))
    ).map { action =>
      db.run(action.transactionally.asTry)
    }
    println(Await.result(Future.sequence(futures), 10 seconds))
  }
}