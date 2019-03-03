package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class CorpusSpec extends FlatSpec {

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



  "A corpus of citable nodes"  should "offer a constructor signature for  a corpus from a string value for a URL identifying a 2-column delimited text source" in  {
    val corpus = Corpus(delimitedText,"#")
    corpus match {
      case c: Corpus => assert(true)
      case _ => fail("Failed to create Corpus object")
    }
  }

  it should "Give a clear error when attempting to build a corpus with duplicate IDs" in {

    val dups = """urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.1#μῆνιν
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.2#ἄειδε
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.2#θεὰ
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.4#Πηληϊάδεω
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.5#Ἀχιλῆος
"""
    try {
      val corpus = Corpus(dups,"#")
      fail("should not have build")
    } catch {
      case e:Exception => {
        assert(e.toString == "java.lang.IllegalArgumentException: requirement failed: Duplicated URN values: urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.2")
      }
    }
  }

  it should "Give a clear error when attempting to build a corpus with range-URNs identifying leaf nodes." in {
    try {
      val badCitations =  """urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.3#θεὰ
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.4-1.1.5#Πηληϊάδεω
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.6#Ἀχιλῆος
"""
      val corpus = Corpus(badCitations,"#")
      fail("should not have build")
    } catch {
      case e:Exception => {
        assert(e.toString == "edu.holycross.shot.ohco2.Ohco2Exception: Invald URN in input. urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.4-1.1.5. Range-URNs are not allowed to identify leaf nodes.")
      }
    }
  }

 it should "Give a clear error when failing to find two components in a line" in {

    val failLine = """urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.1#μῆνιν
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.2ἄειδε
urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.3#θεὰ
"""
    try {
      val corpus = Corpus(failLine,"#")
      fail("should not have build")
    } catch {
      case e:Exception => {
        assert(e.toString == "edu.holycross.shot.ohco2.Ohco2Exception: Badly formatted input.  Did not find 2 columns in the following source: urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.2ἄειδε")
      }
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
  it should "have a size function giving number of nodes" in {
    val corpus = Corpus(delimitedText,"#")
    assert (corpus.size == 3)
  }

  it should "allow comparing of corpus equality" in {
    val allFive = Corpus(fiveTokens,"#")

    val c1 = Corpus(delimitedText,"#")
    val c2 = Corpus(text2,"#")
    val combo = c1 ++ c2

    assert(allFive == combo)
  }

  it should "support addition of corpora" in {
    val c1 = Corpus(delimitedText,"#")
    val c2 = Corpus(text2,"#")
    val combo = c1 ++ c2
    assert (combo.size == 5)
  }



  it should "maintain corpus sequence in addition of corpora" in {
    val allFive = Corpus(fiveTokens,"#")

    val c1 = Corpus(delimitedText,"#")
    val c2 = Corpus(text2,"#")
    val combo = c2 ++ c1

    assert(allFive != combo)
  }

  it should "allow subtraction on corpora" in {
    val allFive = Corpus(fiveTokens,"#")
    val threeLines = Corpus(delimitedText,"#")
    assert( (allFive -- threeLines).size == 2)
  }

  it should "maintain non-commutative property subtraction" in {
    val allFive = Corpus(fiveTokens,"#")
    val threeLines = Corpus(delimitedText,"#")
    assert( (threeLines -- allFive).size == 0)
  }

  it should "support twiddling on vectors of URNs" in {
    val five = Corpus(fiveTokens,"#")
    val one = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.1")
    val two = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.2")
    val three = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.3")
    val urnVector = Vector(one,two,three)
    val resultCorpus = Corpus(Vector.empty)
    val anded = five.~~( urnVector, resultCorpus)
    assert (anded.size == 3)
  }

  it should "support twiddling on a vector of URNs" in {
    val five = Corpus(fiveTokens,"#")
    val one = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.1")
    val two = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.2")
    val three = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.3")
    val urnVector = Vector(one,two,three)
    val anded = five ~~ urnVector
    assert (anded.size == 3)
  }

  it should "support twiddling on a vector of URNs when some might be ranges" in {
    val five = Corpus(fiveTokens,"#")
    val one = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.1-1.1.2")
    val three = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.3")
    val urnVector = Vector(one,three)
    val anded = five ~~ urnVector
    assert (anded.size == 3)
  }

  it should "offer a function for retrieving URNs" in {
    val threeLines = Corpus(delimitedText,"#")
    val one = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.1")
    val two = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.2")
    val three = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.3")
    val expected = Vector(one,two,three)


    assert (threeLines.urns == expected)
  }

  it should "offer a function for retrieving text contents" in {
    val threeLines = Corpus(delimitedText,"#")
    val expected = Vector("μῆνιν","ἄειδε","θεὰ")


    assert (threeLines.contents == expected)
  }


}
