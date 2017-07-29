package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import scala.util.Try

import scala.scalajs.js
import scala.scalajs.js.annotation._

/** Catalog for an entire text repository.
*
* @param texts Set of catalog entries.
*/
@JSExportTopLevel("Catalog") case class Catalog (texts: Vector[CatalogEntry]) {


  // ensure unique urns
  val urnList = texts.map(_.urn)
  val dupes = urnList.groupBy(identity).collect { case (x,ys) if ys.lengthCompare(1) > 0 => x }
  require(dupes.size == 0, s"""Duplicated URN values: ${dupes.mkString(",")}""")

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


  /** Create a new catalog by adding a second corpus to this one.
  *
  * @param catalog2 Catalog to add to this one.
  */
  def ++(catalog2: Catalog): Catalog= {
    val newEntries = texts ++ catalog2.texts
    Catalog(newEntries.distinct)
  }



  /** Create a new catalog by subtracting a catalog corpus from this one.
  *
  * @ catalog2 second catalog with contents to be removed from this one.
  */
  def --(catalog2: Catalog) : Catalog= {
      Catalog( texts diff catalog2.texts)
  }


  /** Create string label for catalog entries matching a
  * given URN.
  *
  * @param urn Urn to match.
  */
  def label(urn: CtsUrn) : String = {
    entriesForUrn(urn).map(_.toString).mkString("\n")
  }



  /** Serialize catalog to a String in CEX format.
  *
  * @param delimiter String value to use as column delimiter.
  */
  def cex(delimiter: String = "#") : String = {
    val header =        s"""urn${delimiter}citationScheme${delimiter}groupName${delimiter}workTitle${delimiter}versionLabel${delimiter}exemplarLabel${delimiter}online"""
    val cexEntries = texts.map(_.cex(delimiter))

    (header +:  cexEntries).mkString("\n") + "\n"
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
      if (row.size < 8) {
        throw Ohco2Exception(s"""Invalid CEX data for text catalog: too few columns (${row.size}) in row ${row.mkString}""")
      }
      val urn = CtsUrn(row(0))
      val citation = row(1)
      val group = row(2)
      val work = row(3)

      val vers = {

        if (row(4).isEmpty) {
          None
        } else {
          Some(row(4))
        }
      }

      val online = Try(row(6).toBoolean).getOrElse(false)
      val lang = row(7)
      if (row(5).isEmpty) {
        entries += CatalogEntry(urn,citation,lang,group,work,vers,None,online)
      } else {
        entries += CatalogEntry(urn,citation,lang,group,work,vers,Some(row(5)),online)

      }
    }
    Catalog(entries.toVector)
  }


}
