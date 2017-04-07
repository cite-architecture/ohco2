package edu.holycross.shot.ohco2

import org.scalatest.FlatSpec
import edu.holycross.shot.cite._
import scala.io.Source
import java.io._
import scala.xml._

class TextRepositorySourceSpec extends FlatSpec {

  "The TextRepositorySource object"  should  "create CEF for an XML file" in  {
    // first get OnlineDocument Vector
    val citeconf = "jvm/src/test/resources/repository/citationconfig.xml"
    val inv = "jvm/src/test/resources/repository/inventory.xml"
    val baseDir = "jvm/src/test/resources/repository/texts"
    val olDocs = TextRepositorySource.onlineVector(citeconf, baseDir)
    val hdt = olDocs(0)

    val cex = TextRepositorySource.cexForDocument(hdt,inv,citeconf)
    println("HDT: " + cex)


  }
  it should "create CEF for a markdown file" in pending
  it should "create CEF for a two-column file" in pending
  it should "create CEF for an 82XF file" in pending

  it should "create a Catalog from XML sources" in {
    val inv = "jvm/src/test/resources/repository/inventory.xml"
    val citeconf = "jvm/src/test/resources/repository/citationconfig.xml"
    val catalog = TextRepositorySource.catalogFromXmlFile(inv,citeconf)
    assert( catalog.size == 1)
  }

  it should "create a TextRepository from local files cataloged by XML documents" in pending
}
