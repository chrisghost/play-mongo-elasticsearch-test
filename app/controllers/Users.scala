package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.Future
import reactivemongo.bson.BSONObjectID
import reactivemongo.api._
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import play.modules.reactivemongo.json.BSONFormats._

import models._
import JsonTransforms._
import extensions.ReactiveMongoExtensions._


object Users extends Controller with MongoController {

  def collection: JSONCollection = db.collection[JSONCollection]("users")

  def list() = Action.async { implicit request =>
    val usersFuture = collection.find(Json.obj()).cursor[User].toList
    usersFuture.map { users =>
      Ok(Json.toJson(users))
    }
  }

  def edit(id: BSONObjectID) = Action.async { implicit request =>
    val cursor = collection.find(Json.obj("_id" -> id)).cursor[User].headOption
    cursor.map { userOption =>
      userOption.map { user =>
        Ok(Json.toJson(user))
      } getOrElse {
        NotFound(id.toString)
      }
    }
  }

  def insert() = save(BSONObjectID.generate)

  def save(id: BSONObjectID) = Action.async(parse.json) { implicit request =>
    request.body.validate(updateObjectId(id) andThen User.reads).fold(
      invalid = e => Future.successful(BadRequest(JsError.toFlatJson(e))),
      valid = { user =>
        collection.update(Json.obj("_id" -> id), user, upsert=true).map { newUser =>
          Created(Json.toJson(user))
        }
      }
    )
  }

  def remove(id: BSONObjectID) = Action.async { implicit request =>
    collection.remove(Json.obj("_id" -> id)).map { lastError =>
      if (lastError.ok)
        Ok
      else
        InternalServerError(lastError.toString)
    }
  }

}
