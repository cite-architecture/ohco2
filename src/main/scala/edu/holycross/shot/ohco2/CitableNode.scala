package edu.holycross.shot.ohco2
import edu.holycross.shot.cite._

case class CitableNode (urn: CtsUrn, text: String) {
  if (text.isEmpty) {
    throw new Exception("CitableNode: text cannot be empty")
  }
}
