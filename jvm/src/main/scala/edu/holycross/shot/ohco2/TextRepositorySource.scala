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


  /** Make a Vector of [[OnlineDocument]]s from the
  * contents of an XML citation configuration file.
  *
  * @param root Parsed root node of an XML citation configuration file.
  */
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
      onlines +=  OnlineDocument(urn,format.get,docName)
    }

    onlines.toVector
  }

  def catalogFromXml (xmlInventory: String, xmlCitationConf: String) : Catalog = {
    val citeConfRoot = XML.loadString(xmlCitationConf)
    val onlineVector = onlineDocsFromXml(citeConfRoot)


    var entries = scala.collection.mutable.ArrayBuffer.empty[CatalogEntry]




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

  def catalogFromXmlFile(invFile: String, citationConfFile: String) = {
    val inv = Source.fromFile(invFile).getLines.mkString("\n")
    val cites = Source.fromFile(citationConfFile).getLines.mkString("\n")
    catalogFromXml(inv,cites)
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
    val format = formatForString(typeSeq(0).text).get
    val fName = fileName(baseDirectory, nameSeq(0).text)

    OnlineDocument(urn,format,fName)
  }


  /** Create a vector of [[OnlineDocument]]s from a cataloged
  * set of files in a local file system.
  *
  * @param configFileName Name of file with details about location and format of files.
  * @param baseDirectoryName Name of root directory where local files are found.
  */
  def onlineVector(
    configFileName: String,
    baseDirectoryName: String
  ): Vector[OnlineDocument] = {
    val configRoot = scala.xml.XML.loadFile(configFileName)
    val files = configRoot \\ "online"
    val docs = files.map(documentFromNode(_, baseDirectoryName))
    docs.toVector
  }

  /** Create TextRepository from local files.
  */
  def fromFiles(invFileName: String,
    configFileName: String,
    baseDirectoryName: String) = {
  }


}
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
