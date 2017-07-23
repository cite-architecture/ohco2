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
}

object OnlineDocument {

  /** Find [[DocumentFormat]] value for a String name.
  *
  * @param value String name of format.
  */
  def formatForString(value: String): Option[DocumentFormat] = {
    val dfVect: Vector[DocumentFormat] = Vector(Wf_Xml, Two_Column, Markdown, Oxf)
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


  /** True if combination of optional parts of [[OnlineDocument]]
  * are appropriate for a given [[DocumentFormat]].
  */
  def validConfiguration(format: DocumentFormat, nsMap: Option[Map[String, String]], xpTemplate: Option[String]): Boolean = {

    val hasNamespaces = nsMap match {
      case m: Some[Map[String,String]] => true
      case None => false
    }
    val hasXPTemplate = xpTemplate match{
        case s: Some[String] => true
        case None => false
    }

    format match {
      case Wf_Xml => if (hasXPTemplate) { true } else { false}
      case Two_Column => if (hasXPTemplate || hasNamespaces) { false } else {true}
      case  Markdown => false // not currently implemented
      case Oxf => if (hasXPTemplate || hasNamespaces) { false } else {true}
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
    if (columns.size != 5) {
      throw Ohco2Exception(s"OnlineDocument: did not find 5 columns of input in ${delimitedText}")
    } else {}

    val urn = CtsUrn(columns(0))
    val format = formatForString(columns(1))
    val formatOk = format match {
      case f: Some[DocumentFormat] => true
      case None => false
    }

    if (formatOk) {
      val docName = columns(2)
      val namespaces = namespacesFromString(columns(3), delimiter2)
      val xpTemplate = if (columns(4).isEmpty) { None } else { Some(columns(4))}

      val settingsOk = validConfiguration(format.get,namespaces,xpTemplate)

      OnlineDocument(urn,format.get,docName,namespaces,xpTemplate )

    } else {
      throw Ohco2Exception(s"Bad value for format: ${columns(1)}")
    }

  }
}
