package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import scala.util.Try

case class CatalogEntry(urn: CtsUrn, citationScheme: String, groupName: String, workTitle: String, versionLabel: String, exemplarLabel: Option[String] = None, online: Boolean = true) {
  require(citationScheme.nonEmpty,"citation scheme cannot be empty")
  require(groupName.nonEmpty,"text group name cannot be empty")
  require(workTitle.nonEmpty,"work title cannot be empty")
  require(versionLabel.nonEmpty,"version label cannot be empty")
  urn.workLevel match {
    case WorkLevel.Version => {
      exemplarLabel match {
        case None => // ok
        case _ => throw Ohco2Exception("exemplar label must be none when URN is at version level")
      }

    }
    case WorkLevel.Exemplar => {
      exemplarLabel match {
        case None =>  throw Ohco2Exception("exemplar label cannot be none when URN is at exemplar level")
        case _ => // ok
      }

    }
  }
}


case class Catalog (texts: Vector[CatalogEntry]) {
  def entriesForUrn(filterUrn: CtsUrn): Vector[CatalogEntry] = {
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
      val online = Try(row(6).toBoolean).getOrElse(false)
      if (row(5).isEmpty) {
        entries += CatalogEntry(urn,citation,group,work,vers,None,online)
      } else {
        entries += CatalogEntry(urn,citation,group,work,vers,Some(row(5)),online)

      }
    }
    Catalog(entries.toVector)
  }


}
