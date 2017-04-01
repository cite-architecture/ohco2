package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._

/** Set of recognized formats implementing
* OHCO2 model of citable texts.
*/
sealed trait DocumentFormat {
  def name: String
}

/** Well-formed XML with citation encoded as
* specified in citation configuration.
*/
case object Wf_Xml extends DocumentFormat {
  val name = "xml"
}

/** Markdown, adhering to OHCO2 requirements for
* hierarchical consistency.
*/
case object Markdown extends DocumentFormat {
  val name = "markdown"
}


/** Two-column format, with lines representing
* citable nodes in document order.
*/
case object Two_Column extends DocumentFormat {
  val name = "twocol"
}


/** 82XF format.
*/
case object Oxf extends DocumentFormat {
  val name = "82xf"
}

/** Metadata about a citable text in a local file.
*
* @param urn CtsUrn for the text.
* @param format OHCO2 format of the file.
* @param docName Relative or absolute path to the document.
*/
case class OnlineDocument (urn: CtsUrn, format : DocumentFormat, docName: String) {
}
