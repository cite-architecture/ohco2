package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class CorpusAlgebraContainmentSpec extends FlatSpec {

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


  "URN containment"  should  "find individual nodes in a concrete version" in {
    val urn = CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.1")
    val contained = corpus >= urn
    assert(contained.size == 1)
    assert(contained.urns(0) == urn)
  }

  it should "enforce containment on both passage and work hierarchy" in {
    val urn = CtsUrn("urn:cts:ns:tg.w.v1:1.2.1")
    val contained = corpus.containedNodes(urn)
    assert(contained.size == 0)
  }

  it should "find individual nodes in a notional version" in {
    val urn = CtsUrn("urn:cts:ns:tg.w:1.2.1")
    val contained = corpus >= urn
    //splat(contained)

    assert(contained.size == 2)
    val expected = Set(
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.1"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.2.1")
    )
    assert(contained.urns.toSet == expected)

  }
  it should "find nodes in a containing passage in a concrete version" in {
    val urn = CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2")
    val contained = corpus >= urn
    assert(contained.size == 3)
    val expected = Vector(
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.1"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.2"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.3")
    )
    assert(contained.urns == expected)
  }

  it should "find nodes in a containing passage in a notional version" in {
      val urn = CtsUrn("urn:cts:ns:tg.w.v1:1.2")
      val contained = corpus >= urn
      val expected = Vector(CtsUrn("urn:cts:ns:tg.w.v1:1.2"))
      assert(contained.urns == expected)
  }

  it should "find nodes in a range in a concrete version" in {
    val range = CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.2-1.2.2")
    val contained = corpus >= range
    val expectedUrns = Vector(
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.2"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.3"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.1"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.2")
    )
    assert(contained.urns == expectedUrns)
  }



  it should "find nodes in a range in a notional version" in {
    val range = CtsUrn("urn:cts:ns:tg.w:1.1.2-1.2.2")

    val contained = corpus >= range
    val expectedUrns = Set(
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.2"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.3"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.1"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.2"),

      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.1.2"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.1.3"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.2.1"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.2.2")
    )

    assert(contained.urns.toSet == expectedUrns)

  }



/*
  it should "find parents and cousins of an exemplar container" in {
    val urn = CtsUrn("urn:cts:ns:tg.w.v1:1.1")
    println("For urn " + urn)
    val filtered = (corpus ~~ urn.dropPassage) ~~ urn
    // result should be seven nodes: the version ("1.1") and three from each exemplar (…:1.1.1, 1.1.2, 1.1.3)
    assert (filtered.nodes.size == 7)
    val expectedSet = Set(
      CtsUrn("urn:cts:ns:tg.w.v1:1.1"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.1"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.2"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.3"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.1.1"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.1.2"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex2:1.1.3")
    )
    assert (expectedSet == filtered.urns.toSet)
  }

  it  should  "find parents and cousins of an exemplar-level range of containers" in {
    val urn = CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1-1.2")
    val filtered = (corpus ~~ urn)
    assert(filtered.size == 30)
  }


  it should "find contained nodes and work-descendents of a version-level URN with a range" in {
    val urn = CtsUrn("urn:cts:ns:tg.w.v1:1.2-1.3")
    val filtered = corpus ~~ urn
    assert(filtered.size == 32)
  }


  it should "find a version-level node when there are no exemplars" in {
   val urn = CtsUrn("urn:cts:ns:tg.w.v2:1.2")
   val filtered = corpus ~~ urn
   // result should be one node
   assert (filtered.nodes.size == 1)
   assert(filtered.nodes(0).urn == urn)
   }

   it should "find version-level nodes in a range when there are no exemplars" in {
     val urn = CtsUrn("urn:cts:ns:tg.w.v2:2.1-2.2")
     val filtered = corpus ~~ urn
     assert (filtered.nodes.size == 2)
     println(filtered.urns.mkString("\n"))
     val expected = Vector(CtsUrn("urn:cts:ns:tg.w.v2:2.1"), CtsUrn("urn:cts:ns:tg.w.v2:2.2"))
     assert (filtered.urns == expected)
   }
*/
}
