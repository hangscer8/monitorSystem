package nathan.service.user

import nathan.dbentity.EntityTable._
import nathan.dbentity.EntityTable.h2.api._
import nathan.protocol.Protocol.{LoginReq, UserReq}
import nathan.protocol._
import io.circe.generic.auto._
import io.circe.syntax._
import nathan.util.s.com.eoi.util.Snowflake

import scala.concurrent.Future
import scala.util.{Failure, Success}

trait RegisterTrait {
  def isUserNameExits(userName: String): Future[Boolean] = {
    db.run(users.filter(_.username === userName).exists.result)
  }
}