package nathan.dbentity

import nathan.monitorSystem.Protocols._

object EntityTable {
  val h2 = slick.jdbc.H2Profile

  import h2.api._

  val db = Database.forConfig("h2local")

  class User(_tableTag: Tag) extends Table[UserEntity](_tableTag, "user") {
    def * = (username, password, lastActiveTime, auth) <> (UserEntity.tupled, UserEntity.unapply)

    val username: Rep[String] = column[String]("username", O.Unique)
    val password: Rep[String] = column[String]("password")
    val lastActiveTime: Rep[Long] = column[Long]("lastActiveTime") //最后活动时间
    val auth: Rep[Option[String]] = column[Option[String]]("auth", O.Default(None))
    val pk = primaryKey("username_pk", (username))
  }

  val users = new TableQuery(tag => new User(tag))

  class CPUPerc(_tableTag: Tag) extends Table[CPUPercEntity](_tableTag, "cPUPerc") {
    def * = (user, sys, _wait, idle, combined, create, agentId) <> (CPUPercEntity.tupled, CPUPercEntity.unapply)

    val user: Rep[Double] = column[Double]("user")
    val sys: Rep[Double] = column[Double]("sys")
    val _wait: Rep[Double] = column[Double]("_wait")
    val idle: Rep[Double] = column[Double]("idle")
    val combined: Rep[Double] = column[Double]("combined")
    val create: Rep[Long] = column[Long]("create")
    val agentId: Rep[String] = column[String]("agentId")
  }

  val cpuPercs = new TableQuery(tag => new CPUPerc(tag))

  class AgentMachine(_tableTag: Tag) extends Table[AgentMachineEntity](_tableTag, "agentMachine") {
    def * = (ip, akkaPort, agentId, cpuCacheSize, cpuVendor, cpuMhz, model, sendMsgNum, joinedTime) <> (AgentMachineEntity.tupled, AgentMachineEntity.unapply)

    val ip: Rep[String] = column[String]("ip")
    val akkaPort: Rep[Int] = column[Int]("akkaPort")
    val agentId: Rep[String] = column[String]("agentId", O.PrimaryKey)
    val cpuCacheSize: Rep[Long] = column[Long]("cpuCacheSize")
    val cpuVendor: Rep[String] = column[String]("cpuVendor")
    val cpuMhz: Rep[Int] = column[Int]("cpuMhz")
    val model: Rep[String] = column[String]("model")
    val sendMsgNum: Rep[Long] = column[Long]("sendMsgNum")
    val joinedTime: Rep[Long] = column[Long]("joinedTime")
  }

  val agentMachines = new TableQuery(tag => new AgentMachine(tag))
}