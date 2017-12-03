package nathan.service.user
import nathan.dbentity.EntityTable._
import h2.api._
import nathan.protocol.Protocol.UserReq
import nathan.util.s.com.eoi.util.Snowflake
import nathan.protocol._
import scala.concurrent.Future
import scala.util.{Failure, Success}
trait UserTrait extends UserDao{
  def createUser(user:UserReq,auth:Option[String])= {
    val userEntity = UserEntity(id = Snowflake.nextId(), username = user.username, password = user.password, user.type1, System.currentTimeMillis(), auth)
    db.run(createUserDBIO(userEntity).transactionally.asTry).flatMap{
      case Success(entity) =>Future{
        RetSuccess(CustomerCodes.success,entity =entity )
      }
      case Failure(ex)=> Future{
        RetFail(CustomerCodes.error,ex.getMessage)
      }
    }
  }
}
