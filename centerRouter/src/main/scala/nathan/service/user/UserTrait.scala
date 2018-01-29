package nathan.service.user

import nathan.dbentity.EntityTable._
import nathan.dbentity.EntityTable.h2.api._
import nathan.protocol.Protocol.{LoginReq, UserReq}
import nathan.protocol._
import io.circe.generic.auto._
import io.circe.syntax._
import scala.concurrent.Future
import scala.util.{Failure, Success}

trait UserTrait extends UserDao {
  def createUser(user: UserReq, auth: Option[String]) = {
    val userEntity = UserEntity(id = Snowflake.nextId(), username = user.username, password = user.password, user.`type`, System.currentTimeMillis(), auth)
    db.run(createUserDBIO(userEntity).transactionally.asTry).flatMap {
      case Success(entity) => Future {
        RetMsg(CustomerCodes.success, entity = entity.asJson.noSpaces)
      }
      case Failure(ex) => Future {
        RetMsg(CustomerCodes.error, ex.getMessage)
      }
    }
  }

  def login(loginReq: LoginReq, auth: Option[String]) = {
    val q = for {
      userOption <- getUserDBIO(loginReq)
      _ <- userOption match {
        case None => DBIO.failed(new Exception("用户不存在!!"))
        case Some(_) => DBIO.successful(None)
      } if userOption.isDefined
      _ <- users.filter(_.id === userOption.get.id).map(u => u.auth).update(auth)
      userEntity <- users.filter(_.id === userOption.get.id).result.head
    } yield userEntity
        db.run(q.transactionally.asTry).flatMap {
          case Success(userEntity) => Future {
            RetMsg(CustomerCodes.success, userEntity.asJson.spaces2)
          }
          case Failure(ex) => Future {
            RetMsg(CustomerCodes.error, ex.getMessage)
          }
        }
  }
}
