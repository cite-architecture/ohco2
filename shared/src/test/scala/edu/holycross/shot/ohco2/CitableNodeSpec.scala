package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class CitableNodeSpec extends FlatSpec {
  val hdtproem = CitableNode(CtsUrn("urn:cts:greekLit:tlg0016.tlg001.eng:1.0"),  "This is the Showing forth of the Inquiry of Herodotus of Halicarnassos, to the end that neither the deeds of men may be forgotten by lapse of time, nor the works great and marvellous, which have been produced some by Hellenes and some by Barbarians, may lose their renown; and especially that the causes may be remembered for which these waged war with one another.")


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

  it should "support URN twiddling" in {
      val cn = CitableNode(CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1"),"Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος")

      val iliad1 = CtsUrn("urn:cts:greekLit:tlg0012.tlg001:1")

      assert (cn ~~ iliad1)

  }

  it should "support string searching" in {
    assert (hdtproem.matches("Showing"))
  }

  it should "support searching for lists of searching" in {
    assert (hdtproem.matches(Vector("Showing", "Hellenes")))
    assert (hdtproem.matches(Vector("Thucydides", "Hellenes")) == false)
  }

  it should "support searching for white-space delimited tokens" in {
    assert (hdtproem.matches("Hellene"))
    assert (hdtproem.tokenMatches("Hellene") == false)
    assert (hdtproem.tokenMatches("Hellenes"))
  }


  it should "support searching for vectors of white-space delimited tokens" in pending/* {

    assert (hdtproem.tokenMatches(Vector("Hellenes", "Barbarians")) == false)
    assert (hdtproem.tokenMatches(Vector( "Hellenes,","Barbarians")))
  }*/

  it should "find coocurrences within a given distance" in {
    assert(hdtproem.tokensWithin(Vector("Showing","Inquiry"), 5))
    assert(hdtproem.tokensWithin(Vector("Showing","Herodotus"), 3) == false)
  }
  it should "report false when a token is absent" in {
    assert(hdtproem.tokensWithin(Vector("Showing","Thucydides"), 5) == false)
  }

  it should "be able to strip punctuation characters out of the text contents" in {
      val expectedText = "This is the Showing forth of the Inquiry of Herodotus of Halicarnassos to the end that neither the deeds of men may be forgotten by lapse of time nor the works great and marvellous which have been produced some by Hellenes and some by Barbarians may lose their renown and especially that the causes may be remembered for which these waged war with one another"

      assert(hdtproem.stripPunctuation == expectedText)
  }

  "The constructor for a citable text node" should "throw an Ohco2 exception if text content is empty" in {
    try {
      CitableNode(CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1"), "")
    } catch {
      case oe: Ohco2Exception => assert(oe.message == "CitableNode: text content cannot be empty")
    }
  }


  "The CitableNode object" should "be able to create a new citable node without punctuation characters from a given CitableNode" in {
    val stripped = CitableNode.stripPunctuation(hdtproem)
    val expectedText = "This is the Showing forth of the Inquiry of Herodotus of Halicarnassos to the end that neither the deeds of men may be forgotten by lapse of time nor the works great and marvellous which have been produced some by Hellenes and some by Barbarians may lose their renown and especially that the causes may be remembered for which these waged war with one another"
    assert(stripped.text == expectedText)
  }

}
