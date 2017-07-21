package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._

import scala.xml._
import scala.annotation.tailrec

import scala.scalajs.js
import js.annotation.JSExport





/** Factory for building [[edu.holycross.shot.ohco2.Corpus]] instances
* from cataloged XML source files.
*/
object SimpleTabulator {

  /** Create a Corpus from a configured XML source.
  *
  * @param docUrn Version- or exemplar-level CtsUrn for this document.
  * @param spec CEX-format catalog specification for this document.
  * @param xmlString A String of XML.
  */
  def apply(docUrn: CtsUrn, spec: String, xmlString: String ): Corpus = {

    val xml = scala.xml.XML.loadString(xmlString)
    //println("Doc: " + xml)
    /*val indices =  xpathTemplate.indexOfKeys


    val lvl = xml \ xpathTemplate.elVector(1)
    println("For " +  xpathTemplate.elVector(1) + ", got " + lvl)
    for (ch <- lvl) {
      println(ch)
    }
*/
    val citableNodes = Vector.empty[CitableNode]
    Corpus(citableNodes)
  }


  //def citationTier() = {
  //}

}
