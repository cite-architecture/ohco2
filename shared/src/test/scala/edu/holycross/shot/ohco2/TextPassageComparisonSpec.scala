package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._

class TextPassageComparisonSpec extends FlatSpec {



  val data = "urn:cts:test:g.w.v1:1.a#One, A, in version 1\n" +
  "urn:cts:test:g.w.v2:1.a#One, A, in version 2\n" +
  "urn:cts:test:g.w.v2:1.b#One, B, in version 2\n" +
  "urn:cts:test:g.w:1#One in notional work\n" +
  "urn:cts:test:g.w.v:1#One in version\n" +
  "urn:cts:test:g.w.v:2#Two in version\n"

  val corpus  = Corpus(data,"#")


  "A Corpus" should "identify when two passage nodes are identical" in {
    val urn = CtsUrn("urn:cts:test:g.w.v2:1.a")
    val relation = corpus.relation(urn, urn)
    assert (relation == TextPassageTopology.PassageEquals)
  }
  it should "identify when two ranges are identical" in {
  }
  it should "identify when one node precedes another" in pending
  it should "identify when one URN contains another" in pending
  it should "identify when one URN precedes and overlaps another" in pending
  it should "idetnify when one URN overlaps and follows another"in pending




}
