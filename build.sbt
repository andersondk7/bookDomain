import Dependencies.*
import commandmatrix.*

lazy val scala2 = "2.13.11"
lazy val scala3 = "3.3.0"
lazy val supportedScalaVersions = List(scala2, scala3)

ThisBuild / organization := "org.dka.books"
ThisBuild / version := "0.7.0"
ThisBuild / scalaVersion := scala3

lazy val flywaySettings: Seq[Def.Setting[_]] = Seq(
  flywayUser := sys.env.getOrElse("BZ_USER", "defaultUser"),
  flywayPassword := sys.env.getOrElse("BZ_PASSWORD", "defaultPassword"),
  flywayUrl := s"jdbc:postgresql://localhost:5432/book_biz?&currentSchema=${sys.env.getOrElse("BZ_SCHEMA", "public")}"
)

inThisBuild(
  Seq(
    commands ++= CrossCommand.single(
      "test",
      matrices = Seq(bookDomain, bookDB),
      dimensions = Seq(
        Dimension.scala(scala3),
        Dimension.platform()
      )
    )
  )
)

lazy val bookDomain = (projectMatrix in file("domain"))
  .configs(IntegrationTest)
  .settings(
    libraryDependencies ++= commonDependencies,
    Defaults.itSettings
  )
  .jvmPlatform(scalaVersions = supportedScalaVersions)


lazy val bookDB = (projectMatrix in file("db"))
  .configs(IntegrationTest)
  .enablePlugins(FlywayPlugin)
  .settings(
    libraryDependencies ++= dbDependencies,
    flywaySettings,
    Defaults.itSettings
  )
  .dependsOn(bookDomain)
  .jvmPlatform(scalaVersions = Seq(scala3))

lazy val books = project
  .in(file("."))
  .configs(IntegrationTest)
  .aggregate(bookDomain.projectRefs ++ bookDB.projectRefs: _*)
  .settings(
    Defaults.itSettings
  )
