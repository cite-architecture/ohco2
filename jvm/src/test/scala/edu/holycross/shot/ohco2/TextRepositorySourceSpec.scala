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


  "The TextRepositorySource object"  should  "create two-column CEF for texts in XML files" in  pending /*{
    // first get OnlineDocument Vector

    val olDocs = TextRepositorySource.onlineVector(citeConf, baseDir)
    val hdt = olDocs(0)

    val cex = TextRepositorySource.cexForDocument(hdt,inv,citeConf)
    val lines = cex.split("\n")
    val expectedLines = 33
    assert(lines.size == expectedLines)
  } */

  it should "create a CEX representation of a documented XML source" in {
    val oneEntry = "urn:cts:greekLit:tlg0016.tlg001.grc:#xml#test-hdt-grc.xml#tei->http://www.tei-c.org/ns/1.0#/tei:TEI/tei:text/tei:body/tei:div[@n = '?']/tei:div[@n = '?']"
    val onlineDoc = OnlineDocument(oneEntry,"#",",")

    val cex = TextRepositorySource.cexForXml(onlineDoc,"#",",")
    println("CEx IS "+ cex)
  }

  it should "create a Vector of OnlineDocuments from a configuration file" in {
    val docVector = TextRepositorySource.onlineVector(citeConf, baseDir)
  }

  it should "create a TextRepository from local XML files cataloged by XML documents" in pending /* {
    val repo = TextRepositorySource.fromFiles(catCex,citeConf,baseDir)
    assert(repo.catalog.size == 1)
    //assert(repo.corpus.size == 33)
  }*/


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
