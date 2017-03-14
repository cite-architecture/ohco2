package edu.holycross.shot.ohco2
import edu.holycross.shot.cite._
import scala.scalajs.js
import js.annotation.JSExport
import scala.annotation.tailrec

/** The smallest canonically citable unit of a text.
*
* @param urn URN identifying the node.
* @param text Text contents of the node.
*/
@JSExport  case class CitableNode (urn: CtsUrn, text: String) {
  if (text.isEmpty) {
    throw new Ohco2Exception("CitableNode: text content cannot be empty")
  }

  /** True if this node matches a second URN.
  *
  * @param urn2 URN to compare to this node.
  */
  def ~~ (urn2: CtsUrn): Boolean = {
    (this.urn ~~ urn2)
  }

  /** True if text content matches a given string.
  *
  * @param s String to test for.
  */
  def matches(s: String): Boolean = {
    (this.text.contains(s))
  }


  /** True if text content matches all strings in a given list.
  * Recursively compares each string in the list, and sets a
  * flag to false if there is no match.
  *
  * @param v List of string to test for.
  * @param checkBox True if all strings seen so far have matched.
  */
  @tailrec final def matches(v: Vector[String], checkBox: Boolean = true): Boolean = {
    if (v.isEmpty) {
      checkBox
    } else {
      if (matches(v(0))) {
        matches(v.drop(1), checkBox)
      } else {
        matches(v.drop(1),false)
      }
    }
  }

  /** True if text content includes a given whitespace-delimited
  * token.
  */
  def tokenMatches(t: String): Boolean = {
    val tokens = this.text.split(" ")
    (tokens.contains(t))
  }

  /** Format first n characters of a string for KWIC
  * display.
  *
  * @param s String to extract characters from.
  * @param n Number of characters to include.
  */
  def firstNCharsKwic(s: String, n: Int): String = {
    if (n >= s.size) {
      s
    } else {
      s.slice(0, n) + "..."
    }
  }

  /** Format last n characters of a string for KWIC
  * display.
  *
  * @param s String to extract characters from.
  * @param n Number of characters to include.
  */
  def lastNCharsKwic(s: String, n: Int): String = {
    if (n >= s.size) {
      s
    } else {
      "..."+ s.slice(s.size - n, s.size)
    }
  }

  /** Format a string extracting a given white-space delimited word token
  * surrounded by a given number of neighboring word tokens.
  * If `wordToken` is not present in [[text]], an empty String
  * is returned.
  *
  * @param wordToken String to match.
  * @param context Number of characters to include on either side of
  * the match.
  */
  def kwic(s: String, context: Int = 20): String = {
    if (s == text) {
      s
    } else {
      val chunks = text.split(s).filter(_.nonEmpty)

      chunks.size match {
        case 1 =>  {
          if (text.startsWith(s)) {
            "**" + s + "**" + firstNCharsKwic(chunks(0), context)
          } else {
            lastNCharsKwic(chunks(0), context) + "**" + s + "**"
          }

        }
        case n: Int => {
          val trailString = firstNCharsKwic(chunks(1),context)
          //println("TRAIL ON " + chunks(1) + " at " + context + " yields " + trailString)
          lastNCharsKwic(chunks(0), context) + "**" + s + "**"  + firstNCharsKwic(chunks(1), context)
        }
      }
    }
  }

}
