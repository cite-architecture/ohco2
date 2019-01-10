package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.util.Try

/** Metadata about a citable text in a local file.
*
* @param urn CtsUrn for the text.
* @param format OHCO2 format of the file.
* @param docName Relative or absolute path to the document.
* @param namespaces Map of prefix abbreviations to URIs for XML namespaces, or None for non-XML document formats.
* @param xpathTemplate XPath-like string defining mapping of citation scheme to XML markup, or None for non-XML document formats.
*/
case class OnlineDocument (urn: CtsUrn, format : DocumentFormat, docName: String, namespaces : Option[Map[String, String]] = None, xpathTemplate: Option[String] = None) {

  /** Create a new OnlineDocument with relative document name expanded
  * to an absolute path.
  *
  * @param directory Base directory to prefix to relative document name.
  */
  def absolutePath(directory: String): OnlineDocument = {
    OnlineDocument(urn, format, fileName(directory, docName), namespaces, xpathTemplate)
  }
}

object OnlineDocument {

  /** Find [[DocumentFormat]] value for a String name.
  *
  * @param value String name of format.
  */
  def formatForString(value: String): Option[DocumentFormat] = {
    val dfVect: Vector[DocumentFormat] = Vector(Wf_Xml, Two_Column, Markdown)
    dfVect.find(_.name == value)
  }


  /** Create optional namespace map from formatting string.
  *
  * @param s Possibly empty string defining namespace mappings.
  */
  def namespacesFromString(s: String, delimiter: String = ","): Option[Map[String, String]] = {
    if (s.isEmpty) {
      None

    } else {
      val lines = s.split("\n")
      val mapped = lines.map( s => s.split("->")).map(arr =>arr(0) -> arr(1)).toMap
      Some(mapped)
    }

  }

  /** Create an [[OnlineDocument]] from a single line of delimited-text data.
  *
  * @param delimitedText Five-column description of local file configuraiton.
  * @param delimiter Delimiting string separating columns.
  * @param delimiter2 Secondary delimiter separating entries in optional
  * XML namespace mappings.
  */
  def apply(delimitedText: String, delimiter: String, delimiter2: String): OnlineDocument = {

    val columns = delimitedText.split(delimiter)
    if (columns.size <  3 ) {
      throw Ohco2Exception(s"OnlineDocument: only found required ${columns}.size columns in ${delimitedText}")
    } else {}

    val urn = CtsUrn(columns(0))
    val format = formatForString(columns(1))
    val docName = columns(2)

    val formatOk = format match {
      case f: Some[DocumentFormat] => true
      case None => false
    }
    if (formatOk) {
      format.get match {
        case Wf_Xml => {
          require(columns.size == 5, "OnlineDocument: XML configuration must include XPathTemplate")
          val namespaces = namespacesFromString(columns(3), delimiter2)
          val xpTemplate = if (columns(4).isEmpty) { None } else { Some(columns(4))}
          OnlineDocument(urn,format.get,docName,namespaces,xpTemplate )
        }
        case _ => {
          require(columns.size == 3, s"OnlineDocument format ${format.get} may not include XPathTemplate or XML namespaces")
          OnlineDocument(urn,format.get,docName,None,None )
        }
      }

    } else {
      throw Ohco2Exception(s"Bad value for format: ${columns(1)}")
    }
  }
}
