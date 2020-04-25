package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import edu.holycross.shot.cex._

import scala.scalajs.js
import scala.scalajs.js.annotation._

/** A cataloged corpus of texts.
*
* @param corpus The text contents.
* @param catalog The catalog.
*/
@JSExportAll case class TextRepository (corpus: Corpus, catalog: Catalog) {


  /** Create a new repository by adding a second repository to this one.
  *
  * @param repo2 Catalog to add to this one.

  TBA def ++(repo2: TextRepository): TextRepository = {
    TextRepository(
      corpus ++ repo2.corpus,
      catalog ++ repo2.catalog
    )
  }
  */

  /** Create a new repository by subtracting a second repository from this one.
  *
  * @param repo2 Catalog to add to this one.

  def -- (repo2: TextRepository): TextRepository = {
    TextRepository(
      corpus -- repo2.corpus,
      catalog -- repo2.catalog
    )
  }  */


  /** Create a new catalog containing only online texts.

  def online: Catalog = Catalog.fromCex(catalog.texts.filter(_.online))
  */
  /** Create label for passage identified by URN.
  */
  def label(urn: CtsUrn): String = {
    catalog.label(urn.dropPassage) + " " + urn.passageComponent
  }


  /** Serialize repository to CITE Exchange format.
  *
  * @param delimiter String value to use as delimiter between columns.
  */
  def cex(delimiter: String = "#") : String = {
    //s"""#!ctscatalog\n${catalog.cex(delimiter)}#!ctsdata\n${corpus.cex(delimiter)}"""
    "TBA"
  }

  // enforce 1-1 relation of texts cataloged as online
  // and texts cited in the corpus
  /*
  require(online.texts.map(_.urn).toSet == corpus.citedWorks.toSet, "Online catalog (" + online.size + " texts) did not match works appearing in corpus (" + corpus.citedWorks.size + s" texts).  Catalog ${online.texts.map(_.urn).toSet} vs. online corpus ${corpus.citedWorks.toSet}")
*/
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

  def apply(cexString: String, delimiter: String = "#") : TextRepository = {
    val cex = CexParser(cexString)
    val catalog = Catalog.fromCex(cex.blockString("ctscatalog"),delimiter)
    val corpus = Corpus(cex.blockString("ctsdata"), delimiter)

    TextRepository(corpus,catalog)
  }  */
}
