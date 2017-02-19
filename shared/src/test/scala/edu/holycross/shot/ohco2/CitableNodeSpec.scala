package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class CitableNodeSpec extends FlatSpec {

  "A citable text node"  should "have an identifying URN" in {
    val cn = CitableNode(CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1"),"Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος")
    assert (cn.urn  == CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1"))
  }

  it  should "have non-empty text content" in {
        val cn = CitableNode(CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1"),"Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος")
        assert (cn.text.nonEmpty)
  }


  it should "consider nodes with identical urns and text content to be equivalent" in {
    val cn1 = CitableNode(CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1"),"Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος")
    val cn2 = CitableNode(CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1"),"Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος")

    assert(cn1 == cn2)
  }

  "The constructor for a citable text node" should "throw an Ohco2 exception if text content is empty" in {
    try {
      CitableNode(CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1"), "")
    } catch {
      case oe: Ohco2Exception => assert(oe.message == "CitableNode: text content cannot be empty")
    }
  }


}
