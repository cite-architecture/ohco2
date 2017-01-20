package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class CorpusSpec extends FlatSpec {

  "A corpus of citable nodes"  should "offer a constructor signature for instantiating a corpus from a 2-column delimited text file" in {
    val srcFile = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(srcFile,"\t")
    corpus match {
      case c: Corpus => assert(true)
      case _ => fail("Failed to create Corpus object")
    }
  }

  it should "have a non-empty vector of citable nodes" in {
    val srcFile = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(srcFile,"\t")
    assert(corpus.nodes.size > 0)
  }


  // filtering on single node URNS:
  it should "filter the corpus contents against a URN with matching work and passage hierarchies" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1.lemma")
    val srcFile = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(srcFile,"\t")
    val filtered = corpus.urnMatch(urn)
    // result should be a single node with same URN:
    assert (filtered.size == 1)
    assert(filtered(0).urn == urn)
  }

  it should "filter the corpus contents against a  URN with matching work hierarchy and containing passage hierarchy" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1")
    val srcFile = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(srcFile,"\t")
    val filtered = corpus.urnMatch(urn)
    // result should be two nodes with:
    assert (filtered.size == 2)
    // add test on trimmed version of URN...
  }

  it should "filter the corpus contents against a URN with containing work hierarchy and matching passage hierarchy" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.1.lemma")
    val srcFile = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(srcFile,"\t")
    val filtered = corpus.urnMatch(urn)
    assert (filtered.size == 1)
    // urns differ only in version:
    assert (filtered(0).urn.textGroup == urn.textGroup)
    assert (filtered(0).urn.work == urn.work)
    assert (filtered(0).urn.passageComponent == urn.passageComponent)
  }


  it should "filter the corpus contents against a URN with containing work hierarchy and containing passage hierarchy" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.1")
    val srcFile = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(srcFile,"\t")
    val filtered = corpus.urnMatch(urn)
    assert (filtered.size == 2)
    for (txtnode <- filtered) {
      assert (txtnode.urn.textGroup == urn.textGroup)
      assert (txtnode.urn.work == urn.work)
    }
  }

  it should "return an empty vector if the filtering URN does not appear in the corpus" in {
      val urn = CtsUrn("urn:cts:fake:group:none")
      val srcFile = "src/test/resources/scholia-twocolumns.tsv"
      val corpus = Corpus(srcFile,"\t")
      val filtered = corpus.urnMatch(urn)
      assert(filtered.isEmpty)
  }

  // filtering on range URNS:
  it should "filter the corpus contents against a range URN with matching work hierarchy and matching range end points" in  {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1.lemma-1.2.comment")
    val srcFile = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(srcFile,"\t")
    val filtered = corpus.urnMatch(urn)
    assert (filtered.size == 4)
    for (txtnode <- filtered) {
      assert (txtnode.urn.textGroup == urn.textGroup)
      assert (txtnode.urn.work == urn.work)
      assert (txtnode.urn.version == urn.version)
    }
  }

  it should "filter the corpus contents against a range URN with matching work hierarchy and containing range beginning point" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1-1.2.comment")
    val srcFile = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(srcFile,"\t")
    val filtered = corpus.urnMatch(urn)
    assert (filtered.size == 4)
    for (txtnode <- filtered) {
      assert (txtnode.urn.textGroup == urn.textGroup)
      assert (txtnode.urn.work == urn.work)
      assert (txtnode.urn.version == urn.version)
    }
  }

  it should "filter the corpus contents against a range URN with matching work hierarchy and containing range ending point" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1.lemma-1.2")
    val srcFile = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(srcFile,"\t")
    val filtered = corpus.urnMatch(urn)
    assert (filtered.size == 4)
    for (txtnode <- filtered) {
      assert (txtnode.urn.textGroup == urn.textGroup)
      assert (txtnode.urn.work == urn.work)
      assert (txtnode.urn.version == urn.version)
    }
  }

  it should "filter the corpus contents against a range URN with matching work hierarchy and containing points for range beginning and ending" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1-1.2")
    val srcFile = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(srcFile,"\t")
    val filtered = corpus.urnMatch(urn)
    assert (filtered.size == 4)
    for (txtnode <- filtered) {
      assert (txtnode.urn.textGroup == urn.textGroup)
      assert (txtnode.urn.work == urn.work)
      assert (txtnode.urn.version == urn.version)
    }
  }

  it should "filter the corpus contents against a range URN with containing work hierarchy and matching range end points" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.1.lemma-1.2.comment")
    val srcFile = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(srcFile,"\t")
    val filtered = corpus.urnMatch(urn)
    assert (filtered.size == 4)
    for (txtnode <- filtered) {
      assert (txtnode.urn.textGroup == urn.textGroup)
      assert (txtnode.urn.work == urn.work)
    }
  }

  it should "filter the corpus contents against a range URN with containing work hierarchy and containing range beginning point" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.1-1.2.comment")
    val srcFile = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(srcFile,"\t")
    val filtered = corpus.urnMatch(urn)
    assert (filtered.size == 4)
    for (txtnode <- filtered) {
      assert (txtnode.urn.textGroup == urn.textGroup)
      assert (txtnode.urn.work == urn.work)
    }
  }

  it should "filter the corpus contents against a range URN with containing work hierarchy and containing range ending point" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.1.lemma-1.2")
    val srcFile = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(srcFile,"\t")
    val filtered = corpus.urnMatch(urn)
    assert (filtered.size == 4)
    for (txtnode <- filtered) {
      assert (txtnode.urn.textGroup == urn.textGroup)
      assert (txtnode.urn.work == urn.work)
    }
  }

  it should "filter the corpus contents against a range URN with containing work hierarchy and containing points for range beginning and ending" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.1-1.2")
    val srcFile = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(srcFile,"\t")
    val filtered = corpus.urnMatch(urn)
    assert (filtered.size == 4)
    for (txtnode <- filtered) {
      assert (txtnode.urn.textGroup == urn.textGroup)
      assert (txtnode.urn.work == urn.work)
    }
  }

  it should "return an empty vector if the first node of the filtering URN does not appear in the corpus" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA:FAKE-1.2")
    val srcFile = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(srcFile,"\t")
    val filtered = corpus.urnMatch(urn)
    assert (filtered.isEmpty)
  }
  it should "return an empty vector if the second node of the filtering URN does not appear in the corpus" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.1-FAKE")
    val srcFile = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(srcFile,"\t")
    val filtered = corpus.urnMatch(urn)
    assert (filtered.isEmpty)
  }

  // CTS-like convenience methods
  it should "offer a convenience method for finding the first citable node in a filtered vector" in {
    val filterUrn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.2.lemma-1.10.comment")
    val srcFile = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(srcFile)
    val expectedFirst = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.2.lemma")
    assert(corpus.getFirstNode(filterUrn).urn == expectedFirst)
  }

  it should "offer a convenience method for finding the last citable node in a filtered vector" in {
    val filterUrn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.2")
    val srcFile = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(srcFile)
    val expectedLast = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.2.comment")
    assert(corpus.getLastNode(filterUrn).urn == expectedLast)
  }
  it should "offer a convenience method for reducing a list of citable nodes to a list of URN" in {
    val filterUrn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.2")
    val srcFile = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(srcFile)
    val expectedReff = Vector(CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.2.lemma"),
    CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.2.comment"))
    assert(corpus.getValidReff(filterUrn) == expectedReff)
  }

  it should "offer a convenience method for extracting the string contents from a list of citable nodes as a single string" in {
    val filterUrn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.2")
    val srcFile = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(srcFile)
    val actualText = corpus.getTextContents(filterUrn)

    // sample beginning and end of long text:
    val expectedOpening = """<div xmlns="http://www.tei-c.org/ns/1.0" n="lemma"> <p> μῆνις</p>"""
    val expectedClosing = """οὐκ ὀρθῶς.</p></div>"""


    assert(actualText.startsWith(expectedOpening))
    assert(actualText.endsWith(expectedClosing))
  }

  it should "offer a convenience method to extract a list of works cited in the corpus" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.1-1.2")
    val srcFile = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(srcFile)
    val expectedWorks = Set(CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:"),
      CtsUrn("urn:cts:greekLit:tlg5026.msAim.hmt:"),
      CtsUrn("urn:cts:greekLit:tlg5026.msAint.hmt:"),
      CtsUrn("urn:cts:greekLit:tlg5026.msAext.hmt:"),
      CtsUrn("urn:cts:greekLit:tlg5026.msAil.hmt:"),
      CtsUrn("urn:cts:greekLit:tlg5026.msAimlater.hmt:")
    )
    assert (corpus.citedWorks.toSet == expectedWorks)
  }

}
