val scala3Version = "3.1.1"

def commonSettings = List(
  version := "0.1.0",

  scalaVersion := scala3Version,

  // scalacOptions += "-Yexplicit-nulls",
  scalacOptions += "-Xcheck-macros",

  libraryDependencies ++= Seq(
    "org.scala-lang" %% "scala3-staging" % scala3Version,
    "com.lihaoyi" %% "utest" % "0.7.10" % "test"
  ),

  testFrameworks += new TestFramework("utest.runner.Framework"),

  Compile / doc / scalacOptions ++= List(
    "-groups", "-comment-syntax", "wiki"),

  credentials += Credentials(Path.userHome / ".sbt" / ".credentials"),

  organization := "com.akmetiuk",
  organizationName := "Anatolii Kmetiuk",
  organizationHomepage := Some(url("https://akmetiuk.com/")),

  scmInfo := Some(
    ScmInfo(
      url("https://github.com/anatoliykmetyuk/simple-rockets-compiler"),
      "scm:git@github.com:anatoliykmetyuk/simple-rockets-compiler.git"
    )
  ),
  developers := List(
    Developer(
      id    = "anatoliykmetyuk",
      name  = "Anatolii Kmetiuk",
      email = "anatoliykmetyuk@gmail.com",
      url   = url("https://akmetiuk.com")
    )
  ),

  description := "Scala-based DSL for Simple Rockets 2.",
  licenses := List("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt")),
  homepage := Some(url("https://github.com/anatoliykmetyuk/simple-rockets-compiler")),

  // Remove all additional repository other than Maven Central from POM
  pomIncludeRepository := { _ => false },
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
    else Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  publishMavenStyle := true,
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
    publish / skip := true,
  )
