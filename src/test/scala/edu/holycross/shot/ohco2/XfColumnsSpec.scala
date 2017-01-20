package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._
import java.io._


class XfColumnsSpec extends FlatSpec {



  // urn: String, nxt: String, prv: String, txt: String
  "A vector of XfColumns objects"  should "be automatically generated from a corpus of citable texts" in {
    val srcFile = "src/test/resources/shortscholia.tsv"
    val corpus = Corpus(srcFile,"\t")
    val xfcols = corpus.to82xfVector
    xfcols match {
      case xf: Vector[XfColumns] => assert(true)
      case _ => fail("Created wrong kind of object")
    }
  }
  it should "have an empty string for the previous value of the first element" in {
    val srcFile = "src/test/resources/shortscholia.tsv"
    val corpus = Corpus(srcFile,"\t")
    val xfcols = corpus.to82xfVector
    assert(xfcols(0).prv.isEmpty)
  }
  it should "have a nonempty string for all other values of the first element" in {
    val srcFile = "src/test/resources/shortscholia.tsv"
    val corpus = Corpus(srcFile,"\t")
    val xfcols = corpus.to82xfVector
    assert(xfcols(0).urn.nonEmpty)
    assert(xfcols(0).nxt.nonEmpty)
    assert(xfcols(0).txt.nonEmpty)
  }
  it should "have an empty string for the next value of the last element" in {
    val srcFile = "src/test/resources/shortscholia.tsv"
    val corpus = Corpus(srcFile,"\t")
    val xfcols = corpus.to82xfVector
    assert(xfcols.last.nxt.isEmpty)
  }

  it should "have a non-empty string for all other values of the last element" in {
    val srcFile = "src/test/resources/shortscholia.tsv"
    val corpus = Corpus(srcFile,"\t")
    val xfcols = corpus.to82xfVector
    assert(xfcols.last.urn.nonEmpty)
    assert(xfcols.last.prv.nonEmpty)
    assert(xfcols.last.txt.nonEmpty)
  }

  it should "have a non-empty string for all other values " in {
    val srcFile = "src/test/resources/shortscholia.tsv"
    val corpus = Corpus(srcFile,"\t")
    val xfcols = corpus.to82xfVector
    for (c <- xfcols) {
      assert(c.urn.nonEmpty)
      assert(c.txt.nonEmpty)
    }
    val middleRows = xfcols.drop(1).dropRight(1)
    assert (middleRows.size == xfcols.size - 2)
    var count = 0
    for (c <- middleRows) {
      assert(c.prv.nonEmpty)
      assert(c.nxt.nonEmpty)
    }
  }
  it should "have a function to format as a single string" in {
    val srcFile = "src/test/resources/shortscholia.tsv"
    val corpus = Corpus(srcFile,"\t")
    val xfcols = corpus.to82xfVector
    assert(xfcols.head.rowString("#") == """urn:cts:greekLit:tlg5026.msA.hmt:1.1.lemma#urn:cts:greekLit:tlg5026.msA.hmt:1.1.comment##<div xmlns="http://www.tei-c.org/ns/1.0" n="lemma"> <p> μῆνιν ἄειδε</p></div>""")
  }

}
