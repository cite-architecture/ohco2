package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._

import scala.scalajs.js
import scala.scalajs.js.annotation._
import scala.annotation.tailrec


import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter


/** Abstraction of a corpus of texts, type parameterized
* for its back-end implementation [[CImpl]]. */
trait Corpus[CImpl] extends LogSupport {


  /** The contents of any [[Corpus]] can be expressed as
  * a Vector of CitableNodes.
  */
  def nodes: Vector[CitableNode]

  /** Create a new corpus by adding a second corpus to this one.
  *
  * @param corpus2 second corpus with contents to be added.
  */
  def ++ (corpus2: CImpl): CImpl

  /** Create a new corpus by subtracting a second corpus from this one.
  *
  * @param corpus2 second corpus with contents to be removed from this one.
  */
  def -- (corpus2: CImpl) : CImpl




  //// Concrete method implementations
  //
  /** Number of citable nodes in the corpus.
  */
  def size: Int = {
    nodes.size
  }

  /** True if citable nodes vector is empty.
  */
  def isEmpty: Boolean = {
    nodes.isEmpty
  }

  /** List all versions or exemplars cited in a corpus.
  */
  def citedWorks: Vector[CtsUrn] = {
    nodes.map(_.urn.dropPassage).distinct
  }

  /** Project text contents of the corpus to a vector of Strings.
  */
  def contents : Vector[String] = {
    nodes.map(_.text)
  }
}
