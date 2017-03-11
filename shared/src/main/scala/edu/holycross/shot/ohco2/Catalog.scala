package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import scala.util.Try


/** Entry for a single concrete version of a text.
*
* @param urn URN for the version.
* @param citationScheme Label for citation scheme, with levels
* separated by "/", e.g., "book/chapter".
* @param groupName Label for text group.
* @param workTitle Title of notional work.
* @param versionLabel Label for edition or translation.
* @param exemplarLabel Label for optional exemplar, or None.
* @param online True if the text is present in the cataloged [[Corpus]].
*/
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

  /** Pretty print a catalog entry.
  */
  override def toString = {
    val baseString = s"${groupName}, ${workTitle}"
    exemplarLabel match {
      case lbl: Some[String] => baseString + s" (${versionLabel}: ${lbl.get})"
      case None => baseString + s" (${versionLabel})"
    }
  }

  /** Pretty print with URN.
  */
  def fullLabel = {
    toString + s". ${urn}"
  }
}


/** Catalog for an entire text repository.
*
* @param texts Set of catalog entries.
*/
case class Catalog (texts: Vector[CatalogEntry]) {

  /** Find catalog entries by URN.
  *
  * @param filterUrn URN identifying text(s).
  */
  def entriesForUrn(filterUrn: CtsUrn): Vector[CatalogEntry] = {
    texts.filter(_.urn.~~(filterUrn))
  }

  /** Number of entries in catalog.
  */
  def size: Int = {
    texts.size
  }


  def label(urn: CtsUrn) : String = {
    entriesForUrn(urn).map(_.toString).mkString("\n")
  }

}

/** Factory for making catalogs from text sources.
*/
object Catalog {


  /** Build a [[Catalog]] from delimited text source.
  *
  * @param data Delimited-text representation of a catalog.
  * @param sep String value separating fields of the catalog entry.
  */
  def apply(data: String, sep: String = "#"): Catalog = {
    var entries = scala.collection.mutable.ArrayBuffer.empty[CatalogEntry]
    // read file, drop header line:
    val columnsByRow = data.split("\n").toVector.filter(_.nonEmpty).map(_.split(sep)).drop(1)

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
