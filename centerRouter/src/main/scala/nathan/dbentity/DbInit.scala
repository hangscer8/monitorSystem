package nathan.dbentity

import nathan.dbentity.EntityTable._
import h2.api._
import nathan.ec.ExecutorService.ec
import scala.concurrent.Future

object DbInit {
  def init = {
    val dbInit = users.schema.create
    db.run(dbInit.asTry).flatMap{r=>Future{r}}
  }
}