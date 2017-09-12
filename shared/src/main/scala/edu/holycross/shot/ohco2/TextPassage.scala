package edu.holycross.shot.ohco2


import scala.scalajs.js
import scala.scalajs.js.annotation._


/** Enumeration of possible  relations of two text passages. */
@JSExportAll object TextPassageTopology extends Enumeration {
  val PassageEquals, PassagePrecedes, PassageFollows, PassageContains, PassageContained, PassagePrecedesAndOverlaps, PassageOverlapsAndPrecededBy, PassageOverlapsAndFollows, PassageOverlapsAndFollowedBy  = Value
}
