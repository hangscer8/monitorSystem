package service.login

import entity.EntityTable._
import h2.api._
import nathan.monitorSystem.Protocols.UserEntity
import util.ExecutorService._

trait LoginServiceTrait {
  def userExistDBIO(username: String, password: String): DBIO[Boolean] = {
    users.filter(u => u.username === username && u.password === password).exists.result
  }
}