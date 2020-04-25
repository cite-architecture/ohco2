package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class CorpusVectorObjectSpec extends FlatSpec {

  val delimitedText = """urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.1#μῆνιν
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.2#ἄειδε
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.3#θεὰ
"""
  val text2 =  """urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.3#θεὰ
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.4#Πηληϊάδεω
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.5#Ἀχιλῆος
"""

val fiveTokens = """urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.1#μῆνιν
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.2#ἄειδε
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.3#θεὰ
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.4#Πηληϊάδεω
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.5#Ἀχιλῆος
"""



  "The CorpusVector object"  should "give a clear error when attempting to build a corpus with duplicate IDs" in pending /* {

    val dups = """urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.1#μῆνιν
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.2#ἄειδε
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.2#θεὰ
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.4#Πηληϊάδεω
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.5#Ἀχιλῆος
"""
    try {
      val corpus = CorpusVector(dups,"#")
      fail("should not have build")
    } catch {
      case e:Exception => {
        assert(e.toString == "java.lang.IllegalArgumentException: requirement failed: Duplicated URN values: urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.2")
      }
    }
  }*/

  it should "give a clear error when attempting to build a corpus with range-URNs identifying leaf nodes" in pending /* {
    try {
      val badCitations =  """urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.3#θεὰ
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.4-1.1.5#Πηληϊάδεω
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.6#Ἀχιλῆος
"""
      val corpus = CorpusVector(badCitations,"#")
      fail("should not have build")
    } catch {
      case e:Exception => {
        assert(e.toString == "edu.holycross.shot.ohco2.Ohco2Exception: Invald URN in input. urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.4-1.1.5. Range-URNs are not allowed to identify leaf nodes.")
      }
    }
  }*/

 it should "give a clear error when failing to find two components in a line" in pending /*{

    val failLine = """urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.1#μῆνιν
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.2ἄειδε
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.3#θεὰ
"""
    try {
      val corpus = CorpusVector(failLine,"#")
      fail("should not have build")
    } catch {
      case e:Exception => {
        assert(e.toString == "edu.holycross.shot.ohco2.Ohco2Exception: Badly formatted input.  Did not find 2 columns in the following source: urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.2ἄειδε")
      }
    }
  }
*/

  it should "throw an Ohco2Exception if badly formatted data is given to the constructor" in pending /*{
    val badInput = "no structure here"
    try {
      CorpusVector(badInput,"#")
      fail("Should not have reached this point")
    } catch {
      case oe: Ohco2Exception => assert(oe.message == "Badly formatted input.  Did not find 2 columns in the following source: no structure here")
      case otherEx : Throwable => fail("Did not match " + otherEx)
    }
  }*/

  it should "have a non-empty vector of citable nodes" in pending /*{
    val corpus = CorpusVector(delimitedText,"#")
    assert(corpus.nodes.size > 0)
  }*/




}
