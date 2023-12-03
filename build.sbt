ThisBuild / version := "0.0.1"

ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name             := "Parser",
    idePackagePrefix := Some("com.graphene"),
    libraryDependencies ++= List(
      "org.typelevel"     %% "cats-core"   % "2.9.0",
      "org.typelevel"     %% "cats-effect" % "3.5.0",
      "com.github.j-mie6" %% "parsley"     % "4.4.0",
    )
  )
