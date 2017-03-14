package edu.holycross.shot.ohco2
import edu.holycross.shot.cite._
import scala.scalajs.js
import js.annotation.JSExport


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


  def firstNCharsKwic(s: String, n: Int): String = {
    if (n >= s.size) {
      s
    } else {
      s.slice(0, n) + "..."
    }
  }

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
