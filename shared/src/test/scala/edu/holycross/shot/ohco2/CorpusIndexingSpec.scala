package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class CorpusIndexingSpec extends FlatSpec {

  val data = "urn:cts:test:g.w.v1:1.a#One, A, in version 1\n" +
  "urn:cts:test:g.w.v2:1.a#One, A, in version 2\n" +
  "urn:cts:test:g.w.v2:1.b#One, B, in version 2\n" +
  "urn:cts:test:g.w:1#One in notional work\n" +
  "urn:cts:test:g.w.v:1#One in version\n" +
  "urn:cts:test:g.w.v:2#Two in version\n"

  val corpus = Corpus(data,"#")


  "A corpus of citable nodes"  should "find the index of a single node" in {
    val idx = corpus.pointIndex(CtsUrn("urn:cts:test:g.w:1"))
    val expectedIndex = 3
    assert(idx == expectedIndex)
  }

  it should "throw an exception if the node does not exist" in {
    try {
      val idx = corpus.pointIndex(CtsUrn("urn:cts:test:g.w:NONEXISTENT"))
      fail("Should not have indexed bogus URN")
    } catch {
      case o2e: Ohco2Exception => assert(o2e.message == "Corpus.pointIndex: urn:cts:test:g.w:NONEXISTENT does not refer to a node.")
      case  exc : Throwable => fail("Should have thrown an Ohco2Exception instead of " + exc.getMessage())
    }
  }



}
