package dao
object EntityTable {
  final case class UserEntity(id: Int, username: String, password: String, userType: String = "1", lastLoginTime: Long)


  val databaseService: DatabaseService = DatabaseService.databaseService

  import databaseService.driver.api._

             

  class User(_tableTag: Tag) extends Table[UserEntity](_tableTag, "user") {
    def * = (id, username, password, userType, lastLoginTime) <> (UserEntity.tupled, UserEntity.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(username), Rep.Some(password), Rep.Some(userType), Rep.Some(lastLoginTime)).shaped.<>({ r => import r._; _1.map(_ => UserEntity.tupled((_1.get, _2.get, _3.get, _4.get, _5.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))


    /** Database column id SqlType(INT), PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.PrimaryKey)
    /** Database column username SqlType(VARCHAR), Length(2000,true) */
    val username: Rep[String] = column[String]("username")
    /** Database column password SqlType(VARCHAR), Length(2000,true) */
    val password: Rep[String] = column[String]("password")
    /** Database column userType SqlType(ENUM), Length(2,false), Default(1) */
    val userType: Rep[String] = column[String]("userType", O.Default("1"))
    /** Database column lastLoginTime SqlType(BIGINT) */
    val lastLoginTime: Rep[Long] = column[Long]("lastLoginTime")
  }

  val users = new TableQuery(tag => new User(tag))
}
