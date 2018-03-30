package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._


/**
*/
class CatalogLabellingSpec extends FlatSpec {

  val catalogData = """urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online#lang
urn:cts:greekLit:tlg5026.msA.hmt:#book/scholion/part#Scholia to the Iliad#Main scholia of the Venetus A#HMT project edition##true#grc
urn:cts:greekLit:tlg5026.msAim.hmt:#book/scholion/part#Scholia to the Iliad#Intermarginal scholia of the Venetus A#HMT project edition##true#grc
urn:cts:greekLit:tlg5026.msAint.hmt:#book/scholion/part#Scholia to the Iliad#Interior scholia of the Venetus A#HMT project edition##true#grc
urn:cts:greekLit:tlg5026.msAext.hmt:#book/scholion/part#Scholia to the Iliad#Exterior scholia of the Venetus A#HMT project edition##true#grc
urn:cts:greekLit:tlg5026.msAil.hmt:#book/scholion/part#Scholia to the Iliad#Interlinear scholia of the Venetus A#HMT project edition##true#grc
urn:cts:greekLit:tlg5026.msAimlater.hmt:#book/scholion/part#Scholia to the Iliad#Later intermarginal scholia of the Venetus A#HMT project edition##true#grc
urn:cts:greekLit:tlg5026.msAimlater.hmt.tokens:#book/scholion/part/token#Scholia to the Iliad#Later intermarginal scholia of the Venetus A#HMT project edition#tokenized exemplar#true#grc
"""
  val catalog = Catalog(catalogData)

  "A catalog of citable nodes" should "find a text group label for a given URN" in {
    val passage = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1")
    val expectedName = "Scholia to the Iliad"
    assert(catalog.groupName(passage) == expectedName)
  }


  it should "throw an exception if the URN is not in the catalog" in {
    val notHere = CtsUrn("urn:cts:greekLit:tlg5026.msA.SOMEBODY_ELSES_EDITION:1.1")
    try {
      catalog.groupName(notHere)
      fail("Should not have found matching URN for " + notHere)
    } catch {
      case t: Throwable => {
        assert (t.toString == "java.lang.Exception: ohco2.Catalog:  no urns matching urn:cts:greekLit:tlg5026.msA.SOMEBODY_ELSES_EDITION:1.1 found.")
      }
    }
  }

  it should "find a work title for a given URN" in {
    val passage = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1")
    val expectedTitle = "Main scholia of the Venetus A"
    assert(catalog.workTitle(passage) == expectedTitle)
  }

  it should "return an empty string if the URN is in the catalog but has no work"

  it should "find a version label for a given URN" in {
    val passage = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1")
    val expectedLabel = "HMT project edition"
    assert(catalog.versionLabel(passage) == expectedLabel)
  }

  it should "return an empty string if the URN is in the catalog but has no version"


  it should "find an exemplar label for a given URN" in {
    val passage = CtsUrn("urn:cts:greekLit:tlg5026.msAimlater.hmt.tokens:1.1.1.1")
    val expectedLabel = "tokenized exemplar"
    assert(catalog.exemplarLabel(passage) == expectedLabel)
  }
  it should "return an empty string if the URN is in the catalog but has no exemplar" in {
    val passage = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1")
    assert(catalog.exemplarLabel(passage) == "")
  }



}
