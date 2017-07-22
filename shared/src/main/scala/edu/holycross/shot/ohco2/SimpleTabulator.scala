package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._

import scala.xml._
import scala.annotation.tailrec

import scala.scalajs.js
import js.annotation.JSExport


///  val cexSpec = "urn:cts:greekLit:tlg0016.tlg001.grcTest:#xml#test-hdt-grc.xml#tei->http://www.tei-c.org/ns/1.0#/tei:TEI/tei:text/tei:body/tei:div[@n = '?']/tei:div[@n = '?']"


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
  def apply(docUrn: CtsUrn, xpTemplate: XPathTemplate, xmlString: String ): Corpus = {

    val xml = scala.xml.XML.loadString(xmlString)
    //println("Doc: " + xml)
    /*val indices =  xpathTemplate.indexOfKeys


    val lvl = xml \ xpathTemplate.elVector(1)
    println("For " +  xpathTemplate.elVector(1) + ", got " + lvl)
    for (ch <- lvl) {
      println(ch)
    }
*/
    //walkXmlTree(xml, 0, xpTemplate )
    val citableNodes = Vector.empty[CitableNode]
    Corpus(citableNodes)
  }

  def updateUrn(urn: CtsUrn, n: Node, level: Int, xpt: XPathTemplate): CtsUrn = {
    if (xpt.isKey(level)) {
      val cit = n \ xpt.keyForIndex(level)
      urn.passageNodeOption match {
        case psg: Some[String] => CtsUrn(urn.toString + "." + cit)
        case _ => CtsUrn(urn.toString + cit)
      }
    } else {
      urn
    }
  }

  def walkXmlTree(urn: CtsUrn, n: Node, level: Int, xpt: XPathTemplate): Unit = {

    val u = updateUrn(urn: CtsUrn, n: Node, level: Int, xpt: XPathTemplate)
    //println(s"Start from level ${level}: el ${n.label}: urn ${u}" )

    val newLevel = level + 1
    if (newLevel == xpt.elVector.size) {
      val cn = CitableNode(u, n.toString)
      println ("MAKE CITABLE NODE: " + cn)
    } else {
      val nextTier = xpt.localNames(newLevel)
      val seq = n \ nextTier
      //println("Found " + seq.size + " matches.")

      for (nd <- seq) {
        walkXmlTree(u, nd, newLevel, xpt)
      }

    }
  }


}
