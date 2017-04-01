package edu.holycross.shot.ohco2


import edu.holycross.shot.cite._

sealed trait DocumentFormat {
  def name: String
}
case object Wf_Xml extends DocumentFormat {
  val name = "xml"
}
case object Markdown extends DocumentFormat {
  val name = "markdown"
}
case object Two_Column extends DocumentFormat {
  val name = "twocol"
}
case object Oxf extends DocumentFormat {
  val name = "82xf"
}

case class OnlineDocument (urn: CtsUrn, format : DocumentFormat, docName: String) {
}
