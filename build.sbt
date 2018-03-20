lazy val shared = (project in file("shared"))

lazy val centerRouter = (project in file("centerRouter"))
  .settings(scalaVersion := "2.12.4")
  .settings()
  .dependsOn(shared)

lazy val agent = (project in file("agent")).dependsOn(shared)

//lazy val front = project.dependsOn(shared) //
lazy val front = (project in file("front"))
  .settings(unmanagedSourceDirectories in Compile ++= ((unmanagedSourceDirectories in shared) in Compile).value)
  .settings(crossTarget in fastOptJS := baseDirectory.value / "frontWeb" / "js")
  .settings(
    libraryDependencies ++= Seq(
      "io.circe" %%% "circe-scalajs" % "0.8.0",
      "io.circe" %%% "circe-core" % "0.8.0",
      "io.circe" %%% "circe-generic" % "0.8.0",
      "io.circe" %%% "circe-parser" % "0.8.0",
      "be.doeraene" %%% "scalajs-jquery" % "0.9.1",
      "com.github.karasiq" %%% "scalajs-highcharts" % "1.2.1",
      "com.thoughtworks.binding" %%% "binding" % "11.0.1",
      "com.thoughtworks.binding" %%% "dom" % "11.0.1",
      "com.thoughtworks.binding" %%% "futurebinding" % "11.0.1"
    )
  ).enablePlugins(ScalaJSPlugin, ScalaJSWeb)
  .dependsOn(shared) //共享的库，需要被js使用，因此加上该句话

lazy val root = project.in(file(".")).aggregate(centerRouter, agent, front)

