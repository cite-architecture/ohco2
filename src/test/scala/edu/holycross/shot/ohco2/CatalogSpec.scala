package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._


/**
*/
class CatalogSpec extends FlatSpec {

  "A catalog of citable texts" should "offer a constructor signature for instantiating a corpus for a delimited text file" in {
    val srcFile = "src/test/resources/scholiacatalog.txt"
    val catalog = Catalog(srcFile)
    catalog match {
      case c: Catalog => assert(true)
      case _ => fail("Failed to create Catalog object")
    }
  }
  it  should "have a non-empty vector of catalog entries" in {
    val srcFile = "src/test/resources/scholiacatalog.txt"
    val catalog = Catalog(srcFile)
    assert(catalog.texts.size > 0)
  }
  it  should "have a convenience method to get entries by URN" in pending

  "A catalog entry"  should "have a CtsUrn with a valid version identifier" in {
    val srcFile = "src/test/resources/scholiacatalog.txt"
    val catalog = Catalog(srcFile)
    for (entry <- catalog.texts) {
      entry.urn.versionOption match {
        case None => fail("Failed to find matching version")
        case _ => assert(true)
      }
    }
  }
  it should "have a non-empty string for citation scheme " in pending
  it should "have a non-empty string for group name " in pending
  it should "have a non-empty string for work title " in pending
  it  should "have a non-empty string for version label " in pending

  it should "throw an Ohco2 error if the URN does have a valid version identifier"
  it should "throw an Ohco2 error if an empty citation scheme is given"
  it should "throw an Ohco2 error if an empty group name is given"
  it should "throw an Ohco2 error if an empty work title is given"
  it should "throw an Ohco2 error if an empty version label is given"

  it should "have a none option for exemplarLabel when the urn is a version-level URN " in pending
  it should "have a non-empty string option for exemplarLabel when the urn is an exemplar-level URN " in pending

}
