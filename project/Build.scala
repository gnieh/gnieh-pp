import sbt._
import Keys._

object PpBuild extends Build {

  lazy val pp = Project(id = "gnieh-pp",
    base = file(".")) settings (
    organization in ThisBuild := "org.gnieh",
    name := "gnieh-pp",
    version in ThisBuild := "0-1.SNAPSHOT",
    scalaVersion in ThisBuild := "2.10.0",
    crossScalaVersions in ThisBuild := Seq("2.9.2", "2.10.0"),
    libraryDependencies in ThisBuild ++= globalDependencies,
    compileOptions) settings(publishSettings: _*)

  lazy val globalDependencies = Seq(
    "org.scalatest" %% "scalatest" % "2.0.M5" % "test" cross CrossVersion.binaryMapped {
      case "2.9.2" => "2.9.0"
      case v => v
    }
  )

  lazy val compileOptions = scalacOptions in ThisBuild <++= scalaVersion map { v =>
    if(v.startsWith("2.10"))
      Seq("-deprecation", "-language:implicitConversions", "-feature")
    else
      Seq("-deprecation")
  }

  lazy val publishSettings = Seq(
    publishMavenStyle in ThisBuild := true,
    publishArtifact in Test := false,
    // The Nexus repo we're publishing to.
    publishTo in ThisBuild <<= version { (v: String) =>
      val nexus = "https://oss.sonatype.org/"
        if (v.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "content/repositories/snapshots")
        else Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    pomIncludeRepository in ThisBuild := { x => false },
    pomExtra in ThisBuild := (
      <licenses>
        <license>
          <name>The Apache Software License, Version 2.0</name>
          <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
          <distribution>repo</distribution>
        </license>
      </licenses>
      <scm>
        <url>https://github.com/gnieh/gnieh-pp</url>
        <connection>scm:git:git://github.com/gnieh/gnieh-pp.git</connection>
        <developerConnection>scm:git:git@github.com:gnieh/gnieh-pp.git</developerConnection>
        <tag>HEAD</tag>
      </scm>
      <developers>
        <developer>
          <id>satabin</id>
          <name>Lucas Satabin</name>
          <email>lucas.satabin@gnieh.org</email>
        </developer>
      </developers>
      <ciManagement>
        <system>travis</system>
        <url>https://travis-ci.org/#!/gnieh/gnieh-pp</url>
      </ciManagement>
      <organization>
        <name>gnieh.org</name>
        <url>https://github.com/gnieh</url>
      </organization>
      <issueManagement>
        <system>github</system>
        <url>https://github.com/gnieh/gnieh-pp/issues</url>
      </issueManagement>
    )
  )
}
