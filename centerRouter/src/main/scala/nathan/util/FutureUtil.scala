package nathan.util

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object FutureUtil {

  implicit class FutureOps[T](f: Future[T]) {
    def exec: T = Await.result(f, 20 seconds)
  }

}