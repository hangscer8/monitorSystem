package nathan.service.user
import nathan.dbentity.EntityTable._
import nathan.dbentity.EntityTable.h2.api._

import scala.concurrent.ExecutionContextExecutor

trait UserDao {
  implicit val ec:ExecutionContextExecutor
  def createUserDBIO(userEntity: UserEntity):DBIO[UserEntity]={
    for{
      _ <- users+=userEntity
    }yield userEntity
  }
}
