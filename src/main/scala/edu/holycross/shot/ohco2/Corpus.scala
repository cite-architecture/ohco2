package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._

case class Corpus (texts: Vector[CitableNode]) {
  def urnMatch(filterUrn: CtsUrn) = {
    filterUrn.isRange match {
      case true => throw Ohco2Exception("Range matching not yet implemented")
      case false =>   texts.filter(_.urn.urnMatch(filterUrn))
    }
  }
}
