## writen in scala(my favourite language)

```scala
import play.api.mvc._

class ActionContext[A](val request: Request[A], private[this] val p: Promise[Result]) {
  def complete(result: Result): Future[Result] = {
    p.complete(Try(result))
    p.future
  }
}

object ActionContext {
  def imperativelyComplete[A](bp: BodyParser[A])(inner: ActionContext[A] => Unit): Action[A] = {
    Action.async(bp) { request =>
      val p = Promise[Result]
      inner(new ActionContext(request, p))
      p.future
    }
  }

  def imperativelyComplete(inner: ActionContext[AnyContent] => Unit): Action[AnyContent] = {
    Action.async { request =>
      val p = Promise[Result]
      inner(new ActionContext(request, p))
      p.future
    }
  }
}
```
