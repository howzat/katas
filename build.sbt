
lazy val dependencies =Seq(
  "org.typelevel" %% "cats-core" % "1.2.0"  withSources() withJavadoc(),
  "org.typelevel" %% "cats-effect" % "1.0.0-RC3"  withSources() withJavadoc(),
  "org.scalacheck" %% "scalacheck" % "1.14.0" % "test" withSources() withJavadoc(),
  "org.scalactic" %% "scalactic" % "3.0.5" % "test" withSources() withJavadoc(),
  "org.scalatest" %% "scalatest" % "3.0.5" % "test" withSources() withJavadoc()
)

lazy val projectProperties = Seq(
  organization := "benoit",
  version := "0.1.0",
  scalaVersion := "2.12.6"
)


lazy val root = (project in file("."))
  .settings(projectProperties: _*)
  .settings(
    name := "katas",
    libraryDependencies ++= dependencies,
    scalacOptions += "-Ypartial-unification",
    scalacOptions += "-deprecation",
    updateOptions := updateOptions.value.withCachedResolution(true),
    unmanagedResourceDirectories in Compile +=   baseDirectory.value / "resources"
)
