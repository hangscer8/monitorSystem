package nathan

import nathan.Protocols.top.mac.ProcessesInfo
package ExtractUtils {

  import nathan.Protocols.top.mac._

  object mac {
    //OSUtils.top().split("\n").drop(number).head.trim
    def extractProcess(str: String) = { //第0行的信息解析
      val Extract =
        """Processes:\s+(\d+)\s+total,\s+(\d+)\s+running,\s+(\d+)\s+stuck,\s+(\d+)\s+sleeping,\s+(\d+)\s+threads\s*""".r
      val Extract(total, running, stuck, sleeping, threads) = str
      ProcessesInfo(total.toInt, running.toInt, stuck.toInt, sleeping.toInt, threads.toInt)
    }

    def extractCollectionTime(str: String) = { //第一行的信息解析
      val Extract =
        """(\d+)/(\d+)/(\d+)\s+(\d+):(\d+):(\d+)\s*""".r
      val Extract(year, month, day, hour, min, sec) = str
      CollectionTimeInfo(year.toInt, month.toInt, day.toInt, hour.toInt, min.toInt, sec.toInt)
    }

    def extractLoadAvg(str: String) = { // 2
      val Extract =
        """Load Avg:\s+(\d+.\d+),\s+(\d+.\d+),\s+(\d+.\d+)\s*""".r
      val Extract(every1Min, every5Min, every15Min) = str
      LoadAvg(every1Min.toDouble, every5Min.toDouble, every15Min.toDouble)
    }

    def extractCPUUsage(str: String) = { //3
      val Extract =
        """CPU usage:\s+(\d+.\d+)%\s+user,\s+(\d+.\d+)%\s+sys,\s+(\d+.\d+)%\s+idle\s*""".r
      val Extract(user, sys, idle) = str
      CPUUsage(user.toDouble / 100, sys.toDouble / 100, idle.toDouble / 100)
    }

    def extractPhysMem(str: String) = { //drop 6
      val Extract =
        """PhysMem:\s+(\d+)M\s+used\s+\((\d+)M\s+wired\),\s+(\d+)M\s+unused.\s*""".r
      val Extract(used, wired, unused) = str
      PhysMem(used.toInt, wired.toInt, unused.toInt)
    }

    def extractJpsItem(str: String) = {
      val Extract ="""(\d+)\s+(\w*)\s*""".r
      val Extract(pid, name) = str
      JpsItem(pid.toInt, name)
    }
  }

}

// * 0次 1次 多次
// ? 0次 1次
// + 1次 多次