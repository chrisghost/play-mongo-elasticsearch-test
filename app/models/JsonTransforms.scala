package models

import _root_.utils.Slugify
import play.api.libs.json._
import play.api.libs.functional.syntax._
import reactivemongo.bson._
import play.modules.reactivemongo.json.BSONFormats._

object JsonTransforms {

  //val stringToObjectId = (__ \ "id").read[String].map { str => BSONObjectID(str) }
  //val objectIdToString = Writes[BSONObjectID] { id => JsString(id.stringify) }

  /*val objectIdToStringFormat = Format[BSONObjectID](
    (JsPath \ "id").read[String] map {str => BSONObjectID(str)},
    Writes[BSONObjectID] { id =>
      JsString(id.toString + "bajs")
    }
  )*/

  /*
  val addObjectId = __.json.update(
    (__ \ '_id).json.put(JsString(BSONObjectID.generate.stringify))
  )
  */

  val generateSlug = __.json.update(
    (__ \ 'slug).json.copyFrom(
      (__ \ 'name).read[String] map { str => JsString(Slugify(str)) }
    )
  )

  def updateObjectId(id: BSONObjectID) = __.json.update(
    (__ \ '_id).json.put(Json.toJson(id))
  )

  def addUserId(id: BSONObjectID) = __.json.update(
    (__ \ 'userId).json.put(BSONObjectIDFormat.writes(id))
  )

  val addNormalizedId = __.json.update(
    (__ \ 'id).json.copyFrom(
      (__ \ '_id).read[BSONObjectID] map { id => JsString(id.stringify) }
    )
  )

  /*
  val bsonObjectIdToString = __.json.update(
    (__ \ 'id).json.copyFrom(
      (__ \ '_id \ '$oid).json.pick
    )
  ) andThen (__ \ '_id).json.prune

  val stringToBSONObjectID = __.json.update(
    (__ \ '_id \ '$oid).json.copyFrom(
      (__ \ 'id).json.pick
    )
  ) andThen (__ \ 'id).json.prune
  */
}
