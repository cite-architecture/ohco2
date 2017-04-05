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
      val v = onlineVector(configFileName, baseDirectoryName)
      v
  }


  /** Create TextRepository from local files.
  */
  def fromFiles(invFileName: String,
    configFileName: String,
    baseDirectoryName: String) = {

      /*
    val invFile = new File(invFileName)
    val inventory = new TextInventory(invFile)

    val confFile = new File(configFileName)
    val citationConfig = new CitationConfigurationFileReader(confFile)
*/


/*
val xmlTab = new XmlTabulator()


val confFile = new File("testdata/xmlCorpus/xmlcitationconfig.xml")
val citationConfig = new CitationConfigurationFileReader(confFile)

val urn = new CtsUrn("urn:cts:greekLit:tlg0016.tlg001.grcTest:")

val f = new File("testdata/xmlCorpus/xml/test-hdt-grc.xml")

val tabString =  xmlTab.tabulateFile(urn, inventory, citationConfig, f)
*/
    }
}
