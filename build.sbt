lazy val shared = (project in file("shared"))
  .settings(scalaVersion := "2.12.4",
    test in assembly := {},
    assemblyJarName in assembly := "shared.jar"
  )
  .settings(libraryDependencies ++= Seq(
    "com.typesafe.akka" % "akka-actor_2.12" % "2.4.19"
  ))

lazy val centerRouter = (project in file("centerRouter"))
  .settings(scalaJSProjects := Seq(front),
    pipelineStages in Assets := Seq(scalaJSPipeline),
    pipelineStages := Seq(digest, gzip),
    compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value)
  .settings(scalaVersion := "2.12.4")
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" % "akka-actor_2.12" % "2.4.19",
      "com.typesafe.akka" % "akka-stream_2.12" % "2.4.19",
      "com.typesafe.akka" % "akka-http_2.12" % "10.0.10",
      "com.typesafe.akka" % "akka-cluster_2.12" % "2.4.19",
      "com.typesafe.akka" % "akka-persistence_2.12" % "2.4.19",
      "com.typesafe.akka" %% "akka-remote" % "2.4.19",
      "mysql" % "mysql-connector-java" % "5.1.23",
      "com.typesafe.slick" %% "slick" % "3.2.0",
      "org.slf4j" % "slf4j-nop" % "1.6.4",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.2.0",
      "io.circe" %% "circe-core" % "0.9.0-M2",
      "io.circe" %% "circe-generic" % "0.9.0-M2",
      "io.circe" %% "circe-parser" % "0.9.0-M2",
      "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.10",
      "junit" % "junit" % "4.12",
      "com.typesafe.slick" %% "slick-codegen" % "3.2.0",
      "com.h2database" % "h2" % "1.4.186",
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.2",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.2",
      "com.typesafe.akka" %% "akka-http-jackson" % "10.0.10",
      "org.ensime" %% "api" % "2.0.0",
      "org.typelevel" %% "cats-core" % "1.0.0-RC1",
      guice,
      "com.vmunier" %% "scalajs-scripts" % "1.1.1",
      "com.dripower" %% "play-circe" % "2609.1"
    )
  ).enablePlugins(PlayScala)
  .dependsOn(shared)

lazy val agent = (project in file("agent"))
  .settings(scalaVersion := "2.12.4",
    resolvers += "type safe" at "http://repo.typesafe.com/typesafe/maven-releases/",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" % "akka-actor_2.12" % "2.4.19",
      "com.typesafe.akka" % "akka-stream_2.12" % "2.4.19",
      "com.typesafe.akka" % "akka-http_2.12" % "10.0.10",
      "com.typesafe.akka" % "akka-cluster_2.12" % "2.4.19",
      "com.typesafe.akka" % "akka-persistence_2.12" % "2.4.19",
      "com.typesafe.akka" %% "akka-remote" % "2.4.19",
      "com.zaxxer" % "HikariCP" % "2.6.3",
      "mysql" % "mysql-connector-java" % "5.1.23",
      "com.typesafe.slick" % "slick_2.12" % "3.2.0",
      "io.circe" %% "circe-core" % "0.9.0-M2",
      "io.circe" %% "circe-generic" % "0.9.0-M2",
      "io.circe" %% "circe-parser" % "0.9.0-M2",
      "junit" % "junit" % "4.12",
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.2",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.2",
      "org.typelevel" %% "cats-core" % "1.0.0-RC1",
      "org.hyperic" % "sigar" % "1.6.4",
      "com.github.oshi" % "oshi-parent" % "3.4.4",
      "com.github.oshi" % "oshi-core" % "3.4.4"
    ),
    test in assembly := {},
    mainClass in assembly := Some("nathan.AgentMain"),
    assemblyJarName in assembly := "agent.jar"
  ).dependsOn(shared)

//lazy val front = project.dependsOn(shared) //
lazy val front = (project in file("front"))
  .settings(unmanagedSourceDirectories in Compile ++= ((unmanagedSourceDirectories in shared) in Compile).value)
  .settings(crossTarget in fastOptJS := baseDirectory.value / ".." / "centerRouter" / "public" / "javascripts")
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

