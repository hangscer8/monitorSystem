package nathan.util

import nathan.dbentity.EntityTable._
import nathan.dbentity.EntityTable.h2.api._

import scala.concurrent.Await
import scala.concurrent.duration._

object UserSupport {
  def isLoginUser(auth: Option[String]) = { //用户凭据 是否登录
    Await.result(db.run(users.filter(_.auth === auth).result.headOption), 10 seconds) match {
      case Some(_) => true
      case _ => false
    }
  }
}
