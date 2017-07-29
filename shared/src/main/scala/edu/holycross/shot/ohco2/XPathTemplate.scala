package edu.holycross.shot.ohco2


import scala.scalajs.js
//import scala.scalajs.js.annotation._


import scala.annotation.tailrec

/** Class for working with XPathTemplates.
* An XPathTemplate is an XPath-like String identifying elements in
* an XML hierarchy. Elements may or may not be qualified by a prefix.
* Elements carrying a citation value on an attribute may have an
* attribute template expression of the form [@ATTRIBUTE = '?'].
*
*/
case class XPathTemplate(s: String) {


  /** Vector of XPath-like element expressions in s
  */
  def elVector = {
    s.split("/").toVector.filter(_.nonEmpty)
  }

  /** Find vector of element names for s,
  * including any qualifying XML namespace abbreviations.
  */
  def qNames: Vector[String] = {
    elVector.map(XPathTemplate.stripAttributes(_) )
  }

  /** Find vector of simple element names for s,
  * without XML namespace abbreviations.
  */
  def localNames: Vector[String] = {
    elVector.map( XPathTemplate.localName(_) )
  }

  /** Find name of attribute that used as the
  * citation key on an element in the XPathTemplate
  * specified by index number.
  *
  * @param i Index of element with a citation template.
  */
  def keyForIndex(i: Int): String = {
    val key = XPathTemplate.citationKeyOption(elVector(i))
    key match {
      case k: Some[String] => k.get
      case _ => throw new Exception(s"XPathTemplate ${s} does not have a citation template at position ${i}.")
    }
  }

  /** True if element expression at index is is a
  * citation template.
  *
  * @param i Index of element to check.
  */
  def isKey(i: Int): Boolean ={
    val key = XPathTemplate.citationKeyOption(elVector(i))
    key match {
      case k: Some[String] => true
      case _ => false
    }
  }




  /** List index values to elements including citation templates
  * by recursively examining each element of elVector.
  */
  def indexOfKeys: Vector[Int] = {
    indexOfKeys(Vector.empty[Int], 0)
  }

  /** List index values to elements including citation templates
  * by recursively examining each element of elVector.
  *
  * @param keys Vector of previously accumulated index values.
  * @param lastSeen Index value of element to examine.
  */
  @tailrec final def indexOfKeys(keys: Vector[Int], index : Int = 0) : Vector[Int] = {
    if (index == elVector.size) {
      keys
    } else {
      val key = XPathTemplate.citationKeyOption(elVector(index))
      key match {
        case k: Some[String] => indexOfKeys(keys :+ index, index + 1)
        case _ => indexOfKeys(keys, index + 1)
      }
    }
  }

}



object XPathTemplate  {

  /** Extract local name component from possibly
  * prefixed element name.
  *
  * @param s String expression
  */
  def localName(s: String): String = {
    val parts = s.split(":")
    if (parts.size > 0) {
      stripAttributes(s.replaceFirst(parts(0) + ":", ""))
    } else {
      stripAttributes(s.trim)
    }
  }


  /** Remove attribute template expression from element.
  * @param s Element expression.
  */
  def stripAttributes (s: String) : String = {
    val attrs = "([^\\[]+).*".r
    s match {
     case attrs(group) => {
       group
     }
     case _ => s
   }
  }

  /** True if s is a valid attribute template.
  */
  def validAttributeTemplate(s: String): Boolean = {
   val parts = s.split("=")
   if (parts.size != 2){
     false
   } else {
     if (parts(1).trim == "'?'") {
       true
     } else {
       false
     }
   }
  }


  /** Find string value of citation key, or
  * empty string.
  *
  * @param s An element expression possibly including attribute
  * in the form element[@attribute = '?'].
  */
  def citationKey(s: String): String = {
    citationKeyOption(s).getOrElse("")
  }

  /** Extract attribute used as citation key if present.
  *
  * @param s An element expression possibly including attribute
  * in the form element[@attribute = '?'].
  */
  def citationKeyOption(s: String): Option[String] = {
    val attrs = ".+\\[(.+)\\]".r
    val key = s match {
     case attrs(group) => {
       if (validAttributeTemplate(group)) {
         val parts = group.split("=")
         Some(stripAttributes(parts(0).trim))
       } else {
         throw new Exception(s"Citation element ${s} has bad attribute expression ${group}.  Should be formatted NAME = '?'")
       }
     }
    case _ => None
    }
    key
  }


}
