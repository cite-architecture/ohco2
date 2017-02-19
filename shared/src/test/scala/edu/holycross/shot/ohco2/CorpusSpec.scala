package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class CorpusSpec extends FlatSpec {

  val delimitedText = """urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.1#μῆνιν
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.2#ἄειδε
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.1#θεά
"""


  "A corpus of citable nodes"  should "offer a constructor signature for  a corpus from a string value for a URL identifying a 2-column delimited text source" in {
    val corpus = Corpus(delimitedText,"#")
    corpus match {
      case c: Corpus => assert(true)
      case _ => fail("Failed to create Corpus object")
    }
  }
  it should "throw an Ohco2Exception if badly formatted data is given to the constructor" in {
    val badInput = "no structure here"
    try {
      Corpus(badInput,"#")
      fail("Should not have reached this point")
    } catch {
      case oe: Ohco2Exception => assert(oe.message == "Badly formatted input.  Did not find 2 columns in the following source: no structure here")
      case otherEx : Throwable => fail("Did not match " + otherEx)
    }
  }

  it should "have a non-empty vector of citable nodes" in {
    val corpus = Corpus(delimitedText,"#")
    assert(corpus.nodes.size > 0)
  }




}
