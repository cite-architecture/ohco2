package edu.holycross.shot.ohco2

import scala.scalajs.js
import edu.holycross.shot.cite._

object Main  {
  def main(args: Array[String]): Unit = {
    val cn = CitableNode(CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1"),"Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος")

    println("Citable node:\n\t" + cn.urn)
    println("has text:\n\t"+ cn.text)


  }
}
