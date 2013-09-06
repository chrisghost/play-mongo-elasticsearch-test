package utils

import scala.annotation.tailrec

object Slugify {

  def apply(str: String): String = {
    import java.text.Normalizer
    Normalizer.normalize(str, Normalizer.Form.NFD).trim().replaceAll("[^\\w ]", "").replace(" ", "-").toLowerCase
  }

  @tailrec
  def unique(slug: String, existingSlugs: Seq[String]): String = {
    if (!(existingSlugs contains slug)) {
      slug
    } else {
      val EndsWithNumber = "(.+-)([0-9]+)$".r
      slug match {
        case EndsWithNumber(s, n) => unique(s + (n.toInt + 1), existingSlugs)
        case s => unique(s + "-2", existingSlugs)
      }
    }
  }
}
