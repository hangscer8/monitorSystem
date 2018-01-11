package nathan
package ExtractUtils {

  import nathan.Protocols.monitorJdk.JpsItem

  object mac {





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