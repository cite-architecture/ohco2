package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import scala.util.Try

case class CatalogEntry(urn: CtsUrn, citationScheme: String, groupName: String, workTitle: String, versionLabel: String, exemplarLabel: Option[String] = None, online: Boolean = true)


case class Catalog (texts: Vector[CatalogEntry]) {
  def forUrnOption(filterUrn: CtsUrn): Vector[CatalogEntry] = {
    texts.filter(_.urn.urnMatch(filterUrn))
  }
}

object Catalog {
  def apply(f: String, sep: String = "#"): Catalog = {
    var entries = scala.collection.mutable.ArrayBuffer.empty[CatalogEntry]
    // read file, drop header line:
    val columnsByRow = Source.fromFile(f).getLines.toVector.map(_.split(sep)).drop(1)

    for (row <- columnsByRow) {
      val urn = CtsUrn(row(0))
      val citation = row(1)
      val group = row(2)
      val work = row(3)
      val vers = row(4)
      row.size match {
        case 6 => {
          val exemplar = None
          val online = Try(row(5).toBoolean).getOrElse(false)
          entries += CatalogEntry(urn,citation,group,work,vers,exemplar,online)
        }
        case 7 => {
          val exemplar = Some(row(5))
          val online = Try(row(6).toBoolean).getOrElse(false)
          entries += CatalogEntry(urn,citation,group,work,vers,exemplar,online)
        }
      }
    }
    Catalog(entries.toVector)
  }


}
