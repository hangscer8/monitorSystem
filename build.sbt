lazy val shared = project
lazy val centerRouter = project.dependsOn(shared)
lazy val agent = project.dependsOn(shared)
lazy val front = project.dependsOn(shared)
lazy val root = project.in(file(".")).aggregate(centerRouter,agent,front)