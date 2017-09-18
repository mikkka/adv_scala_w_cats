val catsVersion = "0.7.2"
val catsAll = "org.typelevel" %% "cats" % catsVersion

/*
val macroParadise = compilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
val kindProjector = compilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")
val resetAllAttrs = "org.scalamacros" %% "resetallattrs" % "1.0.0"

val specs2Version = "3.8.6" // use the version used by discipline
val specs2Core  = "org.specs2" %% "specs2-core" % specs2Version
val specs2Scalacheck = "org.specs2" %% "specs2-scalacheck" % specs2Version
val scalacheck = "org.scalacheck" %% "scalacheck" % "1.13.4"
*/

val _organization = "name.mtkachev"
val _scalaVersion = "2.11.11"

scalaVersion in ThisBuild := _scalaVersion

lazy val root = (project in file(".")).
  settings(
    organization := _organization,
    name := "adv-scala-w-cats-samples",
    scalaVersion := _scalaVersion,
    libraryDependencies ++= Seq(
      catsAll
      /*
            specs2Core,
            specs2Scalacheck,
            scalacheck,
            macroParadise,
            kindProjector,
            resetAllAttrs
      */
    ),
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-feature",
      "-language:_"
    )
  )