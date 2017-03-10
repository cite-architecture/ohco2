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
}
