package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._


/**
*/
class CatalogTocSpec extends FlatSpec {

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

  "A catalog of citable nodes" should "generate a toc" in pending

  it should "identify all text groups in the catalog by URN" in {
    val allGroups = catalog.groups
    assert(allGroups.size == 1)
    val expectedGroups = Set(CtsUrn("urn:cts:greekLit:tlg5026:"))
    assert(allGroups == expectedGroups)
  }

  it should "create a set of cataloged labelled groups" in {
    val allLabelledGroups = catalog.labelledGroups
    val expected = Set(
     LabelledCtsUrn(CtsUrn("urn:cts:greekLit:tlg5026:"), "Scholia to the Iliad")
    )
    assert (allLabelledGroups == expected)
  }



  it should "identify all works in the catalog by URN" in {
    val allWorks = catalog.works
    val expected =  Set(
      CtsUrn("urn:cts:greekLit:tlg5026.msAext:"), CtsUrn("urn:cts:greekLit:tlg5026.msAil:"), CtsUrn("urn:cts:greekLit:tlg5026.msAim:"), CtsUrn("urn:cts:greekLit:tlg5026.msAimlater:"), CtsUrn("urn:cts:greekLit:tlg5026.msA:"), CtsUrn("urn:cts:greekLit:tlg5026.msAint:")
    )
    assert(allWorks == expected)
  }

  it should "create a set of cataloged labelled works" in  {
    val allLabelledWorks = catalog.labelledWorks
    val expectedWorks = 6
    assert(allLabelledWorks.size == expectedWorks)
  }


  it should "identify all versions in the catalog by URN" in {
    val allVersions = catalog.versions
    val expected =  Set(
      CtsUrn("urn:cts:greekLit:tlg5026.msAext.hmt:"), CtsUrn("urn:cts:greekLit:tlg5026.msAil.hmt:"), CtsUrn("urn:cts:greekLit:tlg5026.msAim.hmt:"), CtsUrn("urn:cts:greekLit:tlg5026.msAimlater.hmt:"),CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:"), CtsUrn("urn:cts:greekLit:tlg5026.msAint.hmt:")
    )
    assert(allVersions == expected)
  }

  it should "create a set of cataloged labelled versions" in  {
    val allLabelledVersions = catalog.labelledVersions
    val expectedVersions = 6
    assert(allLabelledVersions.size == expectedVersions)
  }




  it should "identify all exemplars in the catalog by URN" in {
    val allExemplars = catalog.exemplars
    val expected =  Set(
      CtsUrn("urn:cts:greekLit:tlg5026.msAimlater.hmt.tokens:")
    )
    assert(allExemplars == expected)
  }

  it should "create a set of cataloged labelled exemplars" in  {
    val allLabelledExemplars = catalog.labelledExemplars
    val expected = Set(
      LabelledCtsUrn(CtsUrn("urn:cts:greekLit:tlg5026.msAimlater.hmt.tokens:"),"tokenized exemplar")
    )
    assert(allLabelledExemplars == expected)
  }

}
