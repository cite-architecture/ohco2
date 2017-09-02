package edu.holycross.shot.ohco2
import edu.holycross.shot.cite._
import scala.scalajs.js
import scala.scalajs.js.annotation._

import scala.annotation.tailrec

/** The smallest canonically citable unit of a text.
*
* @param urn URN identifying the node.
* @param text Text contents of the node.
*/
@JSExportAll  case class CitableNode (urn: CtsUrn, text: String) {
  if (text.isEmpty) {
    throw new Ohco2Exception("CitableNode: text content cannot be empty")
  }

  /** True if this node is URN-similar to a second URN.
  *
  * @param urn2 URN to compare to this node.
  */
  def ~~ (urn2: CtsUrn): Boolean = {
    (this.urn ~~ urn2)
  }

  def >< (urn2: CtsUrn): Boolean = {
    (this.urn >< urn2)
  }


  def > (urn2: CtsUrn): Boolean = {
    (this.urn > urn2)
  }

  def >= (urn2: CtsUrn): Boolean = {
    (this.urn >= urn2)
  }


  def < (urn2: CtsUrn): Boolean = {
    (this.urn < urn2)
  }

  def <= (urn2: CtsUrn): Boolean = {
    (this.urn <= urn2)
  }

  /** True if text content matches a given string.
  *
  * @param s String to test for.
  */
  def matches(s: String): Boolean = {
    (this.text.contains(s))
  }

  // use of CiableNode in this function is a hideous kludge.
  // get rid of it.

  /** True if all listed tokens fall within a specified
  * distance in a vector of tokens.
  *
  * @param src Text to test on, as a vector of words.
  * @param v  Vector of tokens to test for.
  * @param distance Distance, in words, to test for.
  */
//  @tailrec final def tokensWithin(src: Vector[String], v: Vector[String],
  def tokensWithin(src: Vector[String], v: Vector[String], distance: Int) : Boolean = {

    // strip off any leading words in the text *not*
    // in the list to search for:
    val stripStart = src.drop(1).dropWhile(! v.contains(_))
    stripStart.size match {
      case n if (n <= distance) => {
        // distance is short enough:  are all tokens present?
        CitableNode(urn,stripStart.mkString(" ")).matches(v,true)
      }


      case _ => {
        val stripEnd = stripStart.reverse.drop(1).dropWhile(! v.contains(_)).reverse
        stripEnd.size match {
          case n2 if (n2 <= distance) =>
            CitableNode(urn,stripEnd.mkString(" ")).matches(v,true)
          case _ =>   tokensWithin(stripEnd,v,distance)
        }
      }

    }
  }


  // REWRiT to AVOID USING CitableNode object here
//  @tailrec final def tokensWithin( v: Vector[String], distance: Int):
def tokensWithin( v: Vector[String], distance: Int): Boolean = {
    val wds = text.split(" ").toVector
    val seq = wds.dropWhile(! v.contains(_)).reverse.dropWhile(! v.contains(_)).reverse
    if (CitableNode(urn,seq.mkString(" ")).matches(v)) {
      if (seq.size <= distance) {
        true
      } else {
        tokensWithin(wds, v, distance)
      }
    } else {
      false
    }
  }


  /** True if text content matches all strings in a given list.
  * Recursively compares each string in the list, and sets a
  * flag to false if there is no match.
  *
  * @param v List of string to test for.
  * @param checkBox True if all strings seen so far have matched.
  */
  //@tailrec final def matches(v: Vector[String], checkBox: Boolean = true):
 def matches(v: Vector[String], checkBox: Boolean = true): Boolean = {
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

  /** True if text content includes all whitespace-delimited
  * tokens in a given list.
  * Recursively compares each string in the list, and sets a
  * flag to false if there is no match.
  *
  * @param v List of string to test for.
  * @param checkBox True if all strings seen so far have matched.
  */
//  @tailrec final def tokenMatches(v: Vector[String], checkBox: Boolean =
  def tokenMatches(v: Vector[String], checkBox: Boolean =
       true, omitPunctuation: Boolean = true): Boolean = {
    if (v.isEmpty) {
      checkBox
    } else {
      if (tokenMatches(v(0))) {
        tokenMatches(v.drop(1), checkBox)
      } else {
        tokenMatches(v.drop(1),false)
      }
    }
  }

  /** True if text content includes a given whitespace-delimited
  * token.  Optionally, ignore punctuation.
  *
  * @param t Token to test for.
  * @param omitPunctuation True if punctuation should be ignored.
  */
  //def tokenMatches(t: String, omitPunctuation: Boolean = true): Boolean = {
  def tokenMatches(t: String): Boolean = {
  /*  if (omitPunctuation) {
      true
    } else { */
      val tokens = this.text.split(" ")
      (tokens.contains(t))
  //  }
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

  /** Strip all punctuation characters from text content.
  */
  def stripPunctuation: String = {
    punctuationListRE.replaceAllIn(this.text, "")
  }

  /** Two-column serialization of this node as formated for
  * CEX serialization.
  *
  * @param delimiter String value to separate two columns.
  */
  def cex(delimiter: String = "\t"): String = {
    s"""${urn}${delimiter}${text}"""
  }

}

/** Factory for citable nodes with punctuation stripped out.
*/
object CitableNode {

  /** Create a new citable with all punctuation characters
  * removed from text member.
  */
  def stripPunctuation(cn: CitableNode): CitableNode = {
    CitableNode(cn.urn,cn.stripPunctuation)
  }
}
