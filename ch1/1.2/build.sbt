val scala3Version = "3.1.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "hello-world",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "com.novocode" % "junit-interface" % "0.11" % "test",
      "org.typelevel" %% "cats-core" % "2.7.0",
      "org.typelevel" %% "cats-effect" % "3.3.4",
      "org.typelevel"  %% "squants"  % "1.8.3"
    )
  )