package edu.holycross.shot.ohco2
import edu.holycross.shot.cite._

/** Association of a CtsUrn with labelling information
* from a text catalog.
*
* @param urn The CtsUrn.
* @param label A label for it.
*/
case class LabelledCtsUrn(urn: CtsUrn, label: String) {}
