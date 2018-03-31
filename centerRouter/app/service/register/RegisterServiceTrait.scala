package service.register

import entity.EntityTable._
import h2.api._
import nathan.monitorSystem.Protocols.UserEntity
import util.ExecutorService._
trait RegisterServiceTrait {
  def registerUserDBIO(userEntity: UserEntity): DBIO[UserEntity] = {
    for {
      _ <- users += userEntity
    } yield userEntity
  }
}