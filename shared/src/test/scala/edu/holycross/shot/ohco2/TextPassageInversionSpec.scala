package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._

class TextPassageInversionSpec extends FlatSpec {


  "The Corpus object" should "maintain equality relaitons when inverting topology" in  {
    assert(Corpus.invertTopology(TextPassageTopology.PassageEquals) == TextPassageTopology.PassageEquals)
  }

  it should "invert preceding/following relations" in {
    assert(Corpus.invertTopology(TextPassageTopology.PassagePrecedes) == TextPassageTopology.PassageFollows)
    assert(Corpus.invertTopology(TextPassageTopology.PassageFollows) == TextPassageTopology.PassagePrecedes)
  }

  it should "invert containment relations" in {
    assert(Corpus.invertTopology(TextPassageTopology.PassageContains) == TextPassageTopology.PassageContainedBy)
    assert(Corpus.invertTopology(TextPassageTopology.PassageContainedBy) == TextPassageTopology.PassageContains)
  }

  it should "invert overlap and precedes relations" in {
    assert(Corpus.invertTopology(TextPassageTopology.PassagePrecedesAndOverlaps) == TextPassageTopology.PassageOverlapsAndPrecededBy)
    assert(Corpus.invertTopology(TextPassageTopology.PassageOverlapsAndPrecededBy) == TextPassageTopology.PassagePrecedesAndOverlaps)
  }

}
