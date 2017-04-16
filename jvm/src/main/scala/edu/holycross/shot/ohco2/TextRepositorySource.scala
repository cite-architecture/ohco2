package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.io.Source
import java.io._
import scala.collection.mutable.ArrayBuffer

import edu.harvard.chs.cite.{CtsUrn => HpUrn}
import edu.holycross.shot.hocuspocus._

import scala.xml._


/** Factory for [[TextRepository]] objects and
* string representations of repositories in
* `.cex` format.
*/
object TextRepositorySource {


  /** Create [[TextRepository]] from file with CEX data.
  *
  * @param cexFile File with data in CEX format.
  * @param delimiter String value used to delimit columns
  * of CEX data.
  */
  def fromCexFile(cexFile: String, delimiter: String = "\ts"): TextRepository = {
    TextRepository(Source.fromFile(cexFile).getLines.toVector.mkString("\n"))
  }

  /** Convert an online text documented by an [[OnlineDocument]] to a two-column string.
  *
  * @param doc Documentation of the text to convert.
  * @param invFile File of old-school hocuspocus TextInventory XML.
  * @param confFile File of old-school hocuspocus CitationConfiguration XML.
  */
  def cexForDocument(doc: OnlineDocument, invFile: String, confFile: String,inputDelim: String = "#",outputDelim: String = "\t"): String = {
    doc.format match {
      case Wf_Xml => cexForXml(doc, invFile, confFile,outputDelim = outputDelim )
      case Markdown => cexForMarkdown(doc,invFile,confFile,outputDelim)
      case _ => ""
    }
  }


  /** Convert an online XML file to a two-column string.
  *
  * @param doc Documentation of the text to convert.
  * @param invFile File of old-school hocuspocus TextInventory XML.
  * @param confFile File of old-school hocuspocus CitationConfiguration XML.
  */
  def cexForXml(doc: OnlineDocument, invFile: String, confFile: String, inputDelim: String = "#",outputDelim: String = "\t"): String = {
    val urn = new HpUrn(doc.urn.toString)
    val f = new File(doc.docName)

    val inventory = new TextInventory(new File(invFile))
    val citationConfig = new CitationConfigurationFileReader(new File(confFile))

    val xmlTab = new XmlTabulator()
    val tabString =  xmlTab.tabulateFile(urn, inventory, citationConfig, f)

    val oxf = tabString.split("\n").filterNot(_.contains("namespace")).toVector
    val twocols =   twoColumnsFromHocusPocus(oxf.mkString("\n"), inputDelim,outputDelim)
    twocols
  }



  def cexForMarkdown(doc: OnlineDocument, invFile: String, confFile: String, outputDelim: String = "#"): String = {
    val f = new File(doc.docName)

    val twoCols = MdTabulator.mdFileToTwoColumns(f, doc.urn.toString, outputDelim)

    twoCols
  }


  /** Find title string for a notional work in a TextInventory's `work` XML node.
  *
  * @param n Parsed `work` node.
  */
  def titleFromNode(n: Node) = {
    val titleNodes = n \\ "title"
    titleNodes(0).text
  }


  /** Find string name for a text group in a TextInventory's XML `textgroup` node.
  *
  * @param n Parsed `textgroup` node.
  */
  def groupNameFromNode(n: Node)= {
    val nameNodes = n \\ "groupname"
    nameNodes(0).text
  }


  /** Find label for optional components of
  * OHCO2 work hierarchy  in a TextInventory's
  * node for `edition`, `translation` or `exemplar`.
  *
  * @param n Parsed XML node for `edition`, `translation` or `exemplar`.
  */
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


  /** Create a [[Catalog]] from it XML
  * representation in a text inventory
  * and accompanying citation configuration
  * document.
  *
  * @param xmlInventory XML validating against
  * the CTS TextInventory schema.
  * @param xmlCitationConf XML validating against
  * the CTS CitationConfiguration schema.
  */
  def catalogFromXml (xmlInventory: String, xmlCitationConf: String) : Catalog = {
    //var catalogEntries = ArrayBuffer[CatalogEntry]()

    val citeConfRoot = XML.loadString(xmlCitationConf)
    val onlineVector = onlineDocsFromXml(citeConfRoot)

    var catalogEntries = scala.collection.mutable.ArrayBuffer.empty[CatalogEntry]
    val invRoot = XML.loadString(xmlInventory)

    val tgs = invRoot \\ "textgroup"
    for (tg <- tgs) {
      val textGroupName = groupNameFromNode(tg)

      val wks = tg \\ "work"
      for (wk <- wks) {
        val title = titleFromNode(wk)

        val edd = wk \\ "edition"
        for (ed <- edd) {
          val versionOpt = labelFromNode(ed)
          val urnAttrs = ed \\ "@urn"
          val edUrn = CtsUrn(urnAttrs(0).text)

          val edOnline  = onlineVector.filter(_.urn == edUrn)
          if (edOnline.size == 1) {
            val online = edOnline(0)
            val citeType = online.format.toString
            val catEntry =  CatalogEntry(
              urn = edUrn,
              groupName = textGroupName,
              citationScheme = citeType,
              workTitle = title,
              versionLabel = versionOpt,
              exemplarLabel = None,
              online =     true)
            catalogEntries += catEntry

          } else {}

          val exemplars = ed \\ "exemplar"
          for (ex <- exemplars) {
            val exemplarOpt = labelFromNode(ex)


            val urnAttrs = ex \\ "@urn"
            val exUrn = CtsUrn(urnAttrs(0).text)

            val exOnline  = onlineVector.filter(_.urn == exUrn)
            if (exOnline.size == 1) {
              val online = exOnline(0)
              val citeType = online.format.toString
              val catEntry =  CatalogEntry(
                urn = exUrn,
                groupName = textGroupName,
                citationScheme = citeType,
                workTitle = title,
                versionLabel = versionOpt,
                exemplarLabel = exemplarOpt,
                online =     true)
              catalogEntries += catEntry
            } else {}


          }
        }

        val xlations = wk \\ "translation"
        for (xlate <- xlations) {
          val versionOpt = labelFromNode(xlate)

          val exemplars = xlate \\ "exemplar"
          for (ex <- exemplars) {
            val exemplarOpt = labelFromNode(ex)

            val urnAttrs = ex \\ "@urn"
            val xlateUrn = CtsUrn(urnAttrs(0).text)

            val xlateOnline  = onlineVector.filter(_.urn == xlateUrn)
            if (xlateOnline.size == 1) {
              val online = xlateOnline(0)
              val citeType = online.format.toString
              val catEntry =  CatalogEntry(
                urn = xlateUrn,
                groupName = textGroupName,
                citationScheme = citeType,
                workTitle = title,
                versionLabel = versionOpt,
                exemplarLabel = exemplarOpt,
                online =  true)
              catalogEntries += catEntry
            } else {}
          }
        }
      }
    }
    Catalog(catalogEntries.toVector)
  }


  /** Create a [[Catalog]] from its XML
  * representation in a text inventory
  * and accompanying citation configuration
  * document.
  *
  * @param invFile XML file validating against
  * the CTS TextInventory schema.
  * @param citationConfFile XML file validating against
  * the CTS CitationConfiguration schema.
  */
  def catalogFromXmlFile(invFile: String, citationConfFile: String) = {
    val inv = Source.fromFile(invFile).getLines.mkString("\n")
    val cites = Source.fromFile(citationConfFile).getLines.mkString("\n")
    catalogFromXml(inv,cites)
  }


  /** Create complete CEX representation of
  * a text repository from local files.
  *
  * @param invFileName Name of file with basic inventory of texts and their citation schemes.
  * @param configFileName Name of file with details about location and format of files.
  * @param baseDirectoryName Name of root directory where local files are found.
  */
  def cex(invFileName: String,
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
    baseDirectoryName: String,
    outputDelimiter: String = "#"): TextRepository = {

    val catalog = catalogFromXmlFile(invFileName, configFileName)

    val onlines = onlineVector(configFileName, baseDirectoryName)

    val twocols = for(doc <-onlines) yield {
      cexForDocument(doc,invFileName,configFileName,"#", outputDelimiter)
    }

    val corpus = Corpus(twocols.mkString("\n"), outputDelimiter)

    TextRepository(corpus,catalog)
  }


}
