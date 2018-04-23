package entity

import conf.PlayConf
import nathan.monitorSystem.Protocols._
import util.UtilTrait

object EntityTable extends UtilTrait {

  val h2 = slick.jdbc.H2Profile

  import h2.api._

  val db = Database.forConfig("h2local")

  class User(_tableTag: Tag) extends Table[UserEntity](_tableTag, "user") {
    def * = (id, username, password, alias, email, lastActiveTime) <> (UserEntity.tupled, UserEntity.unapply)

    val id: Rep[Long] = column[Long]("id", O.Unique)
    val username: Rep[String] = column[String]("username", O.Unique)
    val password: Rep[String] = column[String]("password")
    val alias: Rep[String] = column[String]("alias")
    val email: Rep[String] = column[String]("email")
    val lastActiveTime: Rep[Long] = column[Long]("lastActiveTime") //最后活动时间
    val pk = primaryKey("username_pk", (username))
  }

  val users = new TableQuery(tag => new User(tag))

  class CPUPerc(_tableTag: Tag) extends Table[CPUPercEntity](_tableTag, "cPUPerc") {
    def * = (id, user, sys, _wait, idle, combined, create, agentId) <> (CPUPercEntity.tupled, CPUPercEntity.unapply)

    val id: Rep[Long] = column[Long]("id", O.Unique)
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
    def * = (ip, akkaPort, agentId, cpuVendor, model, sendMsgNum, joinedTime, lastReceiveMsgTime, isOnLine) <> (AgentMachineEntity.tupled, AgentMachineEntity.unapply)

    val ip: Rep[String] = column[String]("ip")
    val akkaPort: Rep[Int] = column[Int]("akkaPort")
    val agentId: Rep[String] = column[String]("agentId", O.PrimaryKey)
    val cpuVendor: Rep[String] = column[String]("cpuVendor")
    val model: Rep[String] = column[String]("model")
    val sendMsgNum: Rep[Long] = column[Long]("sendMsgNum")
    val lastReceiveMsgTime: Rep[Long] = column[Long]("lastReceiveMsgTime")
    val joinedTime: Rep[Long] = column[Long]("joinedTime")
    val isOnLine: Rep[Int] = column[Int]("isOnLine")
  }

  val agentMachines = new TableQuery(tag => new AgentMachine(tag))

  class Mem(_tabletag: Tag) extends Table[MEMEntity](_tabletag, "MEMEntity") {
    def * = (id, total, used, create, agentId) <> (MEMEntity.tupled, MEMEntity.unapply)

    val id: Rep[Long] = column[Long]("id", O.Unique)
    val total: Rep[Double] = column[Double]("total")
    val used: Rep[Double] = column[Double]("used")
    val create: Rep[Long] = column[Long]("create")
    val agentId: Rep[String] = column[String]("agentId")
  }

  val mems = new TableQuery(tag => new Mem(tag))

  class Swap(_tableTag: Tag) extends Table[SWAPEntity](_tableTag, "SWAPEntity") {
    def * = (id, total, used, create, agentId) <> (SWAPEntity.tupled, SWAPEntity.unapply)

    val id: Rep[Long] = column[Long]("id", O.Unique)
    val total: Rep[Double] = column[Double]("total")
    val used: Rep[Double] = column[Double]("used")
    val create: Rep[Long] = column[Long]("create")
    val agentId: Rep[String] = column[String]("agentId")
  }

  val swaps = new TableQuery(tag => new Swap(tag))

  class LoadAvg(_tableTag: Tag) extends Table[LoadAvgEntity](_tableTag, "LoadAvgEntity") {
    def * = (id, `1min`, `5min`, `15min`, create, agentId) <> (LoadAvgEntity.tupled, LoadAvgEntity.unapply)

    val id: Rep[Long] = column[Long]("id", O.Unique)
    val `1min`: Rep[Double] = column[Double]("min1")
    val `5min`: Rep[Double] = column[Double]("min5")
    val `15min`: Rep[Double] = column[Double]("min15")
    val create: Rep[Long] = column[Long]("create")
    val agentId: Rep[String] = column[String]("agentId")
  }

  val loadAvgs = new TableQuery(tag => new LoadAvg(tag))

  class FileUsage(_tableTag: Tag) extends Table[FileUsageEntity](_tableTag, "FileUsageEntity") {
    def * = (id, total, used, create, agentId) <> (FileUsageEntity.tupled, FileUsageEntity.unapply)

    val id: Rep[Long] = column[Long]("id", O.Unique)
    val total: Rep[Double] = column[Double]("total")
    val used: Rep[Double] = column[Double]("used")
    val create: Rep[Long] = column[Long]("create")
    val agentId: Rep[String] = column[String]("agentId")
  }

  val fileUsages = new TableQuery(tag => new FileUsage(tag))

  class AlarmRule(_tableTag: Tag) extends Table[AlarmRuleEntity](_tableTag, "AlarmRuleEntity") {
    def * = (id, agentId, `type`, threshold, condition, appearTimes) <> (AlarmRuleEntity.tupled, AlarmRuleEntity.unapply)

    val id: Rep[Long] = column[Long]("id", O.Unique)
    val agentId: Rep[String] = column[String]("agentId")
    val `type`: Rep[String] = column[String]("type")
    val threshold: Rep[Int] = column[Int]("threshold")
    val condition: Rep[String] = column[String]("condition")
    val appearTimes: Rep[Int] = column[Int]("appearTimes")
  }

  val alarmRules = new TableQuery(tag => new AlarmRule(tag))

  class AlarmEvent(tag: Tag) extends Table[AlarmEventEntity](tag, "AlarmEventEntity") {
    def * = (id, alarmRuleId, message, eventValue, created) <> (AlarmEventEntity.tupled, AlarmEventEntity.unapply)

    val id: Rep[Long] = column[Long]("id", O.Unique)
    val alarmRuleId: Rep[Long] = column[Long]("alarmRuleId")
    val message: Rep[String] = column[String]("message")
    val eventValue: Rep[Double] = column[Double]("eventValue")
    val unit: Rep[String] = column[String]("unit")
    val created: Rep[Long] = column[Long]("created")
  }

  val alarmEvents = new TableQuery(tag => new AlarmEvent(tag))

  {
    if (PlayConf.initDataBase) {
      println("init database:start!!")
      List(users, cpuPercs, agentMachines, mems, swaps, loadAvgs, fileUsages, alarmRules, alarmEvents).foreach { tableQuery =>
        db.run(tableQuery.schema.drop.asTry).exe
        db.run(tableQuery.schema.create.asTry).exe
      }
      println("init database:success!!")
    }
  }
}