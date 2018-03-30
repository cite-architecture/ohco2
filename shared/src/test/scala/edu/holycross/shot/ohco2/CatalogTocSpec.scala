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



}
