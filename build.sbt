lazy val shared = project
lazy val centerRouter = project.dependsOn(shared)
lazy val agent = project.dependsOn(shared)
//lazy val front = project.dependsOn(shared) //
lazy val front = project.settings( unmanagedSourceDirectories in Compile ++= ((unmanagedSourceDirectories in shared) in Compile).value ).dependsOn(shared) //共享的库，需要被js使用，因此加上该句话
lazy val root = project.in(file(".")).aggregate(centerRouter,agent,front)
