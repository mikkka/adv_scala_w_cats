val catsVersion = "1.0.0-MF"
val catsAll = Seq(
  "org.typelevel" %% "cats-core" % catsVersion,
  "org.typelevel" %% "cats-macros" % catsVersion,
  "org.typelevel" %% "cats-kernel" % catsVersion
)

val _organization = "name.mtkachev"
val _scalaVersion = "2.12.3"

scalaVersion in ThisBuild := _scalaVersion

lazy val root = (project in file(".")).
  settings(
    organization := _organization,
    name := "adv-scala-w-cats-samples",
    scalaVersion := _scalaVersion,
    libraryDependencies ++= catsAll,
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-feature",
      "-language:_"
    )
  )