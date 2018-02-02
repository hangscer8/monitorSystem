
name := "front"

version := "0.1"

//scalaHome := Some(file("/usr/local/Cellar/scala"))
scalaVersion := "2.12.4"
enablePlugins(ScalaJSPlugin)
libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.1",
  "com.lihaoyi" %%% "scalatags" % "0.6.7",
  "io.circe" %%% "circe-scalajs" % "0.8.0",
  "io.circe" %%% "circe-core" % "0.8.0",
  "io.circe" %%% "circe-generic" % "0.8.0",
  "io.circe" %%% "circe-parser" % "0.8.0",
  "be.doeraene" %%% "scalajs-jquery" % "0.9.1",
  "com.github.karasiq" %%% "scalajs-highcharts" % "1.2.1",
  "org.akka-js" %%% "akkajsactor" % "1.2.5.9"
)
crossTarget in fastOptJS := baseDirectory.value / "frontWeb" / "js"
