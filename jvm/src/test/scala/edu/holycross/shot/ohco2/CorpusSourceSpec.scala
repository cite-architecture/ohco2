package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._
import scala.io.Source
import java.io._


class CorpusSourceSpec extends FlatSpec {

  "Corpus file IO"  should "instantiate a corpus from a 2-column delimited text file" in {
    val srcFile = "jvm/src/test/resources/scholia-twocolumns.tsv"
    val corpus = CorpusSource.fromFile(srcFile,"\t")
    corpus match {
      case c: Corpus => assert(true)
      case _ => fail("Failed to create Corpus object")
    }
  }

  it should "have a non-empty vector of citable nodes" in {
    val srcFile = "jvm/src/test/resources/scholia-twocolumns.tsv"
    val corpus = CorpusSource.fromFile(srcFile,"\t")
    assert(corpus.nodes.size > 0)
  }


  it should "offer a convenience method for extracting the string contents from a list of citable nodes as a single string" in {
    val filterUrn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.2")
    val srcFile = "jvm/src/test/resources/scholia-twocolumns.tsv"
    val corpus = CorpusSource.fromFile(srcFile, "\t")
    val actualText = corpus.textContents(filterUrn, "\n")

    // sample beginning and end of long text:
    val expectedOpening = """<div xmlns="http://www.tei-c.org/ns/1.0" n="lemma"> <p> μῆνις</p>"""
    val expectedClosing = """οὐκ ὀρθῶς.</p></div>"""


    assert(actualText.startsWith(expectedOpening))
    assert(actualText.endsWith(expectedClosing))
  }

  it should "offer a convenience method to extract a list of works cited in the corpus" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.1-1.2")
    val srcFile = "jvm/src/test/resources/scholia-twocolumns.tsv"
    val corpus = CorpusSource.fromFile(srcFile,"\t")
    val expectedWorks = Set(CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:"),
      CtsUrn("urn:cts:greekLit:tlg5026.msAim.hmt:"),
      CtsUrn("urn:cts:greekLit:tlg5026.msAint.hmt:"),
      CtsUrn("urn:cts:greekLit:tlg5026.msAext.hmt:"),
      CtsUrn("urn:cts:greekLit:tlg5026.msAil.hmt:"),
      CtsUrn("urn:cts:greekLit:tlg5026.msAimlater.hmt:")
    )
    assert (corpus.citedWorks.toSet == expectedWorks)
  }

  it should "have a method to get 82xf format in a string" in {
    val srcFile = "jvm/src/test/resources/shortscholia.tsv"
    val corpus = CorpusSource.fromFile(srcFile,"\t")
    val xfString = corpus.to82xfString("#")
    assert(xfString.split("\n").size == 10)
  }
  it should "have a method to write 2 column format in a string" in {
    val srcFile = "jvm/src/test/resources/shortscholia.tsv"
    val corpus = CorpusSource.fromFile(srcFile,"\t")
    val twoColStr = corpus.to2colString("#")
    assert(twoColStr.split("\n").size == 10)
  }

  it should "have a method to write 82xf to a named file" in {
    val srcFile = "jvm/src/test/resources/shortscholia.tsv"
    val corpus = CorpusSource.fromFile(srcFile,"\t")
    val testFileName = "jvm/src/test/resources/test82xf.txt"
    CorpusSource.to82xfFile(corpus, testFileName, "#")
    val outputLines = Source.fromFile(testFileName).getLines.toVector
    assert (outputLines.size == 10)
    val testFile = new File(testFileName)
    testFile.delete()
  }
  it should "have a method to write 82xf to an existing file object" in {
    val srcFile = "jvm/src/test/resources/shortscholia.tsv"
    val corpus = CorpusSource.fromFile(srcFile,"\t")
    val testFile = new File("jvm/src/test/resources/test82xf.txt")
    CorpusSource.to82xfFile(corpus, testFile, "#")
    val outputLines = Source.fromFile(testFile).getLines.toVector
    assert (outputLines.size == 10)
    testFile.delete()
  }

  it should "have a method to write 2 column to a named file" in {
    val srcFile = "jvm/src/test/resources/shortscholia.tsv"
    val corpus = CorpusSource.fromFile(srcFile,"\t")
    val testFileName = "jvm/src/test/resources/test82xf.txt"
    CorpusSource.to2colFile(corpus,testFileName, "#")
    val outputLines = Source.fromFile(testFileName).getLines.toVector
    assert (outputLines.size == 10)
    val testFile = new File(testFileName)
    testFile.delete()
  }
  it should "have a method to write 2 column to an existint file object" in {
    val srcFile = "jvm/src/test/resources/shortscholia.tsv"
    val corpus = CorpusSource.fromFile(srcFile,"\t")
    val testFileName = "jvm/src/test/resources/test82xf.txt"
    val testFile = new File(testFileName)
    CorpusSource.to2colFile(corpus,testFile, "#")
    val outputLines = Source.fromFile(testFileName).getLines.toVector
    assert (outputLines.size == 10)

    testFile.delete()
  }

}
