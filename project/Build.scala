import sbt._
import Keys._
import play.Project._
import java.net._

object Build extends Build {

  val appName = "test"
  val appVersion = "1.0"

  import Keys._

  val appDependencies = Seq(
    "se.digiplant" %% "play-scalr" % "1.0",
    "org.reactivemongo" %% "play2-reactivemongo" % "0.9"
  )

  val root = play.Project(appName, appVersion, appDependencies).settings(
    // Import binders to allow transforms between formats
    routesImport += "binders._",

    // Turn off play's internal compilers
    lessEntryPoints := Nil,
    javascriptEntryPoints := Nil,
    coffeescriptEntryPoints := Nil
  )
}
