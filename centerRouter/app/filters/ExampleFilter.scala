package filters

import java.util.Date
import javax.inject._

import akka.util.ByteString
import io.circe.Printer
import play.api.http.{ContentTypes, HttpEntity}
import play.api.mvc._
import util.UtilTrait

import scala.concurrent.ExecutionContext

/**
  * This is a simple filter that adds a header to all requests. It's
  * added to the application's list of filters by the
  * [[Filters]] class.
  *
  * @param ec This class is needed to execute code asynchronously.
  *           It is used below by the `map` method.
  */
@Singleton
class ExampleFilter @Inject()(implicit ec: ExecutionContext) extends EssentialFilter with UtilTrait {
  val list = List("/assets", "/stream", "/register", "/login")
  implicit val codec = Codec.utf_8
  implicit val customPrinter = Printer.spaces2.copy(dropNullValues = false)
  val badStatus = Result(header = ResponseHeader(401), body = HttpEntity.Strict(ByteString("没有权限!"), None)).as(ContentTypes.HTML)

  override def apply(next: EssentialAction) = EssentialAction { request =>
    next(request).map { result =>
      println(
        s"""
           |--------------------------------------------------
           |timestamp:${new Date(System.currentTimeMillis())}
           |method:${Console.RED}${request.method.toUpperCase}${Console.RESET}
           |headers:${request.headers}
           |path:${Console.GREEN}${request.path}${Console.RESET}
           |session:${Console.BLUE}${request.session}${Console.RESET}
           |flash:${Console.MAGENTA}${request.flash}${Console.RESET}
           |--------------------------------------------------
           |""".stripMargin)
      request.path == "/" || list.exists(prePath => request.path.startsWith(prePath)) match {
        case true => result.withHeaders("X-ExampleFilter" -> "foo") //不需要检查权限
        case false => request.session.get(loginKey) match { //需要检查权限
          case None => badStatus //没有权限
          case _ => result.withHeaders("X-ExampleFilter" -> "foo")
        }
      }
    }
  }
}