package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.io.Source
import java.io._

import edu.harvard.chs.cite.{CtsUrn => HpUrn}
import edu.holycross.shot.hocuspocus._


import scala.xml._

object TextRepositorySource {


  /** Find [[DocumentFormat]] value for
  * a String name.
  *
  * @param value String name of format.
  */
  def formatFromString(value: String): Option[DocumentFormat] = {
    Vector(Wf_Xml, Two_Column, Markdown, Oxf).find(_.name == value)
  }


  /** Create [[OnlineDocument]] from its
  * representation in a text configuraiton file
  * as an XML `online` element.
  *
  * @param n Parsed `online` node.
  */
  def documentFromNode(n: scala.xml.Node) : OnlineDocument = {
    val urnSeq = n \\ "@urn"
    val typeSeq = n \\ "@type"
    val nameSeq = n \\ "@docname"

    val urn = CtsUrn(urnSeq(0).text)
    val format = formatFromString(typeSeq(0).text).get
    val fileName = nameSeq(0).text

    OnlineDocument(urn,format,fileName)
  }


  /** Create a text repository from a cataloged
  * set of files in a local file system.
  *
  * @param invFileName Name of file with basic inventory of texts and their citation schemes.
  * @param configFileName Name of file with details about location and format of files.
  * @param baseDirectoryName Name of root directory where local files are found.
  */
  def fromLocalFiles(invFileName: String,
    configFileName: String,
    baseDirectoryName: String
  ) = { //: TextRepository = {

    val configRoot = scala.xml.XML.loadFile(configFileName)
    val files = configRoot \\ "online"
    val docs = files.map(documentFromNode(_))
    //

  }

}
