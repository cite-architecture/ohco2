package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.io.Source
import java.io._
import scala.collection.mutable.ArrayBuffer


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
  */
  def cexForDocument(doc: OnlineDocument, outputDelim: String = "#"): String = {
    doc.format match {
      case Wf_Xml => cexForXml(doc, outputDelim )
      case Markdown => "Not currently implemented" //cexForMarkdown(doc,invFile,confFile,inputDelim, outputDelim)
      case _ => ""
    }
  }




  /** Convert an online XML file to a two-column string.
  *
  * @param doc Documentation of the text to convert.
  * @param inputDelim Delimiter used in source files.
  * @param outputDelim Delimiter to use in CEX output.
  */
  def cexForXml(doc: OnlineDocument, outputDelim: String = "#"): String = {
    val xml = Source.fromFile(doc.docName).getLines.mkString("\n")
    val corpus = SimpleTabulator(doc.urn, XPathTemplate(doc.xpathTemplate.get), xml)
    //def apply(docUrn: CtsUrn, xpTemplate: XPathTemplate, xmlString: String): Corpus
    corpus.to2colString(outputDelim)

  }
      /*
    val urn = new HpUrn(doc.urn.toString)
    val f = new File(doc.docName)

    val inventory = new TextInventory(new File(invFile))
    val citationConfig = new CitationConfigurationFileReader(new File(confFile))
    println("CITATION CONF: "+ citationConfig)
    val xmlTab = new XmlTabulator()
    val tabString =  xmlTab.tabulateFile(urn, inventory, citationConfig, f)
    println("TAB STRING " + tabString)
    val oxf = tabString.split("\n").filterNot(_.contains("namespace")).toVector
    val twocols =   twoColumnsFromHocusPocus(oxf.mkString("\n"), inputDelim,outputDelim)
    twocols
  }
*/
  def cexForMarkdown(doc: OnlineDocument, invFile: String, confFile: String, outputDelim: String = "#"): String = {
    val f = new File(doc.docName)

    /*
    val twoCols = MdTabulator.mdFileToTwoColumns(f, doc.urn.toString, outputDelim)

    twoCols
    */
    "Not currently implemented"
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
    baseDirectoryName: String,
    delim1: String = "#",
    delim2: String = ",") = {
      val v = onlineVector(configFileName, baseDirectoryName, delim1,delim2)

      v
  }


  /** Create a vector of [[OnlineDocument]]s from a cataloged
  * set of files in a local file system.
  *
  * @param configFileName Name of file with details about location and format of files.
  * @param baseDirectoryName Name of root directory where local files are found.
  */
  def onlineVector(
    configFileName: String,
    baseDirectoryName: String,
    delimiter1: String = "#",
    delimiter2: String = ","
  ): Vector[OnlineDocument] = {
    val lines = Source.fromFile(configFileName).getLines.toVector.drop(1)
    val docs = lines.map(OnlineDocument(_, delimiter1, delimiter2)).toVector
    docs.map(_.absolutePath(baseDirectoryName))
  }

  /** Create TextRepository from local files.
  */
  def fromFiles(catalogFileName: String,
    configFileName: String,
    baseDirectoryName: String,
    delimiter: String = "#"): TextRepository = {

    val catalogText = Source.fromFile(catalogFileName).getLines.mkString("\n")

/*



    val twocols = for(doc <-onlines) yield {
      cexForDocument(doc,invFileName,configFileName,"#", outputDelimiter)
    }

    val corpus = Corpus(twocols.mkString("\n"), outputDelimiter)
*/
    val corpus = Corpus(Vector.empty[CitableNode])
    val catalog = Catalog(catalogText, delimiter)

    println("SIZE OF XCAT: " + catalog.size)

    TextRepository(corpus,catalog)
  }


}
