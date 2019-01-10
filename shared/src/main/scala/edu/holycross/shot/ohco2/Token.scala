package edu.holycross.shot.ohco2
import edu.holycross.shot.cite._
import scala.scalajs.js
import scala.scalajs.js.annotation._

import scala.annotation.tailrec

/** Tokenization is a langauge and corpus-specific
* operation, the results should share these functions.
*/
@JSExportAll trait Token  {
  /** CitableNode in an analytical exemplar.*/
  def citableNode : CitableNode

  /** True if token is a lexical token that
  * can be subjected to morphological analysis.*/
  def lexical : Boolean
}
