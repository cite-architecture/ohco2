package edu.holycross.shot.ohco2
import edu.holycross.shot.cite._
import scala.scalajs.js
import js.annotation.JSExport


/** Simplify syntax of working with tuples when counting strings.
*
* @param s String
* @param count Number of occurrences in a corpus.
*/
@JSExport  case class StringCount (s: String, count: Int) {
  override def toString = {
    s"${count} ${s}"
  }
}

/** Counts of occurrences of strings.
*
* @param histogram String counts, created in descending order by
* [[Corpus]] object's histogram builders.
*/
@JSExport  case class StringHistogram (histogram: Vector[StringCount]) {

  /** Create a new StringHistogram containing only Strings
  * matching a given pattern.
  *
  * @param s2 String to match.
  */
  def stringMatch(s2: String) = {
    StringHistogram(histogram.filter(_.s.contains(s2)))
  }

  override def toString = {
    histogram.mkString("\n")
  }
}
