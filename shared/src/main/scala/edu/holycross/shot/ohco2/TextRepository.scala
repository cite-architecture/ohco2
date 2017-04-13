package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import edu.holycross.shot.cex._

import scala.scalajs.js
import js.annotation.JSExport
/** A cataloged corpus of texts.
*
* @param corpus The text contents.
* @param catalog The catalog
*/
@JSExport  case class TextRepository (corpus: Corpus, catalog: Catalog) {

  /** Create a new catalog containing only online texts.
  */
  def online: Catalog = Catalog(catalog.texts.filter(_.online))

  /** Create label for passage identified by URN.
  */
  def label(urn: CtsUrn): String = {
    catalog.label(urn.dropPassage) + " " + urn.passageComponent
  }


  /** Serialize repository to CITE Exchange format.
  *
  * @param delimiter String value to use as delimiter between columns.
  */
  def cex(delimiter: String = "\t") : String = {
    s"""#!ctscatalog\n${catalog.cex(delimiter)}#!ctsdata\n${corpus.cex(delimiter)}"""
  }

  // enforce 1-1 relation of texts cataloged as online
  // and texts cited in the corpus
  require(online.texts.map(_.urn).toSet == corpus.citedWorks.toSet, "Online catalog (" + online.size + " texts) did not match works appearing in corpus (" + corpus.citedWorks.size + " texts)")

/*
  def find(s: String): TextRepository = {
      val sCorpus = corpus.find(s)
  }*/

}


/** Factory for constructing [[TextRepository]] fromFile
* source data in CEX format.
*/
object TextRepository {


  /** Create TextRepository from CEX data.
  *
  * @param cexString Data in CEX format.
  * @param delimiter String value delimiting columns
  * in CEX data.
  */
  def apply(cexString: String, delimiter: String = "#") : TextRepository = {
    /*
    val sections = cex.split("#!").filter(_.nonEmpty)
    val typeList = sections.map(_.split("\n")(0))

    val catalogString = sections(typeList.indexOf("ctscatalog"))
    val catalogData = catalogString.split("\n").drop(1).mkString("\n")
    */

    val cex = CexParser(cexString)

    val catalog = Catalog(cex.blocks("ctscatalog"),delimiter)

    /*


    val corpusString = sections(typeList.indexOf("ctsdata"))
    val corpusData = corpusString.split("\n").drop(1).mkString("\n")
    */
    val corpus = Corpus(cex.blocks("ctsdata"), delimiter)

    TextRepository(corpus,catalog)
  }
}
