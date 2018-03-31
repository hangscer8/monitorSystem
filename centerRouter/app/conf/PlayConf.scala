package conf

import java.io.File

import com.typesafe.config.ConfigFactory

import scala.io.Source

object PlayConf {
  private def playConfig =
    if (new File(".", "play.conf").exists)
      ConfigFactory.parseFile(new File(".", "play.conf"))
    else
      ConfigFactory.parseString(Source.fromInputStream(this.getClass.getResourceAsStream("/play.conf")).mkString)

  def uploadDir: String = playConfig.getString("uploadDir")

  def akkaPort: Int = playConfig.getInt("akkaPort")

  def initDataBase: Boolean = playConfig.getBoolean("initDataBase")
}