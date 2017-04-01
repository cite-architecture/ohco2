package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._


/**
*/
class CexSerializationSpec extends FlatSpec {

  val catalogData = """urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online
urn:cts:greekLit:tlg5026.msA.hmt:#book/scholion/part#Scholia to the Iliad#Main scholia of the Venetus A#HMT project edition##true
urn:cts:greekLit:tlg5026.msAim.hmt:#book/scholion/part#Scholia to the Iliad#Intermarginal scholia of the Venetus A#HMT project edition##true
urn:cts:greekLit:tlg5026.msAint.hmt:#book/scholion/part#Scholia to the Iliad#Interior scholia of the Venetus A#HMT project edition##true
urn:cts:greekLit:tlg5026.msAext.hmt:#book/scholion/part#Scholia to the Iliad#Exterior scholia of the Venetus A#HMT project edition##true
urn:cts:greekLit:tlg5026.msAil.hmt:#book/scholion/part#Scholia to the Iliad#Interlinear scholia of the Venetus A#HMT project edition##true
urn:cts:greekLit:tlg5026.msAimlater.hmt:#book/scholion/part#Scholia to the Iliad#Later intermarginal scholia of the Venetus A#HMT project edition##true
"""


  val catalog = Catalog(catalogData)


  "A Catalog" should "offer a .cex seraliazation of the catalog" in pending


  "A CatalogEntry" should "offer .cex formatting" in {
    val entry = catalog.texts(0)

    val expected = s"""urn:cts:greekLit:tlg5026.msA.hmt:#book/scholion/part#Scholia to the Iliad#Main scholia of the Venetus A#HMT project edition##true"""
    assert(entry.cex("#") == expected)
  }


  //urn     citationScheme  groupName       workTitle       versionLabel    exemplarLabel   online


  "A Corpus" should "offer a .cex serialization of the corpus" in pending
  "A CitableNode" should "offer .cex formatting" in pending

  "A TextRepository" should "offer .cex serialization of the whole repository" in pending

}
