import play.api._
import play.api.libs.json._

import reactivemongo.api._
import play.modules.reactivemongo._
import play.modules.reactivemongo.json.collection.JSONCollection

import models._

import scala.concurrent.{Await, ExecutionContext}
import play.api.Play.current

object Global extends GlobalSettings {

  override def onStart(app: Application) {
  }

  override def onStop(app: Application) {
  }

}
