package nathan.service.user

import io.circe.generic.auto._
import io.circe.syntax._
import nathan.dbentity.EntityTable._
import nathan.dbentity.EntityTable.h2.api._
import nathan.monitorSystem.Protocols.UserEntity
import nathan.protocol.Protocol.UserReq
import nathan.protocol._
import nathan.monitorSystem.MsgCode._
import scala.concurrent.Future
import scala.util.{Failure, Success}

trait UserTrait extends UserDao {
  def createUser(user: UserReq, auth: Option[String]) = {
    val userEntity = UserEntity(username = user.username, password = user.password, System.currentTimeMillis(), auth)
    db.run(createUserDBIO(userEntity).transactionally.asTry).flatMap {
      case Success(entity) => Future {
        RetMsg(success, entity = entity.asJson.noSpaces)
      }
      case Failure(ex) => Future {
        RetMsg(failure, ex.getMessage)
      }
    }
  }

  def userLogin(userEntity: UserEntity): Future[String] = {
    val q = users.filter(u => u.username === userEntity.username && u.password === userEntity.password).map(u => (u.lastActiveTime, u.auth))
      .update(userEntity.lastActiveTime, userEntity.auth)
    db.run(q.asTry).map {
      case Success(res) => res match {
        case 1 => success
        case _ => failure
      }
      case Failure(ex) => ex.getMessage+""
    }
  }
}