package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class CorpusValidReffSpec extends FlatSpec {

val textDelimited = "urn:cts:ns:tg.w.v1:1.1#Version_1:1.1\n" +
  "urn:cts:ns:tg.w.v1:1.2#Version_1:1.2\n" +
  "urn:cts:ns:tg.w.v1:1.3#Version_1:1.3\n" +
  "urn:cts:ns:tg.w.v1:2.1#Version_1:2.1\n" +
  "urn:cts:ns:tg.w.v1:2.2#Version_1:2.2\n" +
  "urn:cts:ns:tg.w.v1:2.3#Version_1:2.3\n" +
  "urn:cts:ns:tg.w.v2:1.1#Version_2:1.1\n" +
  "urn:cts:ns:tg.w.v2:1.2#Version_2:1.2\n" +
  "urn:cts:ns:tg.w.v2:1.3#Version_2:1.3\n" +
  "urn:cts:ns:tg.w.v2:2.1#Version_2:2.1\n" +
  "urn:cts:ns:tg.w.v2:2.2#Version_2:2.2\n" +
  "urn:cts:ns:tg.w.v2:2.3#Version_2:2.3\n" +
  "urn:cts:ns:tg.w.v1.ex1:1.1.1#Version_1_exemp_1:1.1.1\n" +
  "urn:cts:ns:tg.w.v1.ex1:1.1.2#Version_1_exemp_1:1.1.2\n" +
  "urn:cts:ns:tg.w.v1.ex1:1.1.3#Version_1_exemp_1:1.1.3\n" +
  "urn:cts:ns:tg.w.v1.ex1:1.2.1#Version_1_exemp_1:1.2.1\n" +
  "urn:cts:ns:tg.w.v1.ex1:1.2.2#Version_1_exemp_1:1.2.2\n" +
  "urn:cts:ns:tg.w.v1.ex1:1.2.3#Version_1_exemp_1:1.2.3\n" +
  "urn:cts:ns:tg.w.v1.ex1:1.3.1#Version_1_exemp_1:1.3.1\n" +
  "urn:cts:ns:tg.w.v1.ex1:1.3.2#Version_1_exemp_1:1.3.2\n" +
  "urn:cts:ns:tg.w.v1.ex1:1.3.3#Version_1_exemp_1:1.3.3\n" +
  "urn:cts:ns:tg.w.v1.ex1:2.1.1#Version_1_exemp_1:2.1.1\n" +
  "urn:cts:ns:tg.w.v1.ex1:2.1.2#Version_1_exemp_1:2.1.2\n" +
  "urn:cts:ns:tg.w.v1.ex1:2.1.3#Version_1_exemp_1:2.1.3\n" +
  "urn:cts:ns:tg.w.v1.ex1:2.2.1#Version_1_exemp_1:2.2.1\n" +
  "urn:cts:ns:tg.w.v1.ex1:2.2.2#Version_1_exemp_1:2.2.2\n" +
  "urn:cts:ns:tg.w.v1.ex1:2.2.3#Version_1_exemp_1:2.2.3\n" +
  "urn:cts:ns:tg.w.v1.ex1:2.3.1#Version_1_exemp_1:2.3.1\n" +
  "urn:cts:ns:tg.w.v1.ex1:2.3.2#Version_1_exemp_1:2.3.2\n" +
  "urn:cts:ns:tg.w.v1.ex1:2.3.3#Version_1_exemp_1:2.3.3\n" +
  "urn:cts:ns:tg.w.v1.ex2:1.1.1#Version_1_exemp_2:1.1.1\n" +
  "urn:cts:ns:tg.w.v1.ex2:1.1.2#Version_1_exemp_2:1.1.2\n" +
  "urn:cts:ns:tg.w.v1.ex2:1.1.3#Version_1_exemp_2:1.1.3\n" +
  "urn:cts:ns:tg.w.v1.ex2:1.2.1#Version_1_exemp_2:1.2.1\n" +
  "urn:cts:ns:tg.w.v1.ex2:1.2.2#Version_1_exemp_2:1.2.2\n" +
  "urn:cts:ns:tg.w.v1.ex2:1.2.3#Version_1_exemp_2:1.2.3\n" +
  "urn:cts:ns:tg.w.v1.ex2:1.3.1#Version_1_exemp_2:1.3.1\n" +
  "urn:cts:ns:tg.w.v1.ex2:1.3.2#Version_1_exemp_2:1.3.2\n" +
  "urn:cts:ns:tg.w.v1.ex2:1.3.3#Version_1_exemp_2:1.3.3\n" +
  "urn:cts:ns:tg.w.v1.ex2:2.1.1#Version_1_exemp_2:2.1.1\n" +
  "urn:cts:ns:tg.w.v1.ex2:2.1.2#Version_1_exemp_2:2.1.2\n" +
  "urn:cts:ns:tg.w.v1.ex2:2.1.3#Version_1_exemp_2:2.1.3\n" +
  "urn:cts:ns:tg.w.v1.ex2:2.2.1#Version_1_exemp_2:2.2.1\n" +
  "urn:cts:ns:tg.w.v1.ex2:2.2.2#Version_1_exemp_2:2.2.2\n" +
  "urn:cts:ns:tg.w.v1.ex2:2.2.3#Version_1_exemp_2:2.2.3\n" +
  "urn:cts:ns:tg.w.v1.ex2:2.3.1#Version_1_exemp_2:2.3.1\n" +
  "urn:cts:ns:tg.w.v1.ex2:2.3.2#Version_1_exemp_2:2.3.2\n" +
  "urn:cts:ns:tg.w.v1.ex2:2.3.3#Version_1_exemp_2:2.3.3"
  val corpus = Corpus(textDelimited,"#")

  "A Corpus"  should  "offer a method reporting valid references" in {
    val passages:CtsUrn = CtsUrn("urn:cts:ns:tg.w.v1:1.1-1.3")
    val sorted:Vector[CtsUrn] = Vector(
      CtsUrn("urn:cts:ns:tg.w.v1:1.1"),
      CtsUrn("urn:cts:ns:tg.w.v1:1.2"),
      CtsUrn("urn:cts:ns:tg.w.v1:1.3"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.1"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.2"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.3"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.1"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.2"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.3"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.3.1"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.3.2"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.3.3"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.1.1"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.1.2"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.1.3"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.2.1"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.2.2"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.2.3"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.3.1"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.3.2"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.3.3")
    )    
    assert(corpus.validReff(passages) == sorted)
  }

  it  should  "correctly report validReff for exemplar-level URNs" in {
    val passages:CtsUrn = CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.1-1.2.3")
    val sorted:Vector[CtsUrn] = Vector(
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.1"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.2"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.3")
    )
    assert(corpus.validReff(passages) == sorted)
  }


  it  should  "correctly report validReff for notional works URNs" in {
    val passages:CtsUrn = CtsUrn("urn:cts:ns:tg.w:1.2")
    val sorted:Vector[CtsUrn] = Vector(
      CtsUrn("urn:cts:ns:tg.w.v1:1.2"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.1"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.2"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.3"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.2.1"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.2.2"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.2.3"), 
      CtsUrn("urn:cts:ns:tg.w.v2:1.2")
    )
    assert(corpus.validReff(passages).toSet == sorted.toSet)
  }
  
 it  should  "correctly report validReff for notional works range URNs" in {
    val passages:CtsUrn = CtsUrn("urn:cts:ns:tg.w:1.1-1.2")
    val sorted:Vector[CtsUrn] = Vector(
      CtsUrn("urn:cts:ns:tg.w.v1:1.1"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.1"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.2"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.3"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.1.1"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.1.2"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.1.3"), 
      CtsUrn("urn:cts:ns:tg.w.v2:1.1"),
      CtsUrn("urn:cts:ns:tg.w.v1:1.2"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.1"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.2"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.3"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.2.1"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.2.2"), 
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.2.3"), 
      CtsUrn("urn:cts:ns:tg.w.v2:1.2")
    )
    assert(corpus.validReff(passages).toSet == sorted.toSet)
  }


  it should "correctly report validReff on ranges of containing elements" in {
      val crUrn:CtsUrn = CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1-1.2")    
      assert(corpus.validReff(crUrn).size == 6)
  }

  it  should  "correctly report validReff for notional works" in {
    val passages:CtsUrn = CtsUrn("urn:cts:ns:tg.w:")
    val totalNum:Int = textDelimited.lines.size
    assert(corpus.validReff(passages).size == totalNum)
  } 



}
