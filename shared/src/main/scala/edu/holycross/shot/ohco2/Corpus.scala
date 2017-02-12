package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.io.Source
import java.io._
import java.net.URL

case class Corpus (nodes: Vector[CitableNode]) {
  def urnMatch(filterUrn: CtsUrn) : Corpus = {
    filterUrn.isRange match {
      // range filter:
      case true => {
        val u1 = CtsUrn(filterUrn.dropPassage.toString + filterUrn.rangeBeginRef)
        val u2 = CtsUrn(filterUrn.dropPassage.toString + filterUrn.rangeEndRef)
        try {
          val idx1 = nodes.indexOf(getFirstNode(u1))
          val idx2 = nodes.indexOf(getLastNode(u2)) + 1
          Corpus(nodes.slice(idx1,idx2))
        } catch {
          case oe: Ohco2Exception => Corpus(Vector.empty[CitableNode])
        }
      }
      //node filter:
      case false =>  {
       Corpus(nodes.filter(_.urn.urnMatch(filterUrn)))
     }
    }
  }

  def ~~(filterUrn: CtsUrn) : Corpus = {
    urnMatch(filterUrn)
  }


  def getValidReff(filterUrn: CtsUrn): Vector[CtsUrn]
 = {
    urnMatch(filterUrn).nodes.map(_.urn)
  }
  def getTextContents(filterUrn: CtsUrn, connector: String = "\n"): String = {
    urnMatch(filterUrn).nodes.map(_.text).mkString(connector)
  }
  def getFirstNodeOption(filterUrn: CtsUrn): Option[CitableNode] = {
    val matching = urnMatch(filterUrn)
    matching.nodes.isEmpty match {
      case true => None
      case false => Some(matching.nodes.head)
    }
  }
  def getFirstNode(filterUrn: CtsUrn): CitableNode = {
    getFirstNodeOption(filterUrn) match {
      case None => throw Ohco2Exception("No node matching " + filterUrn)
      case n: Some[CitableNode] => n.get
    }
  }

  def getLastNodeOption(filterUrn: CtsUrn): Option[CitableNode] = {
    val matching = urnMatch(filterUrn)
    matching.nodes.isEmpty match {
      case true => None
      case false => Some(matching.nodes.last)
    }
  }

  def getLastNode(filterUrn: CtsUrn): CitableNode = {
    getLastNodeOption(filterUrn) match {
      case None => throw Ohco2Exception("No node matching " + filterUrn)
      case n: Some[CitableNode] => n.get
    }
  }
  def citedWorks: Vector[CtsUrn] = {
    nodes.map(_.urn.dropPassage).distinct
  }


  def to82xfVector: Vector[XfColumns] = {
    val ids = nodes.map ( n => n.urn)
    val templateVector = Vector.empty[String]
    val nextColumn = templateVector  ++ ids.drop(1) ++ Vector("")
    val prevColumn = templateVector ++ Vector("") ++ ids.dropRight(1)
    val nextPrev = nextColumn.zip(prevColumn)
    nodes.zip(nextPrev).map {
      case (n,(nxt,prv)) => XfColumns(n.urn.toString,nxt.toString,prv.toString,n.text)
    }
  }
  def to82xfString(delimiter: String): String = {
    to82xfVector.map(_.rowString(delimiter)).mkString("\n")
  }
  def to2colString(delimiter: String): String = {
    nodes.map(cn => cn.urn + delimiter + cn.text).mkString("\n")
  }
}


object Corpus {
  def apply(data: String, separator: String = "\t"): Corpus = {
    val stringPairs = data.split("\n").toVector.map(_.split(separator))
    val citableNodes = stringPairs.map( arr => CitableNode(CtsUrn(arr(0)), arr(1)))
    Corpus(citableNodes)
  }

}


case class XfColumns(urn: String, nxt: String, prv: String, txt: String) {

  def rowString(delimiter: String ): String = {
    urn + delimiter + nxt + delimiter + prv + delimiter + txt
  }
}
