### writen in scala(my favourite language)

#### some helpful util

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

```scala
@Singleton
class AlarmEventController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Circe with AlarmServiceTrait with UtilTrait {
  def index() = ActionContext.imperativelyComplete { ctx =>
    val agentList = db.run(alarmEvents.sortBy(_.created.desc).take(15).result).exe
    ctx.complete(Ok(views.html.alarm.alarmEvent("查看告警信息", agentList)))
  }
}
```
