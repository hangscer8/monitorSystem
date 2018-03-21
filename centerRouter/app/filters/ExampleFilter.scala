package filters

import java.util.Date

import javax.inject._
import play.api.mvc._

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
class ExampleFilter @Inject()(implicit ec: ExecutionContext) extends EssentialFilter {

  override def apply(next: EssentialAction) = EssentialAction { request =>
    next(request).map { result =>
      println(s"timestamp:${new Date(System.currentTimeMillis())} , method:${Console.RED}${request.method.toUpperCase}${Console.RESET} , path:${Console.GREEN}${request.path}${Console.RESET} , session:${Console.BLUE}${request.session}${Console.RESET} , flash:${Console.MAGENTA}${request.flash}${Console.RESET}")
      result.withHeaders("X-ExampleFilter" -> "foo")
    }
  }
}
