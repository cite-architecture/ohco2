package edu.holycross.shot.ohco2
import edu.holycross.shot.cite._
import scala.scalajs.js
import scala.scalajs.js.annotation._


/** Number of occurrences of a String in a corpus.
*
* @param s String
* @param count Number of occurrences in a corpus.
*/
@JSExportAll case class StringCount (s: String, count: Int) {
  override def toString = {
    s"${count} ${s}"
  }
}

/** Counts of occurrences of strings.
*
* @param histogram String counts, created in descending order by
* [[Corpus]] object's histogram builders.
*/
@JSExportAll case class StringHistogram (histogram: Vector[StringCount]) {

  /** Create a new StringHistogram containing only Strings
  * matching a given pattern.
  *
  * @param s2 String to match.
  */
  def stringMatch(s2: String): StringHistogram = {
    StringHistogram(histogram.filter(_.s.contains(s2)))
  }

  /** Number of entries in string counts in histogram.*/
  def size: Int =  {
    histogram.size
  }

  /** Override default toString function.
  */
  override def toString = {
    histogram.mkString("\n")
  }
}
