package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class CorpusVectorSpec extends FlatSpec {

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



  "A CorpusVector"  should "do" in pending


  it should "have a non-empty vector of citable nodes" in pending /*{
    val corpus = CorpusVector(delimitedText,"#")
    assert(corpus.nodes.size > 0)
  }*/
  it should "have a size function giving number of nodes" in pending /*{
    val corpus = CorpusVector(delimitedText,"#")
    assert (corpus.size == 3)
  }*/

  it should "allow comparing of corpus equality" in pending /*{
    val allFive = CorpusVector(fiveTokens,"#")

    val c1 = CorpusVector(delimitedText,"#")
    val c2 = CorpusVector(text2,"#")
    val combo = c1 ++ c2

    assert(allFive == combo)
  }*/

  it should "support addition of corpora" in pending /*{
    val c1 = CorpusVector(delimitedText,"#")
    val c2 = CorpusVector(text2,"#")
    val combo = c1 ++ c2
    assert (combo.size == 5)
  }*/



  it should "maintain corpus sequence in addition of corpora" in pending /*{
    val allFive = CorpusVector(fiveTokens,"#")

    val c1 = CorpusVector(delimitedText,"#")
    val c2 = CorpusVector(text2,"#")
    val combo = c2 ++ c1

    assert(allFive != combo)
  }*/

  it should "allow subtraction on corpora" in pending /*{
    val allFive = CorpusVector(fiveTokens,"#")
    val threeLines = CorpusVector(delimitedText,"#")
    assert( (allFive -- threeLines).size == 2)
  }*/

  it should "maintain non-commutative property subtraction" in pending /* {
    val allFive = CorpusVector(fiveTokens,"#")
    val threeLines = CorpusVector(delimitedText,"#")
    assert( (threeLines -- allFive).size == 0)
  }*/

  it should "support twiddling on vectors of URNs" in pending /*{
    val five = CorpusVector(fiveTokens,"#")
    val one = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.1")
    val two = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.2")
    val three = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.3")
    val urnVector = Vector(one,two,three)
    val resultCorpus = CorpusVector(Vector.empty)
    val anded = five.~~( urnVector, resultCorpus)
    assert (anded.size == 3)
  }*/

  it should "support twiddling on a vector of URNs" in pending /*{
    val five = CorpusVector(fiveTokens,"#")
    val one = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.1")
    val two = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.2")
    val three = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.3")
    val urnVector = Vector(one,two,three)
    val anded = five ~~ urnVector
    assert (anded.size == 3)
  }*/

  it should "support twiddling on a vector of URNs when some might be ranges" in pending /*{
    val five = CorpusVector(fiveTokens,"#")
    val one = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.1-1.1.2")
    val three = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.3")
    val urnVector = Vector(one,three)
    val anded = five ~~ urnVector
    assert (anded.size == 3)
  } */

  it should "offer a function for retrieving URNs" in pending /*{
    val threeLines = CorpusVector(delimitedText,"#")
    val one = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.1")
    val two = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.2")
    val three = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA.tkns:1.1.3")
    val expected = Vector(one,two,three)


    assert (threeLines.urns == expected)
  } */

  it should "offer a function for retrieving text contents" in pending /*{
    val threeLines = CorpusVector(delimitedText,"#")
    val expected = Vector("μῆνιν","ἄειδε","θεὰ")


    assert (threeLines.contents == expected)
  }*/


}
