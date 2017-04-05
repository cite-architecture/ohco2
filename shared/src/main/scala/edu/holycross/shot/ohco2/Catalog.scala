package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import scala.util.Try


import scala.xml._

import scala.scalajs.js
import js.annotation.JSExport


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


  def cex(delimiter: String = "\t") : String = {
    val header =        s"""urn${delimiter}citationScheme${delimiter}groupName${delimiter}workTitle${delimiter}versionLabel${delimiter}exemplarLabel${delimiter}online"""
    val cexEntries = texts.map(_.cex(delimiter))

    (header +:  cexEntries).mkString("\n") + "\n"
  }
}

/** Factory for making catalogs from text sources.
*/
object Catalog {


  /*
  urn: CtsUrn,
  groupName: String,
  workTitle: String,

  versionLabel: Option[String],
  exemplarLabel: Option[String] = None,

  citationScheme: String,

  online: Boolean = true

      */



  def titleFromNode(n: Node) = {
    val titleNodes = n \\ "title"
    titleNodes(0).text
  }
  def groupNameFromNode(n: Node)= {
    val nameNodes = n \\ "groupname"
    nameNodes(0).text
  }
  def labelFromNode(n: Node): Option[String] = {
    val labelNodes = n \\ "label"
    labelNodes.size match {
      case 0 => None
      case _ => Some(labelNodes(0).text)
    }
  }


  def formatForString(s: String): DocumentFormat = {
    s match {
      case "xml" => Wf_Xml
      case "markdown" => Markdown
      case "twocol" => Two_Column
      case "82xf" => Oxf
    }
  }


  // get a vector of [[OnlineDocument]]s
  def onlineDocsFromXml(root: Node): Vector[OnlineDocument] = {

    var onlines = scala.collection.mutable.ArrayBuffer.empty[OnlineDocument]
    val onlineNodes = root \\ "online"

    for (n <- onlineNodes) {
      val urnAttrs = n \\ "@urn"
      val urn = CtsUrn(urnAttrs(0).text)

      val formatAttrs = n \\ "@type"
      val format = formatForString(formatAttrs(0).text)

      val docNameAttrs = n \\ "@docname"
      val docName = docNameAttrs(0).text
      onlines +=  OnlineDocument(urn,format,docName)
    }

    onlines.toVector
  }

  def fromXml (xmlInventory: String, xmlCitationConf: String) : Catalog = {

    var entries = scala.collection.mutable.ArrayBuffer.empty[CatalogEntry]


    val citeConfRoot = XML.loadString(xmlCitationConf)
    val onlineVector = onlineDocsFromXml(citeConfRoot)

    val invRoot = XML.loadString(xmlInventory)

    val tgs = invRoot \\ "textgroup"

    for (tg <- tgs) {
      val urnAttrs = tg \\ "@urn"
      val urn = CtsUrn(urnAttrs(0).text)
      val groupName = groupNameFromNode(tg)
      println(groupName + " " + urn)

      val wks = tg \\ "work"
      for (wk <- wks) {
        val title = titleFromNode(wk)

        val edd = wk \\ "edition"
        for (ed <- edd) {
          val versionLabel = labelFromNode(ed)
          val exemplars = ed \\ "exemplar"
          for (ex <- exemplars) {
            val exLabel = labelFromNode(ex)
          }
        }

        val xlations = wk \\ "translation"
        for (xlate <- xlations) {
          val versionLabel = labelFromNode(xlate)
          val exemplars = xlate \\ "exemplar"
          for (ex <- exemplars) {
            val exLabel = labelFromNode(ex)

            // CHECK in onlineVector...
            // figure out something for citation scheme...
            // and create new CatalogEntry!
          }
        }
      }
    }
    Catalog(entries.toVector)
  }




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
      val vers = {

        if (row(4).isEmpty) {
          None
        } else {
          Some(row(4))
        }
      }

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
