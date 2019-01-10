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


  /** Create a [[TextRepository]] from a single file with CEX data.
  *
  * @param cexFile File with data in CEX format.
  * @param delimiter String value used to delimit columns
  * of CEX data.
  */
  def fromCexFile(cexFile: String, delimiter: String = "#", encoding: String = "UTF-8"): TextRepository = {
    TextRepository(Source.fromFile(cexFile, encoding).getLines.toVector.mkString("\n"))
  }

  /** Convert an online text documented by an [[OnlineDocument]]
  * object to a two-column string.
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

  /** Convert a single XML file documentd by an [[OnlineDocument]]
  * object to a two-column string.
  *
  * @param doc Documentation of the text to convert.
  * @param inputDelim Delimiter used in source files.
  * @param outputDelim Delimiter to use in CEX output.
  */
  def cexForXml(doc: OnlineDocument, outputDelim: String = "#", encoding: String = "UTF-8"): String = {
    val xml = Source.fromFile(doc.docName, encoding).getLines.mkString("\n")
    val corpus = SimpleTabulator(doc.urn, XPathTemplate(doc.xpathTemplate.get), xml)
    corpus.to2colString(outputDelim)
  }


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


  // this is messed up...
  def cex(invFileName: String,
    configFileName: String,
    baseDirectoryName: String,
    delim1: String = "#",
    delim2: String = ",") = {
      val v = onlineVector(configFileName, baseDirectoryName, delim1,delim2)

      v
  }

  def corpusFromOnlineVector (onlineVect: Vector[OnlineDocument]): Corpus = {
    val cex = onlineVect.map(cexForDocument(_))
    Corpus(cex.mkString("\n"))
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
    delimiter2: String = ",",
    encoding: String = "UTF-8"
  ): Vector[OnlineDocument] = {
    val lines = Source.fromFile(configFileName, encoding).getLines.toVector.drop(1)
    val docs = lines.map(OnlineDocument(_, delimiter1, delimiter2)).toVector
    docs.map(_.absolutePath(baseDirectoryName))
  }

  /** Create a TextRepository from local files.
  *
  * @param catalogFileName Name of file with catalog of texts in CEX format.
  * @param configFileName Name of file with delimited-text data mapping cataloged texts
  * to typed local files.
  * @param baseDirectoryName Base directory for local files.  Paths in the configuration
  * file are relative to this directory.
  * @param delimter Top-level delimiter for CEX structure.
  * @param delimiter2 Secondary delimiter for CEX structure.
  */
  def fromFiles(catalogFileName: String,
    configFileName: String,
    baseDirectoryName: String,
    delimiter: String = "#",
    delimiter2 : String = ",",
    encoding: String = "UTF-8"
  ): TextRepository = {

    val catalogText = Source.fromFile(catalogFileName, encoding).getLines.mkString("\n")
    val catalog = Catalog(catalogText, delimiter)

    val onlineVect = TextRepositorySource.onlineVector(configFileName, baseDirectoryName)
    val corpus = corpusFromOnlineVector (onlineVect)

    TextRepository(corpus,catalog)
  }


}
