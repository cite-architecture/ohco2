package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._

class CorpusSelectionSpec extends FlatSpec {

  def splat(c: Corpus): Unit = {
    println(c.size)
    println(c.urns.mkString("\n"))
  }

  val data = "urn:cts:test:g.w.v1:1.a#One, A, in version 1\n" +
  "urn:cts:test:g.w.v2:1.a#One, A, in version 2\n" +
  "urn:cts:test:g.w.v2:1.b#One, B, in version 2\n" +
  "urn:cts:test:g.w.v2:1.c#One, C, in version 2\n" +
  "urn:cts:test:g.w.v2:1.d#One, D, in version 2\n" +
  "urn:cts:test:g.w.v2:2.a#Two, A, in version 2\n" +
  "urn:cts:test:g.w.v2:2.b#Two, B, in version 2\n" +
  "urn:cts:test:g.w.v3:1#One in version 3\n" +
  "urn:cts:test:g.w.v3:2#Two in version 3\n" +
  "urn:cts:test:g.w.v3:3#Three in version 3\n" +
  "urn:cts:test:g.w.v3:4#Four in version 3\n"

  val corpus  = Corpus(data,"#")


  "URN similarity (twiddling)" should "match specific nodes in concrete texts" in  {
    val urn = CtsUrn("urn:cts:test:g.w.v2:1.a")
    val matched = corpus ~~ urn
    assert (matched.size == 1)
    assert (matched.urns.head == urn)
  }
  it should "match specific nodes or containing nodes from notional works" in {
    val urn = CtsUrn("urn:cts:test:g.w:1.a")
    val matched = corpus ~~ urn

    val expected = Set(
      CtsUrn("urn:cts:test:g.w.v1:1.a"),
      CtsUrn("urn:cts:test:g.w.v2:1.a"),
      CtsUrn("urn:cts:test:g.w.v3:1")
    )
    assert(matched.urns.toSet == expected)
  }

  it should "match specific nodes in a range" in {
    val urn = CtsUrn("urn:cts:test:g.w.v3:2-4")
    val matched = corpus ~~ urn
    val expected = Vector(
      CtsUrn("urn:cts:test:g.w.v3:2"),
      CtsUrn("urn:cts:test:g.w.v3:3"),
      CtsUrn("urn:cts:test:g.w.v3:4")
    )
    assert (matched.urns == expected)
  }

  it should "match containining nodes in a range" in {
    val urn = CtsUrn("urn:cts:test:g.w.v2:1-2")
    val matched = corpus ~~ urn

    val expected = Vector(
      CtsUrn("urn:cts:test:g.w.v2:1.a"),
      CtsUrn("urn:cts:test:g.w.v2:1.b"),
      CtsUrn("urn:cts:test:g.w.v2:1.c"),
      CtsUrn("urn:cts:test:g.w.v2:1.d"),
      CtsUrn("urn:cts:test:g.w.v2:2.a"),
      CtsUrn("urn:cts:test:g.w.v2:2.b")
    )
    assert(matched.urns == expected)
  }


  it should "match specific nodes in a range on a notional work" in {
    val urn = CtsUrn("urn:cts:test:g.w:1-2")

    val matched = corpus ~~ urn
    // does *not* match v1
    val expected = Set(
      CtsUrn("urn:cts:test:g.w.v2:1.a"),
      CtsUrn("urn:cts:test:g.w.v2:1.b"),
      CtsUrn("urn:cts:test:g.w.v2:1.c"),
      CtsUrn("urn:cts:test:g.w.v2:1.d"),
      CtsUrn("urn:cts:test:g.w.v2:2.a"),
      CtsUrn("urn:cts:test:g.w.v2:2.b"),
      CtsUrn("urn:cts:test:g.w.v3:1"),
      CtsUrn("urn:cts:test:g.w.v3:2")
    )
    assert(matched.urns.toSet == expected)
  }


/*
  it should "create a new Corpus by selecting nodes *not* URN-similar (><) to another single node" in  {
    val u1 = CtsUrn("urn:cts:test:g.w.v:1.a")
    assert ((corpus >< u1).size == 4)
  }



  it should "create a new Corpus by selecting nodes *not* URN-similar  (><)  to a range" in pending


  it should "create a new Corpus by selecting nodes contained by (u1 > u2) another single node" in {
      val data = "urn:cts:test:g.w.v:1.a#One, A in version\n" +
      "urn:cts:test:g.w:1#One in notional work\n"
      val corpus  = Corpus(data,"#")


      val u1 = CtsUrn("urn:cts:test:g.w.v:1.a")
      val u2 = CtsUrn("urn:cts:test:g.w:1")

      // u1 contains no other nodes:
      assert((corpus > u1).size == 0)

      // u2 contains u1:
      val contained = corpus > u2
      assert(contained.size == 1)
      assert(contained.nodes(0).urn == CtsUrn("urn:cts:test:g.w.v:1.a"))

  }

  it should "create a new Corpus by selecting nodes contained by (u1 > u2) a range" in  pending
  it should "create a new Corpus by selecting nodes containing (u1 < u2) another single node" in pending
  it should "create a new Corpus by selecting nodes containing (u1 < u2) a range" in  pending


  it should "create a new Corpus by selecting nodes contained by or equal to (u1 >= u2) another single node"
  it should "create a new Corpus by selecting nodes contained by or equal (u1 >= u2) to a range" in  pending
  it should "create a new Corpus by selecting nodes containing or equal to (u1 <= u2) another single node" in pending
  it should "create a new Corpus by selecting nodes containing or equal to (u1 <= u2) a range" in  pending
  */

}
