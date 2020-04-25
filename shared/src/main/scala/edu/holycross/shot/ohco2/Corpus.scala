package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.annotation.tailrec

import scala.collection.mutable.Map

import scala.scalajs.js
import scala.scalajs.js.annotation._


import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter


trait Corpus[CImpl] extends LogSupport {

  def nodes: Vector[CitableNode]

  def ++ (corpus2: CImpl): CImpl
  def --(corpus2: CImpl) : CImpl
}
