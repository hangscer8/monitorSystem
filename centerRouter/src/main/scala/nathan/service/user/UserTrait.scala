package nathan.service.user

import io.circe.generic.auto._
import io.circe.syntax._
import nathan.dbentity.EntityTable._
import nathan.dbentity.EntityTable.h2.api._
import nathan.monitorSystem.Protocols.UserEntity
import nathan.protocol.Protocol.UserReq
import nathan.protocol._

import scala.concurrent.Future
import scala.util.{Failure, Success}

trait UserTrait extends UserDao {
  def createUser(user: UserReq, auth: Option[String]) = {
    val userEntity = UserEntity(username = user.username, password = user.password, System.currentTimeMillis(), auth)
    db.run(createUserDBIO(userEntity).transactionally.asTry).flatMap {
      case Success(entity) => Future {
        RetMsg(CustomerCodes.success, entity = entity.asJson.noSpaces)
      }
      case Failure(ex) => Future {
        RetMsg(CustomerCodes.error, ex.getMessage)
      }
    }
  }

}
