package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._

class CorpusSelectionSpec extends FlatSpec {

  "A Corpus" should "create a new Corpus by selecting nodes that are URN-similar (~~) to a single node" in {
    val data = "urn:cts:test:g.w.v:1.a#One, A in version\n" +
    "urn:cts:test:g.w:1#One in notional work\n"
    val corpus  = Corpus(data,"#")

    val u1 = CtsUrn("urn:cts:test:g.w.v:1.a")
    val u2 = CtsUrn("urn:cts:test:g.w:1")

    // since both nodes are URN similar, twiddling either
    // way should find both:
    assert((corpus ~~ u1) == (corpus ~~ u2))
  }
  it "create a new Corpus by selecting nodes that are URN-similar (~~) to a range" in
  it should "create a new Corpus by selecting nodes *not* URN-similar (><) to another single node" in pending
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

}
