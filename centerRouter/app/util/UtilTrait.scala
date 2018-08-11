package util

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

trait UtilTrait {
  //无状态的工具的汇总
  val loginKey = "isLogin"

  def reNameFile(file: String): String = { //上传文件的重命名
    val EXTRACT =
      """([^.]*).?(.*)""".r
    val EXTRACT(a1, a2) = file
    s"${a1}${scala.util.Random.alphanumeric.take(15).mkString}.${a2}"
  }

  implicit class FutureOps[A](f: Future[A]) {
    def exe: A = {
      Await.result(f, 40 seconds)
    }
  }

}