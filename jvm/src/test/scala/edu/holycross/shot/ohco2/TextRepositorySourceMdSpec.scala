package edu.holycross.shot.ohco2

import org.scalatest.FlatSpec
import edu.holycross.shot.cite._
import scala.io.Source
import java.io._
import scala.xml._

class TextRepositorySourceMdSpec extends FlatSpec {


  val inv = "jvm/src/test/resources/xmlrepo/inventory.xml"
  val citeConf = "jvm/src/test/resources/xmlrepo/citationconfig.xml"
  val baseDir = "jvm/src/test/resources/xmlrepo/texts"


  "The TextRepositorySource object"  should  "create CEF for a markdown file" in pending /*{
    val inv = "jvm/src/test/resources/mdrepo/inventory.xml"
    val citeConf = "jvm/src/test/resources/mdrepo/citationconfig.xml"
    val baseDir = "jvm/src/test/resources/mdrepo/texts"

    val olDocs = TextRepositorySource.onlineVector(citeConf, baseDir)
    val demo = olDocs(0)

    val cex = TextRepositorySource.cexForDocument(demo,inv,citeConf,outputDelim = "#")
    val lines = cex.split("\n").toVector

    val expectedHeader = "urn:cts:mddemo:demo.powered.cite:1.h1# Powered by OHCO2"
    assert(lines(0) == expectedHeader)

    val expectedLines = 19
    assert(lines.size == 19)
  }*/

  it should "create a TextRepository from local Markdown files cataloged by XML documents" in pending /*{
    val inv = "jvm/src/test/resources/mdrepo/inventory.xml"
    val citeConf = "jvm/src/test/resources/mdrepo/citationconfig.xml"
    val baseDir = "jvm/src/test/resources/mdrepo/texts"


    val repo = TextRepositorySource.fromFiles(inv,citeConf,baseDir,outputDelimiter = "#")
    assert(repo.catalog.size == 1)
    assert(repo.corpus.size == 19)
  }*/

}
