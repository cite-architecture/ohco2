package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._

case class TextRepository (corpus: Corpus, catalog: Catalog) {
  // enforce 1-1 relation of texts cataloged as online
  // and texts cited in the corpus
  val online = catalog.texts.filter(_.online)
  require(online.map(_.urn).toSet == corpus.citedWorks.toSet, "Online catalog (" + online.size + " texts) did not match works appearing in corpus (" + corpus.citedWorks.size + " texts)")
}
