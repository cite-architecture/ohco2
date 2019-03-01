package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class RangeAlgebraSpec extends FlatSpec {



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


  def splat(c: Corpus): Unit = {
    println(c.size)
    println(c.urns.mkString("\n"))
  }


  "A Corpus"  should  "index nodes in a range in a concrete version" in  {
    val range = CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.2-1.2.2")
    val rIdx = corpus.rangeIndex(range)
    val expectedStartIndex = 13
    val expectedEndIndex = 16
    assert(rIdx.a == expectedStartIndex)
    assert(rIdx.b == expectedEndIndex)
  }

  it should "throw an exception if asked to index a range in a notional version" in  {
    val range = CtsUrn("urn:cts:ns:tg.w:1.1.2-1.2.2")
    try {
      val rIdx = corpus.rangeIndex(range)
      fail("Should have thrown an exception making a range index on " + range)
    } catch {
      case iae: IllegalArgumentException => assert(iae.getMessage() == "requirement failed: Can only index references to concrete texts: urn:cts:ns:tg.w:1.1.2-1.2.2")
      case t: Throwable => fail("Should have thrown an Ocho2Exception: " +  t)
    }
  }

  it should "extract a range from a concrete version" in {
    val range = CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.2-1.2.2")
    val extracted = corpus.rangeExtract(range)
    val expected = Vector(
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.2"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.3"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.1"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.2")
    )
    assert(extracted.urns == expected)
  }

   it should "throw an exception if asked to index an invalid range" in  {
    val range = CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.2-1.1.4")
    try {
      val rIdx = corpus.rangeIndex(range)
      fail("Should have thrown an exception making a range index on " + range)
    } catch {
      case t: Throwable => assert(t.toString == "edu.holycross.shot.ohco2.Ohco2Exception: Failed to match both ends of range reference: urn:cts:ns:tg.w.v1.ex1:1.1.2-1.1.4")
    }
  }

  it should "be able to apply the containedNodes function to a concrete range" in {
    val range = CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.2-1.2.2")
    assert (corpus.rangeExtract(range) == corpus.containedNodes(range))
  }

  it should "index containers to a RangeIndex" in {
      val container = CtsUrn("urn:cts:ns:tg.w.v1.ex1:1")
      val rIdx = corpus.rangeIndex(container)
      val expectedStartIndex = 12
      val expectedEndIndex = 20

      assert(rIdx.a == expectedStartIndex)
      assert(rIdx.b == expectedEndIndex)
  }



  it should "be able to find concrete exemplars for ranges of notional works" in {
    //val range = CtsUrn("urn:cts:ns:tg.w:1.1.2-1.2.2")
    val range = CtsUrn("urn:cts:ns:tg.w:1.1.2")
    val expected = Set(
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:")
    )

    assert(expected == corpus.exemplars(range))
  }

  it should "be able to find concrete versions for ranges of notional works" in {
    val range = CtsUrn("urn:cts:ns:tg.w:1.1.2")
    val expected = Set(
      CtsUrn("urn:cts:ns:tg.w.v1:"),
      CtsUrn("urn:cts:ns:tg.w.v2:")
    )
    assert(expected == corpus.versions(range))
  }

  it should "throw an Ocho2Exception if a bad range is requested" in {
    val range = CtsUrn("urn:cts:ns:tg.w.v1.ex1:1-100")
    try {
      val extracted = corpus.rangeExtract(range)
    } catch {
      case oe : Ohco2Exception => assert(oe.message == "Failed to match both ends of range reference: urn:cts:ns:tg.w.v1.ex1:1-100")
    }
  }


}
