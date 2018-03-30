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
@JSExportAll case class Catalog (texts: Vector[CatalogEntry]) {


  // ensure unique urns
  val urnList = texts.map(_.urn)
  val dupes = urnList.groupBy(identity).collect { case (x,ys) if ys.lengthCompare(1) > 0 => x }
  require(dupes.size == 0, s"""Duplicated URN values: ${dupes.mkString(",")}""")

  /** Find catalog entries by URN.
  *
  * @param filterUrn URN identifying text(s).
  */
  def entriesForUrn(filterUrn: CtsUrn): Vector[CatalogEntry] = {
    texts.filter(_.urn <= filterUrn.dropPassage)
  }

  /** Number of entries in catalog.
  */
  def size: Int = {
    texts.size
  }


  /** For all versions in a list, create a Vector of exemplars belonging to them.
  * The result is a map of [LabelledCtsUrn]s to a
  *(possibly empty) Vector of [LabelledCtsUrn]s.
  *
  * @param versions List of text versions, identified by [[LabelledCtsUrn]] objects.
  */
  def versionsToc(versions: Vector[LabelledCtsUrn]):  Map[LabelledCtsUrn, Vector[LabelledCtsUrn]] = {
    val pairings = for (v <- versions.sortBy(_.label)) yield {
      val exemplars = labelledExemplars.filter(_.urn ~~ v.urn)
      (v, exemplars.toSeq.toVector.sortBy(_.label))
    }
    pairings.toMap
  }


  /** For all works in a list, find mappings of all versions belonging to them.
  * The result is a map of [[LabelledCtsUrn]]s to a
  * further map of [[LabelledCtsUrn]]s to a
  * Vector of [LabelledCtsUrn]s.
  *
  * @param versions List of text versions, identified by [[LabelledCtsUrn]] objects.
  */
  def worksToc(works: Vector[LabelledCtsUrn]):  Map[LabelledCtsUrn, Map[LabelledCtsUrn, Vector[LabelledCtsUrn]]] = {
    val pairings = for (w <- works.sortBy(_.label)) yield {
      val vers = labelledVersions.filter(_.urn ~~ w.urn)
      (w, versionsToc(vers.toSeq.toVector.sortBy(_.label)))
      //println("WORKS TOC " + s"${w}->${vers}")
    }
    pairings.toMap
  }


  /** Beginning from all text groups in the catalog,
  * find mappings of all works belonging to them.
  * The result is a map of [[LabelledCtsUrn]]s to a
  * map of [[LabelledCtsUrn]]s for works to a further
  * map of [[LabelledCtsUrn]]s for versions to a
  * Vector of [LabelledCtsUrn]s.
  */
  def toc: Map[LabelledCtsUrn, Map[LabelledCtsUrn, Map[LabelledCtsUrn, Vector[LabelledCtsUrn]]]] = {
    val pairings = for (g <- labelledGroups.toSeq.sortBy(_.label)) yield {
      //(g,
      val wks = labelledWorks.filter(_.urn ~~ g.urn)
      (g, worksToc(wks.toSeq.toVector.sortBy(_.label)))
      //println(wks.toSeq.toVector)
    }
    pairings.toMap
  }

  /** Find Set of all text groups represented in the catalog.
  */
  def groups: Set[CtsUrn] = {
    texts.map(_.urn.toTextGroup).distinct.toSet
  }

  /** Created labelled URNs for the set of
  * text groups in this catalog.
  */
  def labelledGroups: Set[LabelledCtsUrn] = {
    groups.map(urn => LabelledCtsUrn(urn, groupName(urn) ))
  }


  /** Find Set of all works represented in the catalog.
  */
  def works: Set[CtsUrn] = {
    texts.map(_.urn.toWork).distinct.toSet
  }

  /** Created labelled URNs for the set of
  * text groups in this catalog.
  */
  def labelledWorks: Set[LabelledCtsUrn] = {
    works.map(urn => LabelledCtsUrn(urn, workTitle(urn)) )
  }

  /** Find Set of all versions represented in the catalog.
  */
  def versions: Set[CtsUrn] = {
    texts.map(_.urn.toVersion).distinct.filter(_.isVersion).toSet
  }

  /** Created labelled URNs for the set of
  * versions in this catalog.
  */
  def labelledVersions: Set[LabelledCtsUrn] = {
    versions.map(urn => LabelledCtsUrn(urn, versionLabel(urn)) )
  }

  /** Find Set of all versions represented in the catalog.
  */
  def exemplars: Set[CtsUrn] = {
     texts.map(_.urn.toExemplar).distinct.filter(_.isExemplar).toSet

  }

  /** Created labelled URNs for the set of
  * versions in this catalog.
  */
  def labelledExemplars: Set[LabelledCtsUrn] = {
    exemplars.map(urn => LabelledCtsUrn(urn, exemplarLabel(urn)) )
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

  /** Serialize catalog to a String in CEX format.
  *
  * @param delimiter String value to use as column delimiter.
  */
  def cex(delimiter: String = "#") : String = {
    val header =        s"""urn${delimiter}citationScheme${delimiter}groupName${delimiter}workTitle${delimiter}versionLabel${delimiter}exemplarLabel${delimiter}online${delimiter}lang"""
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
