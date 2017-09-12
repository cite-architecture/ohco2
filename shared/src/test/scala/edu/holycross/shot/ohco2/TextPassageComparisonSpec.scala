package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._

class TextPassageComparisonSpec extends FlatSpec {



  val data = "urn:cts:test:g.w.v1:1.a#One, A, in version 1\n" +
  "urn:cts:test:g.w.v2:1.a#One, A, in version 2\n" +
  "urn:cts:test:g.w.v2:1.b#One, B, in version 2\n" +
  "urn:cts:test:g.w:1#One in notional work\n" +
  "urn:cts:test:g.w.v:1#One in version\n" +
  "urn:cts:test:g.w.v:2#Two in version\n" +
  "urn:cts:test:g.w.v:3#Three in version\n" +
  "urn:cts:test:g.w.v:4#Four in version\n"

  val corpus  = Corpus(data,"#")


  "A Corpus" should "identify when two passage nodes are identical" in {
    val urn = CtsUrn("urn:cts:test:g.w.v2:1.a")
    val relation = corpus.relation(urn, urn)
    assert (relation == TextPassageTopology.PassageEquals)
  }
  it should "identify when two ranges are identical" in {
  }

  it should "identify when one node precedes another" in {
    val a = CtsUrn("urn:cts:test:g.w.v2:1.a")
    val b = CtsUrn("urn:cts:test:g.w.v2:1.b")
    val abRelation = corpus.relation(a,b)
    assert (abRelation == TextPassageTopology.PassagePrecedes)

    val baRelation = corpus.relation(b,a)
    assert(baRelation == TextPassageTopology.PassageFollows)
  }

  it should "identify when a range contains a node" in {
    val r = CtsUrn("urn:cts:test:g.w.v:2-4")
    val pt = CtsUrn("urn:cts:test:g.w.v:3")

    val ptRangeRelation = corpus.relation(r,pt)
    assert(ptRangeRelation == TextPassageTopology.PassageContains)

    val rangePointRelation = corpus.relation(pt, r)
    assert(ptRangeRelation == TextPassageTopology.PassageContained)
  }
  it should "identify when one URN precedes and overlaps another" in pending
  it should "idetnify when one URN overlaps and follows another"in pending




}
