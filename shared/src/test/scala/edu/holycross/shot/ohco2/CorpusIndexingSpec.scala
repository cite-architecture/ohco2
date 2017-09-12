package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class CorpusIndexingSpec extends FlatSpec {

  "A RangeIndex" should "have two index values, a and b" in {
    val rIdx = RangeIndex(0,4)
    assert(rIdx.a == 0)
    assert(rIdx.b == 4)
  }




  val data = "urn:cts:test:g.w.v1:1.a#One, A, in version 1\n" +
  "urn:cts:test:g.w.v2.ex1:1.a#One, A, in version 2, exemplar 1\n" +
  "urn:cts:test:g.w.v2.ex1:1.b#One, B, in version 2, exemplar 1\n" +
  "urn:cts:test:g.w.v2.ex1:2.a#Two, A, in version 2, exemplar 1\n" +
  "urn:cts:test:g.w.v2.ex1:2.b#Two, B, in version 2, exemplar 1\n" +
  "urn:cts:test:g.w.v:1#One in version\n" +
  "urn:cts:test:g.w.v:2#Two in version\n" +
  "urn:cts:test:g.w.v:3#Three in version\n" +
  "urn:cts:test:g.w.v:4#Four in version\n"
  val corpus = Corpus(data,"#")

  "A corpus of citable nodes"  should "find the index of a single node" in {
    val idx = corpus.pointIndex(CtsUrn("urn:cts:test:g.w.v:1"))
    val expectedIndex = 5
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

  it should "throw an exception if the URN exists but is not a node" in {
    val containingUrn = CtsUrn("urn:cts:test:g.w.v2:1")
      try {
        val idx = corpus.pointIndex(containingUrn)
        fail("Should not have indexed bogus URN")
      } catch {
        case o2e: Ohco2Exception => assert(o2e.message == "Corpus.pointIndex: urn:cts:test:g.w.v2:1 does not refer to a node.")
        case  exc : Throwable => fail("Should have thrown an Ohco2Exception instead of " + exc.getMessage())
      }
  }


  it should "compute a RangeIndex for range expressions" in  {
    val range = CtsUrn("urn:cts:test:g.w.v:2-4")
    val rIdx = corpus.rangeIndex(range)
    val expectedIdx = RangeIndex(6,8)
    assert(rIdx == expectedIdx)
  }


  it should "list versions identified by a given URN" in {
    val notional = CtsUrn("urn:cts:test:g.w:")
    val expectedSet =  Set(
      CtsUrn("urn:cts:test:g.w.v1:"),
      CtsUrn("urn:cts:test:g.w.v:")
    )
    assert (expectedSet == corpus.versions(notional))
  }


  it should "identify exemplars identified by a given URN" in {
    val notional = CtsUrn("urn:cts:test:g.w:")

    val expectedExemplars = Set(CtsUrn("urn:cts:test:g.w.v2.ex1:"))
    assert(expectedExemplars == corpus.exemplars(notional))
  }





}
