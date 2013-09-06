package models

import play.api.libs.json._
import play.api.libs.functional.syntax._

import reactivemongo.bson.BSONObjectID
import reactivemongo.api._
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection

import play.modules.reactivemongo.ReactiveMongoPlugin
import play.modules.reactivemongo.json.BSONFormats._
import scala.concurrent.ExecutionContext

case class User(
  name: String,
  email: String,
  _id: BSONObjectID = BSONObjectID.generate
)

object User {
  implicit val reads: Reads[User] = Json.reads[User]
  implicit val writes: Writes[User] = Json.writes[User].transform(json => json.transform(JsonTransforms.addNormalizedId).getOrElse(json))
  implicit val format: Format[User] = Format(reads, writes)
}
