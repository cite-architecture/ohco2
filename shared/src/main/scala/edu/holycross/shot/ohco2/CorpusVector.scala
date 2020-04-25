package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.annotation.tailrec

import scala.collection.mutable.Map

import scala.scalajs.js
import scala.scalajs.js.annotation._


import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter


/** A corpus of citable texts.
*
* @constructor Create a new corpus with a vector of CitableNode objects.
* @param nodes Contents of the citable corpus
*/
@JSExportAll case class CorpusVector (nodes: Vector[CitableNode]) extends Corpus with LogSupport {

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
}
