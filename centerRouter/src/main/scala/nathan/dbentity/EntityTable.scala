package nathan.dbentity

object EntityTable {
  val h2 = slick.jdbc.H2Profile

  import h2.api._

  val db = Database.forConfig("h2local")

  case class UserEntity(id: Long, username: String, password: String, `type`: String, lastActiveTime: Long, auth: Option[String])

  class User(_tableTag: Tag) extends Table[UserEntity](_tableTag, "user") {
    def * = (id, username, password, `type`, lastActiveTime,auth) <> (UserEntity.tupled, UserEntity.unapply)

    val id: Rep[Long] = column[Long]("id")
    val username: Rep[String] = column[String]("username", O.Unique)
    val password: Rep[String] = column[String]("password")
    val `type`: Rep[String] = column[String]("type")
    val lastActiveTime: Rep[Long] = column[Long]("lastActiveTime") //最后活动时间
    val auth: Rep[Option[String]] = column[Option[String]]("auth", O.Default(None))
    val pk = primaryKey("id_pk", (id))
  }

  val users = new TableQuery(tag => new User(tag))
}