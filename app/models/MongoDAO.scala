package models
 
import scala.concurrent.Future
import play.modules.reactivemongo._
import reactivemongo.api._
import reactivemongo.api.indexes._
import reactivemongo.bson._
import reactivemongo.core.commands.LastError
import reactivemongo.core.commands.GetLastError
import play.modules.reactivemongo.json.collection.{JSONQueryBuilder, JSONGenericHandlers}
import reactivemongo.api.collections.GenericCollection
import play.api.libs.json._

import play.api.Play.current
import scala.concurrent.ExecutionContext

trait MongoDAO[T] extends GenericCollection[JsObject, Reads, Writes] with JSONGenericHandlers {

  def name: String //classOf[T].getSimpleName
  def failoverStrategy = FailoverStrategy()
  def db = ReactiveMongoPlugin.db
  def genericQueryBuilder = JSONQueryBuilder(this, failoverStrategy)
  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.global

  val ID = "_id"

  /*def findOne[T](query: JsObject):Future[Option[T]] = {
    val tReader = implicitly[Reads[T]]
    genericQueryBuilder.query(query).one(tReader, ec)
  }*/
  /*
    def find(query: JsObject) = {
      val cursor = collection.find(query).cursor[JsObject].toList
      cursor.map { items =>
        items.foldLeft(JsArray(List()))( (obj, item) => obj ++ Json.arr(item) )
      }
    }

    def findOption(query: JsObject) = collection.find(query).cursor[T].headOption

    def findById(id: BSONObjectID) = findOption(Json.obj(ID -> id))

    def remove(query: JsObject) = collection.remove(query)

    def remove(id: BSONObjectID) = remove(Json.obj(ID -> id))
    */
}
