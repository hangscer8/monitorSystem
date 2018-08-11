package nathan.service.user

import nathan.dbentity.EntityTable._
import nathan.dbentity.EntityTable.h2.api._
import nathan.monitorSystem.Protocols.UserEntity
import nathan.protocol.Protocol.LoginReq

import scala.concurrent.ExecutionContextExecutor

trait UserDao {
  implicit val ec: ExecutionContextExecutor

  def createUserDBIO(userEntity: UserEntity): DBIO[UserEntity] = {
    for {
      _ <- users += userEntity
    } yield userEntity
  }

  def getUserDBIO(loginReq: LoginReq): DBIO[Option[UserEntity]] = {
    for {
      userOption <- users.filter(u => u.username === loginReq.username)
        .filter(u => u.password === loginReq.password).result.headOption
    } yield userOption
  }
}
