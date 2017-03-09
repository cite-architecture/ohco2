package edu.holycross.shot.ohco2
import edu.holycross.shot.cite._
import scala.scalajs.js
import js.annotation.JSExport


@JSExport  case class StringCount (s: String, count: Int)

@JSExport  case class StringHistogram (histogram: Vector[StringCount]) 
