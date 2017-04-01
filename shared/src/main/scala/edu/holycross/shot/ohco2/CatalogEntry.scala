package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import scala.util.Try

import scala.scalajs.js
import js.annotation.JSExport


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
@JSExport case class CatalogEntry(urn: CtsUrn, citationScheme: String, groupName: String, workTitle: String, versionLabel: Option[String], exemplarLabel: Option[String] = None, online: Boolean = true) {
  require(citationScheme.nonEmpty,"citation scheme cannot be empty")
  require(groupName.nonEmpty,"text group name cannot be empty")
  require(workTitle.nonEmpty,"work title cannot be empty")
  //require(versionLabel.nonEmpty,"version label cannot be empty")
  urn.workLevel match {
    case WorkLevel.Work => {
      exemplarLabel match {
        case None => // ok
        case _ => throw Ohco2Exception("exemplar label must be none when URN is at notional level")
      }

      versionLabel match {
        case None => // ok
        case _ => throw Ohco2Exception("version label must be none when URN is at notional level")
      }
    }

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
    versionLabel match {
      case vers: Some[String] =>
        exemplarLabel match {
          case lbl: Some[String] => baseString + s" (${versionLabel.get}: ${lbl.get})"
          case None => baseString + s" (${versionLabel.get})"
        }

      case None => baseString
    }

    /*

    */
  }

  /** Pretty print with URN.
  */
  def fullLabel: String = {
    toString + s". ${urn}"
  }

  /** Serialization in .cex format.
  *
  * @param delimiter String to use as column separator.
  */
  def cex(delimiter: String = "\t"): String = {
    s"""${urn}${delimiter}${citationScheme}${delimiter}${groupName}${delimiter}${workTitle}${delimiter}${versionLabel.getOrElse("")}${delimiter}${exemplarLabel.getOrElse("")}${delimiter}${online}"""
  }
}
