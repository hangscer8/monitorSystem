package nathan.util

import java.io.File
import java.nio.file.{Files, Paths}

import scala.io.Source

object AgentId {
  private val agentIdFile = new File(System.getProperty("user.home") + File.separator + ".agentId")
  val agentIdString = if (agentIdFile.exists()) {
    Source.fromFile(agentIdFile).getLines().mkString("").trim
  } else {
    Files.write(Paths.get(agentIdFile.toURI), scala.util.Random.alphanumeric.take(40).mkString("").getBytes)
    Source.fromFile(agentIdFile).getLines().mkString("").trim
  }
}