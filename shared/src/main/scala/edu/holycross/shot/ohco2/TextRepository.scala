package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._


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

  // enforce 1-1 relation of texts cataloged as online
  // and texts cited in the corpus
  require(online.texts.map(_.urn).toSet == corpus.citedWorks.toSet, "Online catalog (" + online.size + " texts) did not match works appearing in corpus (" + corpus.citedWorks.size + " texts)")



}
