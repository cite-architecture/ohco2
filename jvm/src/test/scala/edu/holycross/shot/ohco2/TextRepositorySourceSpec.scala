package edu.holycross.shot.ohco2

import org.scalatest.FlatSpec
import edu.holycross.shot.cite._
import scala.io.Source
import java.io._
import scala.xml._

class TextRepositorySourceSpec extends FlatSpec {


  val catCex = "jvm/src/test/resources/xmlrepo/ctscatalog.cex"
  val citeConf = "jvm/src/test/resources/xmlrepo/citationconfig.cex"
  val baseDir = "jvm/src/test/resources/xmlrepo/texts"


  "The TextRepositorySource object"  should  "create a CEX representation of a text from a documented XML file" in {
    val oneEntry = "urn:cts:greekLit:tlg0016.tlg001.grc:#xml#test-hdt-grc.xml#tei->http://www.tei-c.org/ns/1.0#/tei:TEI/tei:text/tei:body/tei:div[@n = '?']/tei:div[@n = '?']"
    val relativeOnlineDoc = OnlineDocument(oneEntry,"#",",")

    val onlineDoc = relativeOnlineDoc.absolutePath("jvm/src/test/resources/xmlrepo/texts/")

    val cex = TextRepositorySource.cexForXml(onlineDoc,"#")
    val lines = cex.split("\n").toVector
    val expectedSections = 33
    assert(lines.size == expectedSections)
  }

  it should "create a Vector of OnlineDocuments from a configuration file" in {
    val docVector = TextRepositorySource.onlineVector(citeConf, baseDir)
    for (doc <- docVector) {
      doc match {
        case olDoc: OnlineDocument => assert(true)
        case _ => fail("Should have created an online document")
      }
    }
  }


  it should "create a single Corpus from a vector of OnlineDocument configurations" in {
    val onlineVect = TextRepositorySource.onlineVector(citeConf, baseDir)
    val c = TextRepositorySource.corpusFromOnlineVector(onlineVect)
    val expectedNodes = 133
    assert(c.size == expectedNodes)
  }

  it should "create a TextRepository from local XML files cataloged by XML documents" in {

    val repo = TextRepositorySource.fromFiles(catCex,citeConf,baseDir)
    val expectedTexts = 2
    val expectedNodes = 133
    assert(repo.catalog.size == expectedTexts)
    assert(repo.corpus.size == expectedNodes)
  }


  it should "create CEF for a two-column file" in pending
  it should "create CEF for an 82XF file" in pending

  it should "create a Catalog from CEX sources" in pending /*{
    val catalog = TextRepositorySource.catalogFromXmlFile(catCex,citeConf)
    assert( catalog.size == 1)
  }*/







  it should "create a TextRepository from a single file of CEX data" in {
    val cex = "jvm/src/test/resources/million.cex"
    val repo = TextRepositorySource.fromCexFile(cex,"#")
    repo match {
      case tr: TextRepository => assert(true)
      case _ => fail("Should have created a TextRepository")
    }
  }
}
