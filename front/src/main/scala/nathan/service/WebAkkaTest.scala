package nathan.service

import akka.actor.{Actor, ActorSystem, Props}

import scala.concurrent.duration._
import scala.scalajs.js.annotation.JSExport

@JSExport
object WebAkkaTest {
  val system = ActorSystem("webAkkaSystem")

  import system.dispatcher

  @JSExport
  def testAkka(): Unit = {
    val webActor = system.actorOf(Props(new WebActor), "webActor")
    system.scheduler.schedule(2 seconds, 2 seconds) {
      webActor ! "hahah" + System.currentTimeMillis()
    }
  }
}

class WebActor extends Actor {
  override def receive: Receive = {
    case any => println(any)
  }
}