package nathan.ec

import java.util.concurrent.Executors

import akka.actor.{ActorSystem, Props}
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import nathan.actor.{CenterRouterActor, DaoActor}
import nathan.monitorSystem.akkaSystemConst._

import scala.concurrent.ExecutionContext

object ExecutorService {
  implicit val system = ActorSystem(center_system_name, ConfigFactory.load().withFallback(ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$center_port")))
  private[this] lazy val ecFixThreadPoll = Executors.newFixedThreadPool(20)
  implicit lazy val ec = ExecutionContext.fromExecutor(ecFixThreadPoll)
  lazy val mat = ActorMaterializer()
  val centerRouterActor = system.actorOf(Props(classOf[CenterRouterActor]), center_actor_name)
  val daoActor = system.actorOf(Props(classOf[DaoActor]), dao_actor_name)

  def getActorPath(targetSystem: String, host: String, targetPort: Int, actorPath: String) = {
    s"akka.tcp://$targetSystem@$host:$targetPort/user/$actorPath"
  }
}