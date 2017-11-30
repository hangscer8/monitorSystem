name := "front"

version := "0.1"

//scalaHome := Some(file("/usr/local/Cellar/scala"))
scalaVersion := "2.12.4"
enablePlugins(ScalaJSPlugin)
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.1",
  "com.thoughtworks.binding" %%% "dom" % "latest.release",
  "com.thoughtworks.binding" %%% "binding" % "latest.release",
  "com.lihaoyi" % "scalatags_2.12" % "0.6.7",
  "io.circe" %%% "circe-scalajs" % "0.8.0",
  "io.circe" %%% "circe-core" % "0.8.0",
  "io.circe" %%% "circe-generic" % "0.8.0",
  "io.circe" %%% "circe-parser" % "0.8.0"
)
crossTarget in fastOptJS := baseDirectory.value/"web"/"js"