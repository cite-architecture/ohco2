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
* @param texts Set of catalog entries.
*/
trait Catalog[CatImpl]  extends LogSupport {


  /** Catalog of texts. */
  def texts: Vector[CatalogEntry]

  /** Create a Catalog of texts from CEX source. */
  def fromCex(data: String, sep: String = "#"): CatImpl

  def entriesForUrn(filterUrn: CtsUrn): Vector[CatalogEntry]

  /** Create a new catalog by adding a second corpus to this one.
  *
  * @param catalog2 Catalog to add to this one.
  */
  def ++(catalog2: CatImpl): CatImpl

  /** Create a new catalog by subtracting a catalog corpus from this one.
  *
  * @ catalog2 second catalog with contents to be removed from this one.
  */
  def --(catalog2: CatImpl) : CatImpl


  // ensure unique urns
  def urnList = texts.map(_.urn)
  val dupes = urnList.groupBy(identity).collect {
    case (x,ys) if ys.lengthCompare(1) > 0 => x
  }
  require(dupes.size == 0, s"""Duplicated URN values: ${dupes.mkString(",")}""")


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
      //debug("WORKS TOC " + s"${w}->${vers}")
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
      //debug(wks.toSeq.toVector)
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
  def groupName(u: CtsUrn): String

  /** Find title of work for a given URN.
  *
  * @param u URN to look up in catalog.
  */
  def workTitle(u: CtsUrn): String

  /** Find label for a version of a work for a given URN.
  *
  * @param u URN to look up in catalog.
  */
  def versionLabel(u: CtsUrn): String

  /** Find label for an exemplar of a work for a given URN.
  *
  * @param u URN to look up in catalog.
  */
  def exemplarLabel(u: CtsUrn): String

  /** Create string label describing all catalog entries matching a
  * given URN.
  *
  * @param urn Urn to match.
  */
  def labels(urn: CtsUrn) : String

  /** Create string label describing catalog entry matching a
  * given URN.
  *
  * @param urn Urn to match.
  */
  def label(urn: CtsUrn) : String

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
