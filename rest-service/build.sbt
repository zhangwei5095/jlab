import sbt.Resolver.file
import sbt._
import Keys._
import spray.revolver.RevolverPlugin.Revolver

name := "rest-service"

version := "1.0"

scalaVersion := "2.10.4"

resolvers += "spray repo" at "http://repo.spray.io"

resolvers += "spray nightlies" at "http://nightlies.spray.io"

resolvers += "Local Ivy Repository" at "file:///"+Path.userHome+"/.ivy2/local"

resolvers += "Local Maven Repository" at "file:///"+Path.userHome+"/.m2/repository"

resolvers += Resolver.mavenLocal

libraryDependencies ++= {
  val akkaVersion  = "2.3.7"
  val sprayVersion = "1.3.1"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion exclude ("org.scala-lang" , "scala-library"),
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion exclude ("org.slf4j", "slf4j-api")
                                                      exclude ("org.scala-lang" , "scala-library"),
    "com.typesafe.akka" %% "akka-remote" % akkaVersion,
    "ch.qos.logback"    % "logback-classic"   % "1.0.13",
    "io.spray"          % "spray-can"         % sprayVersion,
    "io.spray"          % "spray-routing"     % sprayVersion,
    "io.spray"          %% "spray-json"       % "1.2.5"              exclude ("org.scala-lang" , "scala-library"),
    "org.specs2"        %% "specs2"           % "1.14"        % "test",
    "io.spray"          % "spray-testkit"     % sprayVersion  % "test",
    "org.json4s"        %% "json4s-native"    % "3.2.11",
    "com.typesafe.akka" %% "akka-testkit"     % akkaVersion   % "test",
    "com.novocode"      % "junit-interface"   % "0.11-RC1"    % "test->default" exclude("org.hamcrest", "hamcrest-core"),
    "org.scalatest"     %   "scalatest_2.10"  % "2.0" % "test",
    "org.seleniumhq.selenium.fluent" % "fluent-selenium"  % "1.14.5"  % "test",
    "com.github.detro.ghostdriver"   % "phantomjsdriver"  % "1.1.0"   % "test",
    "com.paulhammant"                % "ngwebdriver"      % "0.9.1"   % "test" ,
    "com.codahale.metrics"           % "metrics-core"     % "3.0.0"   % "test",
    "org.hamcrest"                    % "hamcrest-all"     % "1.3"     % "test",
    "org.jlab" % "common" % "1.0.0"
  )
}

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-Ywarn-dead-code",
  "-language:_",
  "-target:jvm-1.7",
  "-encoding", "UTF-8"
)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")

unmanagedResourceDirectories in Compile <++= baseDirectory {
  base => Seq(base / "src/main/angular")
}

watchSources += baseDirectory.value / "src" / "*.*"

Revolver.settings : Seq[sbt.Def.Setting[_]]

crossPaths := false

//conflictManager := ConflictManager.loose

net.virtualvoid.sbt.graph.Plugin.graphSettings
