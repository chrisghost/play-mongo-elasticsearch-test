import play.Project._

name := "play-mongo-elastic-angular"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  cache,
  "se.digiplant" %% "play-scalr" % "1.0",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.9" exclude("org.scala-stm", "scala-stm_2.10.0")
)

play.Project.playScalaSettings

routesImport += "binders._"

lessEntryPoints := Nil

javascriptEntryPoints := Nil

coffeescriptEntryPoints := Nil
