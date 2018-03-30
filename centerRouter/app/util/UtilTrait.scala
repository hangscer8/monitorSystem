package util

trait UtilTrait {
  //无状态的工具的汇总
  def reNameFile(file: String): String = { //上传文件的重命名
    val EXTRACT =
      """([^.]*).?(.*)""".r
    val EXTRACT(a1, a2) = file
    s"${a1}${scala.util.Random.alphanumeric.take(15).mkString}.${a2}"
  }
}