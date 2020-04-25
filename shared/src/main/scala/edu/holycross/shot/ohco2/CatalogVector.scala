package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import scala.util.Try


import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter


import scala.scalajs.js
import scala.scalajs.js.annotation._

/** Catalog for an entire text repository.
*
* @param texts Vector of catalog entries.
*/
case class CatalogVector (texts: Vector[CatalogEntry]) extends Catalog with LogSupport {

  def fromCex(data: String, sep: String = "#") = CatalogVector(data, sep)

  /** Find catalog entries by URN.
  *
  * @param filterUrn URN identifying text(s).
  */
  def entriesForUrn(filterUrn: CtsUrn): Vector[CatalogEntry] = {
    val urns = texts.map(_.urn)
   //debug("SELECT FROM "+  urns.mkString("\n"))

    for (u <- urns) {
     //debug("CF " + u + " and " + filterUrn.dropPassage)
     //debug(u <= filterUrn.dropPassage)
    }
    texts.filter(_.urn <= filterUrn.dropPassage)
  }


  /** Find name of text group for a given URN.
  *
  * @param u URN to look up in catalog.
  */
  def groupName(u: CtsUrn): String = {
    val matches = entriesForUrn(u)
    if (matches.size < 1) {
      throw new  Exception(s"ohco2.Catalog:  no urns matching ${u} found.")
    } else {
      matches(0).groupName
    }
  }

  /** Find title of work for a given URN.
  *
  * @param u URN to look up in catalog.
  */
  def workTitle(u: CtsUrn): String = {
    val matches = entriesForUrn(u)
    if (matches.size < 1) {
      throw new  Exception(s"ohco2.Catalog:  no urns matching ${u} found.")
    } else {
      matches(0).workTitle
    }
  }

  /** Find label for a version of a work for a given URN.
  *
  * @param u URN to look up in catalog.
  */
  def versionLabel(u: CtsUrn): String = {
    val matches = entriesForUrn(u)
    if (matches.size < 1) {
      throw new  Exception(s"ohco2.Catalog:  no urns matching ${u} found.")
    } else {
      matches(0).versionLabel match {
        case None => ""
        case str: Some[String] => str.get
      }
    }
  }

  /** Find label for an exemplar of a work for a given URN.
  *
  * @param u URN to look up in catalog.
  */
  def exemplarLabel(u: CtsUrn): String = {
    val matches = entriesForUrn(u)
    if (matches.size < 1) {
      throw new  Exception(s"ohco2.Catalog:  no urns matching ${u} found.")
    } else {
      matches(0).exemplarLabel match {
        case None => ""
        case str: Some[String] => str.get
      }
    }
  }
  /** Create a new catalog by adding a second corpus to this one.
  *
  * @param catalog2 Catalog to add to this one.
  */
  def ++(catalog2: Catalog): CatalogVector= {
    val newEntries = texts ++ catalog2.texts
    CatalogVector(newEntries.distinct)
  }



  /** Create a new catalog by subtracting a catalog corpus from this one.
  *
  * @ catalog2 second catalog with contents to be removed from this one.
  */
  def --(catalog2: Catalog) : CatalogVector= {
      CatalogVector( texts diff catalog2.texts)
  }


  /** Create string label describing all catalog entries matching a
  * given URN.
  *
  * @param urn Urn to match.
  */
  def labels(urn: CtsUrn) : String = {
    entriesForUrn(urn).map(_.toString).mkString("\n")
  }

  /** Create string label describing catalog entry matching a
  * given URN.
  *
  * @param urn Urn to match.
  */
  def label(urn: CtsUrn) : String = {
    entriesForUrn(urn).filter(_.urn == urn).mkString(" ")
  }

}

/** Factory for making catalogs from text sources.
*/
object CatalogVector {

  /** Build a [[CatalogVector]] from delimited text source.
  *
  * @param data Delimited-text representation of a CatalogVector.
  * @param sep String value separating fields of the CatalogVector entry.
  */
  def apply(data: String, sep: String = "#"): CatalogVector = {
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
    CatalogVector(entries.toVector)
  }


}
