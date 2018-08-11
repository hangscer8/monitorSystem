package nathan.config

import com.typesafe.config.ConfigFactory

object ConfigUtil {
  lazy val config=ConfigFactory.load()
  def get[T](path:String):T=config.getAnyRef(path).asInstanceOf[T]
}