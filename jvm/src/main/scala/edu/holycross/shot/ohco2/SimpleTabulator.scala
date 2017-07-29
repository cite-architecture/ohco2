package edu.holycross.shot.ohco2

import scala.collection.mutable.ArrayBuffer

import edu.holycross.shot.cite._

import scala.xml._
import scala.annotation.tailrec

import scala.scalajs.js


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

    val citableNodes = collectCitableNodes(docUrn, xml, 0, xpTemplate)
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


  def collectCitableNodes(urn: CtsUrn, n: Node, level: Int, xpt: XPathTemplate, citable: Vector[CitableNode] = Vector.empty[CitableNode]): Vector[CitableNode] = {
    val u = updateUrn(urn: CtsUrn, n: Node, level: Int, xpt: XPathTemplate)
    var buff = scala.collection.mutable.ArrayBuffer[CitableNode]()
    buff ++= citable
    val newLevel = level + 1

    if (newLevel == xpt.elVector.size) {
      val cn = CitableNode(u, n.toString.replaceAll("\n", " ").replaceAll("[ ]+", " "))
      buff.append( cn)

    } else {
      val nextTier = xpt.localNames(newLevel)
      val seq = n \ nextTier
      for (nd <- seq) yield {
        //val toAppend = walkXmlTree(u, nd, newLevel, xpt, citable)
        buff = buff ++ collectCitableNodes(u, nd, newLevel, xpt, citable)

        buff
      }
    }
    buff.toVector
  }


}
