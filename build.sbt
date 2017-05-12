
lazy val dependencies =Seq(
  "org.typelevel" %% "cats" % "0.9.0"  withSources() withJavadoc(),
  "org.scalacheck" %% "scalacheck" % "1.12.1" % "test" withSources() withJavadoc(),
  "org.scalactic" %% "scalactic" % "3.0.0" % "test" withSources() withJavadoc(),
  "org.scalatest" % "scalatest_2.11" % "3.0.0" % "test"  withSources() withJavadoc()
)

lazy val projectProperties = Seq(
  organization := "benoit",
  version := "0.1.0",
  scalaVersion := "2.11.8"
)


lazy val root = (project in file("."))
  .settings(projectProperties: _*)
  .settings(
    name := "katas",
    libraryDependencies ++= dependencies,
    updateOptions := updateOptions.value.withCachedResolution(true),
    unmanagedResourceDirectories in Compile +=   baseDirectory.value / "resources"
)
