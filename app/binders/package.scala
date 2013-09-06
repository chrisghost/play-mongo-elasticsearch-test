import play.api.mvc._
import scala.util.Try

package object binders {

  type BSONObjectID = reactivemongo.bson.BSONObjectID

  /**
   * QueryString binder for BSONObjectID
   */
  implicit def objectIdQueryStringBindable = new QueryStringBindable[BSONObjectID] {
    def bind(key: String, params: Map[String, Seq[String]]) = params.get(key).flatMap(_.headOption).map { value =>
      Try(new BSONObjectID(value)).map { id =>
        Right(id)
      } getOrElse {
        Left("Cannot parse parameter " + key + " as ObjectId")
      }
    }
    def unbind(key: String, value: BSONObjectID) = key + "=" + value.toString
  }

  /**
   * Path binder for BSONObjectID.
   */
  implicit def objectIdPathBindable = new PathBindable[BSONObjectID] {
    def bind(key: String, value: String) = {
      Try(new BSONObjectID(value)).map { id =>
        Right(id)
      } getOrElse {
        Left("Cannot parse parameter " + key + " as ObjectId")
      }
    }
    def unbind(key: String, value: BSONObjectID) = value.toString
  }

  /**
   * Convert a BSONObjectID to a Javascript String
   */
  implicit def objectIdJavascriptLitteral = new JavascriptLitteral[BSONObjectID] {
    def to(value: BSONObjectID) = value.toString
  }

}
