val scala3Version = "3.1.1"

def commonSettings = List(
  organization := "com.akmetiuk",
  version := "0.1.0",

  scalaVersion := scala3Version,

  // scalacOptions += "-Yexplicit-nulls",
  scalacOptions += "-Xcheck-macros",

  libraryDependencies ++= Seq(
    "org.scala-lang" %% "scala3-staging" % scala3Version,
    "com.lihaoyi" %% "utest" % "0.7.10" % "test"
  ),

  testFrameworks += new TestFramework("utest.runner.Framework")
)

lazy val core = project
  .in(file("core"))
  .settings(commonSettings)
  .settings(
    name := "simple-rockets-compiler",
  )

lazy val examples = project
  .in(file("examples")).dependsOn(core)
  .settings(commonSettings)
  .settings(
    name := "simple-rockets-compiler-examples",
  )
