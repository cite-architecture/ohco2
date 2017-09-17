package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._

class TextPassageComparisonSpec extends FlatSpec {

  val data = "urn:cts:test:g.w.v1:1.a#One, A, in version 1\n" +
  "urn:cts:test:g.w.v2:1.a#One, A, in version 2\n" +
  "urn:cts:test:g.w.v2:1.b#One, B, in version 2\n" +
  "urn:cts:test:g.w.v2:1.c#One, C, in version 2\n" +
  "urn:cts:test:g.w.v2:2.a#Two, A, in version 2\n" +
  "urn:cts:test:g.w.v2:2.b#Two, B, in version 2\n" +
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

    val ptRangeRelation = corpus.relation(pt,r)
    assert(ptRangeRelation == TextPassageTopology.PassageContainedBy)

    val rangePointRelation = corpus.relation(r,pt)
    assert(rangePointRelation == TextPassageTopology.PassageContains)
  }


  it should "identify the relation of a containing URN to a node" in {
    val container = CtsUrn("urn:cts:test:g.w.v2:1")
    val node =  CtsUrn("urn:cts:test:g.w.v2:1.a")



    val nodeContainerRelation = corpus.relation(node,container)
    assert(nodeContainerRelation == TextPassageTopology.PassageContainedBy)
    
    val containerNodeRelation = corpus.relation(container,node)
    assert(containerNodeRelation == TextPassageTopology.PassageContains)

  }

  it should "identify when two ranges are identical" in {
    val range = CtsUrn("urn:cts:test:g.w.v:1-3")
    assert (corpus.relation(range, range) == TextPassageTopology.PassageEquals)
  }

  it should "identify when a container and a range are equal" in  {
    val range = CtsUrn("urn:cts:test:g.w.v2:1.a-1.c")
    val container = CtsUrn("urn:cts:test:g.w.v2:1")
    assert (corpus.relation(range,container) == TextPassageTopology.PassageEquals)
  }

  it should "identify when one range contains another" in {
    val r1 = CtsUrn("urn:cts:test:g.w.v:1-4")
    val r2 = CtsUrn("urn:cts:test:g.w.v:3-4")
    assert(corpus.relation(r1,r2) == TextPassageTopology.PassageContains)
    assert(corpus.relation(r2,r1) == TextPassageTopology.PassageContainedBy)
  }

  it should "identify when one range precedes another" in {
    val r1 = CtsUrn("urn:cts:test:g.w.v:1-2")
    val r2 = CtsUrn("urn:cts:test:g.w.v:3-4")

    assert(corpus.relation(r1,r2) == TextPassageTopology.PassagePrecedes)
    assert(corpus.relation(r2,r1) == TextPassageTopology.PassageFollows)
  }

  it should "identify when one range precedes and overlaps another" in  {
    val r1 = CtsUrn("urn:cts:test:g.w.v:1-3")
    val r2 = CtsUrn("urn:cts:test:g.w.v:3-4")

    assert(corpus.relation(r1,r2) == TextPassageTopology.PassagePrecedesAndOverlaps)
    assert(corpus.relation(r2,r1) == TextPassageTopology.PassageOverlapsAndFollows)
  }


  it should "idenify when one range overlaps and follows another"in {

    val r1 = CtsUrn("urn:cts:test:g.w.v:3-4")
    val r2 = CtsUrn("urn:cts:test:g.w.v:2-3")

    assert(corpus.relation(r1,r2) == TextPassageTopology.PassageOverlapsAndFollows)
    assert(corpus.relation(r2,r1) == TextPassageTopology.PassagePrecedesAndOverlaps)
  }




}
