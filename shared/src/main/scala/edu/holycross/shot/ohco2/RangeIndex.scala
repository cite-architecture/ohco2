package edu.holycross.shot.ohco2

import scala.scalajs.js
import scala.scalajs.js.annotation._

/** A cataloged corpus of texts.
*
* @param a Index of first node in range.
* @param b Index of last node in range.
*/
@JSExportAll case class RangeIndex (a: Int, b: Int) {

  /** Number of nodes in range.
  */
  def size: Int = {
    b - a + 1
  }
}
