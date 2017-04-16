package edu.holycross.shot.ohco2

import org.scalatest.FlatSpec
import edu.holycross.shot.cite._
import scala.io.Source
import java.io._
import scala.xml._

class TextRepositorySourceSpec extends FlatSpec {


  val inv = "jvm/src/test/resources/xmlrepo/inventory.xml"
  val citeConf = "jvm/src/test/resources/xmlrepo/citationconfig.xml"
  val baseDir = "jvm/src/test/resources/xmlrepo/texts"


  "The TextRepositorySource object"  should  "create two-column CEF for an XML file" in  {
    // first get OnlineDocument Vector

    val olDocs = TextRepositorySource.onlineVector(citeConf, baseDir)
    val hdt = olDocs(0)

    val cex = TextRepositorySource.cexForDocument(hdt,inv,citeConf)
    val lines = cex.split("\n")
    val expectedLines = 33
    assert(lines.size == expectedLines)
  }



  it should "create CEF for a two-column file" in pending
  it should "create CEF for an 82XF file" in pending

  it should "create a Catalog from XML sources" in {
    val catalog = TextRepositorySource.catalogFromXmlFile(inv,citeConf)
    assert( catalog.size == 1)
  }

  it should "create a TextRepository from local XML files cataloged by XML documents" in {
    val repo = TextRepositorySource.fromFiles(inv,citeConf,baseDir)
    assert(repo.catalog.size == 1)
    assert(repo.corpus.size == 33)
  }





  it should "create a TextRepository from a single file of CEX data" in {
    val cex = "jvm/src/test/resources/million.cex"
    val repo = TextRepositorySource.fromCexFile(cex,"#")
    repo match {
      case tr: TextRepository => assert(true)
      case _ => fail("Should have created a TextRepository")
    }
  }
}
