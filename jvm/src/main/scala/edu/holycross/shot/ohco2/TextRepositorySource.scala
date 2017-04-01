package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.io.Source
import java.io._

import edu.harvard.chs.cite.{CtsUrn => HpUrn}
import edu.holycross.shot.hocuspocus._

import scala.xml._


/** Factory for [[TextRepository]] objects and
* string representations of repositories in
* `.cex` format.
*/
object TextRepositorySource {

  /** Find [[DocumentFormat]] value for
  * a String name.
  *
  * @param value String name of format.
  */
  def formatFromString(value: String): Option[DocumentFormat] = {
    Vector(Wf_Xml, Two_Column, Markdown, Oxf).find(_.name == value)
  }


  /** Format directory plus file as a path String.
  *
  * @param dir Directory name.
  * @param f File name
  */
  def fileName(dir: String, f: String) : String = {
    if (dir.last == '/') {
      dir + f
    } else {
      dir + "/" + f
    }
  }


  /** Create [[OnlineDocument]] from its
  * representation in a text configuraiton file
  * as an XML `online` element.
  *
  * @param n Parsed `online` node.
  */
  def documentFromNode(n: scala.xml.Node, baseDirectory: String) : OnlineDocument = {
    val urnSeq = n \\ "@urn"
    val typeSeq = n \\ "@type"
    val nameSeq = n \\ "@docname"

    val urn = CtsUrn(urnSeq(0).text)
    val format = formatFromString(typeSeq(0).text).get
    val fName = fileName(baseDirectory, nameSeq(0).text)

    OnlineDocument(urn,format,fName)
  }


  /** Convert an online document to a two-column string.
  *
  * @param doc Document to convert.
  */
  def cefFromDocument(doc: OnlineDocument): String = {
    println(doc.docName)
    doc.format match {
      case Wf_Xml => "XML CONvERTED"
      case _ => ""
    }
  }

  /** Create a vector of [[OnlineDocument]]s from a cataloged
  * set of files in a local file system.
  *
  * @param configFileName Name of file with details about location and format of files.
  * @param baseDirectoryName Name of root directory where local files are found.
  */
  def documentVector(
    configFileName: String,
    baseDirectoryName: String
  ): Vector[OnlineDocument] = {
    val configRoot = scala.xml.XML.loadFile(configFileName)
    val files = configRoot \\ "online"
    val docs = files.map(documentFromNode(_, baseDirectoryName))
    docs.toVector
  }


  /** Create complete CEF representation of
  * a text repository from local files.
  *
  * @param invFileName Name of file with basic inventory of texts and their citation schemes.
  * @param configFileName Name of file with details about location and format of files.
  * @param baseDirectoryName Name of root directory where local files are found.
  */
  def cef(invFileName: String,
    configFileName: String,
    baseDirectoryName: String) = {
      val v = documentVector(configFileName, baseDirectoryName)
      v
  }
}
