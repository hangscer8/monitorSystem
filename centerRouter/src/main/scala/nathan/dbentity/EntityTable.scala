package nathan.dbentity

import slick.jdbc.H2Profile.api._

object EntityTable {

  case class UserEntity(id: Long, username: String, password: String, `type`: String, lastLoginTime: Long)

  class User(_tableTag: Tag) extends Table[UserEntity](_tableTag, "user") {
    def * = (id, username, password, `type`, lastLoginTime) <> (UserEntity.tupled, UserEntity.unapply)

    val id: Rep[Long] = column[Long]("id")

    val username: Rep[String] = column[String]("username", O.Unique)

    val password: Rep[String] = column[String]("password")

    val `type`: Rep[String] = column[String]("type")

    val lastLoginTime: Rep[Long] = column[Long]("lastLoginTime")

    val pk = primaryKey("id_pk", (id))
  }

  val users = new TableQuery(tag => new User(tag))
}