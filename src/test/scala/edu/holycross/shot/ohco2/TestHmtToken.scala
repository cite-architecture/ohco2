package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class CitableNodeSpec extends FlatSpec {

  "A citable text node"  should "have an identifying URN" in {
    val cn = CitableNode(CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1"),"Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος")
    assert (cn.urn  == CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1"))
  }

  "A citable text node"  should "have non-empty text content" in {
        val cn = CitableNode(CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1"),"Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος")
        assert (cn.text.nonEmpty)
  }


}
