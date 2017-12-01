package nathan.dbentity
import slick.jdbc.H2Profile.api._
object EntityTable {

  case class UserEntity(id: Long, username: String, password: String, `type`: String, lastLoginTime: Long)

  class User(_tableTag: Tag) extends Table[UserEntity](_tableTag, "user") {
    def * = (id, username, password, `type`, lastLoginTime)
    def id: Rep[Long]=
    def username: String
    def password: String
    def `type`: String
    def lastLoginTime: Long
  }

}