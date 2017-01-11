package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.io.Source

case class Corpus (texts: Vector[CitableNode]) {
  def urnMatch(filterUrn: CtsUrn) = {
    filterUrn.isRange match {
      case true => throw Ohco2Exception("Range matching not yet implemented")
      case false =>   texts.filter(_.urn.urnMatch(filterUrn))
    }
  }
  def getValidReff(filterUrn: CtsUrn): Vector[CtsUrn] = {
    urnMatch(filterUrn).map(_.urn)
  }
  def getTextContents(filterUrn: CtsUrn, connector: String = "\n"): String = {
    urnMatch(filterUrn).map(_.text).mkString(connector)
  }
  def getFirstNode(filterUrn: CtsUrn): CitableNode = {
    urnMatch(filterUrn).head
  }
  def getLastNode(filterUrn: CtsUrn): CitableNode = {
    urnMatch(filterUrn).tail(1)
  }
}


object Corpus {
  def apply(f: String, separator: String = "\t"): Corpus = {
    val stringPairs = Source.fromFile(f).getLines.toVector.map(_.split(separator))
    val citableNodes = stringPairs.map( arr => CitableNode(CtsUrn(arr(0)), arr(1)))
    Corpus(citableNodes)
  }
}
