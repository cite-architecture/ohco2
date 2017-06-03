package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import scala.util.Try

import scala.scalajs.js
import js.annotation.JSExport

/** Catalog for an entire text repository.
*
* @param texts Set of catalog entries.
*/
@JSExport case class Catalog (texts: Vector[CatalogEntry]) {


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


  def label(urn: CtsUrn) : String = {
    entriesForUrn(urn).map(_.toString).mkString("\n")
  }


  def cex(delimiter: String = "\t") : String = {
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
