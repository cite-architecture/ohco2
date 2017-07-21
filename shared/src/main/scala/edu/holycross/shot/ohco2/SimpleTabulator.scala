package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.annotation.tailrec

import scala.scalajs.js
import js.annotation.JSExport


/** Factory for building [[edu.holycross.shot.ohco2.Corpus]] instances
* from cataloged XML source files.
*/
object SimpleTabulator {

  /** Create a Corpus from ...

  */
  def apply(): Corpus = {
    val citableNodes = Vector.empty[CitableNode]

    Corpus(citableNodes)
  }

}
