
name := "front"

version := "0.1"

//scalaHome := Some(file("/usr/local/Cellar/scala"))
scalaVersion := "2.12.4"
enablePlugins(ScalaJSPlugin)
libraryDependencies ++= Seq(
  "io.circe" %%% "circe-scalajs" % "0.8.0",
  "io.circe" %%% "circe-core" % "0.8.0",
  "io.circe" %%% "circe-generic" % "0.8.0",
  "io.circe" %%% "circe-parser" % "0.8.0",
  "be.doeraene" %%% "scalajs-jquery" % "0.9.1",
  "com.github.karasiq" %%% "scalajs-highcharts" % "1.2.1",
  "org.akka-js" %%% "akkajsactor" % "1.2.5.9",
  "com.thoughtworks.binding" %%% "binding" % "11.0.1",
  "com.thoughtworks.binding" %%% "dom" % "11.0.1",
  "com.thoughtworks.binding" %% "futurebinding" % "11.0.1"
)
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
crossTarget in fastOptJS := baseDirectory.value / "frontWeb" / "js"