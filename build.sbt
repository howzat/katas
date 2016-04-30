
lazy val dependencies =Seq(
  "org.typelevel" %% "cats" % "0.4.1"  withSources() withJavadoc(),
  "org.scalacheck" %% "scalacheck" % "1.12.1" % "test" withSources() withJavadoc(),
  "org.scalactic" %% "scalactic" % "2.2.6"  withSources() withJavadoc(),
  "org.scalatest" %% "scalatest" % "2.2.6" % "test"  withSources() withJavadoc()
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
    updateOptions := updateOptions.value.withCachedResolution(true)
)
