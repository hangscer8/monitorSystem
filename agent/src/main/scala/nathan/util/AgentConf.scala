package nathan.util

import java.io.File

import com.typesafe.config.ConfigFactory

import scala.io.Source

object AgentConf {
  val applicationConf =
    if (new File(".", "application.conf").exists)
      ConfigFactory.parseFile(new File(".", "application.conf"))
    else
      ConfigFactory.parseString(Source.fromInputStream(this.getClass.getResourceAsStream("/application.conf")).mkString)
}