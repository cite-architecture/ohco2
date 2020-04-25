package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._

import scala.scalajs.js
import scala.scalajs.js.annotation._
import scala.annotation.tailrec

import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter


/** A corpus of citable texts.
*
* @constructor Create a new corpus with a vector of CitableNode objects.
* @param nodes Contents of the citable corpus
*/
@JSExportAll case class CorpusVector (nodeList: Vector[CitableNode]) extends Corpus[CorpusVector] with LogSupport {

/*
  def apply(nodeList: Vector[CitableNode]): CorpusVector = {
    new CorpusVector(nodeList)
  }
*/

  def nodes: Vector[CitableNode] = nodeList

  /** Create a new corpus comprising nodes contained by a given URN.
  *
  * @param u A CtsUrn at either version or exemplar level.

  def containedNodes(u: CtsUrn): CorpusVector  = {
    require(u.concrete, "Can only compute contained nodes for a concrete instance of a text: " + u)
    val trimmed = u.dropPassage
    //debug("\n\nLimit containment to " + trimmed)
    //debug("From corpus \n" + urns.mkString("\n") )
    val matchingWork = nodes.filter(_.urn.dropPassage == trimmed)
    //debug("Found " + matchingWork.size + " nodes")

    if (u.isRange) {
      // Allow for possibility that range begin/end references are
      // either containers or nodes
      //debug("CONTAINMENT ON RANGE: " + u)
      val urnA = CtsUrn(u.dropPassage.toString + u.rangeBeginRef)
      val urnB = CtsUrn(u.dropPassage.toString + u.rangeEndRef)

      val aContained = containedNodes(urnA)
      val bContained = containedNodes(urnB)
      if (aContained.size < 1 || bContained.size < 1) {
        CorpusVector(Vector.empty)
      } else {
        val firstRef = aContained.nodes.head.urn.passageNodeRef
        val lastRef = bContained.nodes.last.urn.passageNodeRef

        val extentUrn = CtsUrn(u.dropPassage.toString + firstRef + "-" + lastRef)
        rangeExtract(extentUrn)
      }

    } else {
      // single node or containing reference:
      val containedCorpus = CorpusVector(matchingWork.filter(u >= _.urn ))
      //debug("Filtering using " + u + ": " + containedCorpus.size)
      containedCorpus
    }
  }*/


  /** Create a new corpus by adding a second corpus to this one.
  *
  * @param corpus2 second corpus with contents to be added.
  */
  def ++(corpus2: CorpusVector): CorpusVector  = {
    val newNodes = nodes ++ corpus2.nodes
    CorpusVector(newNodes.distinct)
  }

  /** Create a new corpus by subtracting a second corpus from this one.
  *
  * @param corpus2 second corpus with contents to be removed from this one.
  */
  def --(corpus2: CorpusVector) : CorpusVector = {
    CorpusVector( nodes diff corpus2.nodes)
  }

}
