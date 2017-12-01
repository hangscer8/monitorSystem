package nathan.ec

import java.util.concurrent.Executors

import akka.actor.{ActorSystem, Props}
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import nathan.actor.CenterRouterActor

import scala.concurrent.ExecutionContext

object ExecutorService {
  implicit lazy val  system = ActorSystem(Protocol.center_system_name,ConfigFactory.load())
  private[this] lazy val ecFixThreadPoll = Executors.newFixedThreadPool(20)
  implicit lazy val ec = ExecutionContext.fromExecutor(ecFixThreadPoll)
  lazy val mat = ActorMaterializer()
  lazy val centerRouterActor=system.actorOf(Props(classOf[CenterRouterActor]),Protocol.center_actor_name)
  def getActorPath(targetSystem:String,host:String,targetPort:Int,actorPath:String)={
    s"akka.tcp://$targetSystem@$host:$targetPort/user/$actorPath"
  }
}