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
    def * = (user, sys, idle, create, agentId) <> (CPUPercEntity.tupled, CPUPercEntity.unapply)

    val user: Rep[Double] = column[Double]("user")
    val sys: Rep[Double] = column[Double]("sys")
    val idle: Rep[Double] = column[Double]("idle")
    val create: Rep[Long] = column[Long]("create")
    val agentId: Rep[String] = column[String]("agentId")
  }

  val cpuPercs = new TableQuery(tag => new CPUPerc(tag))

  class AgentMachine(_tableTag: Tag) extends Table[AgentMachineEntity](_tableTag, "agentMachine") {
    def * = (ip, akkaPort, agentId, cpuVendor, model, sendMsgNum, joinedTime) <> (AgentMachineEntity.tupled, AgentMachineEntity.unapply)

    val ip: Rep[String] = column[String]("ip")
    val akkaPort: Rep[Int] = column[Int]("akkaPort")
    val agentId: Rep[String] = column[String]("agentId", O.PrimaryKey)
    val cpuVendor: Rep[String] = column[String]("cpuVendor")
    val model: Rep[String] = column[String]("model")
    val sendMsgNum: Rep[Long] = column[Long]("sendMsgNum")
    val joinedTime: Rep[Long] = column[Long]("joinedTime")
  }

  val agentMachines = new TableQuery(tag => new AgentMachine(tag))

  class Mem(_tabletag: Tag) extends Table[MEMEntity](_tabletag, "MEMEntity") {
    def * = (total, used, create, agentId) <> (MEMEntity.tupled, MEMEntity.unapply)

    val total: Rep[Double] = column[Double]("total")
    val used: Rep[Double] = column[Double]("used")
    val create: Rep[Long] = column[Long]("create")
    val agentId: Rep[String] = column[String]("agentId")
  }

  val mems = new TableQuery(tag => new Mem(tag))

  class Swap(_tableTag: Tag) extends Table[SWAPEntity](_tableTag, "SWAPEntity") {
    def * = (total, used, create, agentId) <> (SWAPEntity.tupled, SWAPEntity.unapply)

    val total: Rep[Double] = column[Double]("total")
    val used: Rep[Double] = column[Double]("used")
    val create: Rep[Long] = column[Long]("create")
    val agentId: Rep[String] = column[String]("agentId")
  }

  val swaps = new TableQuery(tag => new Swap(tag))

  class LoadAvg(_tableTag: Tag) extends Table[LoadAvgEntity](_tableTag, "LoadAvgEntity") {
    def * = (`1min`, `5min`, `15min`, create, agentId) <> (LoadAvgEntity.tupled, LoadAvgEntity.unapply)

    val `1min`: Rep[Double] = column[Double]("min1")
    val `5min`: Rep[Double] = column[Double]("min5")
    val `15min`: Rep[Double] = column[Double]("min15")
    val create: Rep[Long] = column[Long]("create")
    val agentId: Rep[String] = column[String]("agentId")
  }

  val loadAvgs = new TableQuery(tag => new LoadAvg(tag))

  class FileUsage(_tableTag: Tag) extends Table[FileUsageEntity](_tableTag, "FileUsageEntity") {
    def * = (total, used, create, agentId) <> (FileUsageEntity.tupled, FileUsageEntity.unapply)

    val total: Rep[Double] = column[Double]("total")
    val used: Rep[Double] = column[Double]("used")
    val create: Rep[Long] = column[Long]("create")
    val agentId: Rep[String] = column[String]("agentId")
  }

  val fileUsages = new TableQuery(tag => new FileUsage(tag))

  class NetInfo(_tableTag: Tag) extends Table[NetInfoEntity](_tableTag, "NetInfoEntity") {
    def * = (netSpeed, create, agentId) <> (NetInfoEntity.tupled, NetInfoEntity.unapply)

    val netSpeed: Rep[Long] = column[Long]("netSpeed")
    val create: Rep[Long] = column[Long]("create")
    val agentId: Rep[String] = column[String]("agentId")
  }

  val netInfos = new TableQuery(tag => new NetInfo(tag))
}