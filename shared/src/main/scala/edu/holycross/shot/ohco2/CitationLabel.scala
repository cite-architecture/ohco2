package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._

import scala.scalajs.js
import scala.scalajs.js.annotation._


/**
*
* @param urn URN for the version.
* @param label Label for citation scheme, with levels
* separated by "/", e.g., "book/chapter
*/
@JSExportTopLevel("CitationLabel") case class CitationLabel(urn: CtsUrn, citationScheme: String) {
}
