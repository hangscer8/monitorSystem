package nathan.service

import akka.actor.{Actor, ActorSystem, Props}

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.scalajs.js.annotation.JSExport

@JSExport
object WebAkkaTest {
  val system = ActorSystem("webAkkaSystem")

  import system.dispatcher

  @JSExport
  def testAkka(): Unit = {
    val webActor = system.actorOf(Props(new WebActor), "webActor") //不可以是Props(classOf[WebActor])
    system.scheduler.schedule(2 seconds, 2 seconds) {
      webActor ! "hahah" + System.currentTimeMillis()
    }
  }
}

class WebActor extends Actor {

  import akka.pattern._
  import context.dispatcher

  override def receive: Receive = {
    case (msg: String, "ok") =>
      println(msg)
    case str: String =>
      Future(str).zip(Future("ok")).pipeTo(self)
  }
}