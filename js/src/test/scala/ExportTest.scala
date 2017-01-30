package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import org.scalatest._

class ExportTest extends FlatSpec {

  "The ohco2 library"  should "expose methods of CitableNode" in {
    val cn = CitableNode(CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1"),"Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος")

    assert(cn.urn == CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1"))
    assert(cn.text == "Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος")
  }

}
