package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._


/**
*/
class CatalogSpec extends FlatSpec {

  "A catalog of citable nodes" should "offer a constructor signature for instantiating a corpus for a delimited text file" in {
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
  it  should "have a convenience method to get entries by URN" in {
    val srcFile = "src/test/resources/scholiacatalog.txt"
    val catalog = Catalog(srcFile)
    val mainScholiaUrn = CtsUrn("urn:cts:greekLit:tlg5026.msA:")
    val mainScholia = catalog.entriesForUrn(mainScholiaUrn)
    val documents = mainScholia.map(_.urn).distinct
    assert (documents.size == 1)
    assert (documents(0).urnMatch(mainScholiaUrn))
  }

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
  it should "have a non-empty string for citation scheme" in {
    val srcFile = "src/test/resources/scholiacatalog.txt"
    val catalog = Catalog(srcFile)
    for (entry <- catalog.texts) {
      assert (entry.citationScheme.nonEmpty)
    }
  }
  it should "have a non-empty string for group name " in {
    val srcFile = "src/test/resources/scholiacatalog.txt"
    val catalog = Catalog(srcFile)
    for (entry <- catalog.texts) {
      assert (entry.groupName.nonEmpty)
    }
  }
  it should "have a non-empty string for work title " in {
    val srcFile = "src/test/resources/scholiacatalog.txt"
    val catalog = Catalog(srcFile)
    for (entry <- catalog.texts) {
      assert (entry.workTitle.nonEmpty)
    }
  }
  it  should "have a non-empty string for version label " in {
    val srcFile = "src/test/resources/scholiacatalog.txt"
    val catalog = Catalog(srcFile)
    for (entry <- catalog.texts) {
      assert (entry.versionLabel.nonEmpty)
    }
  }

  it should "throw an Ohco2 error if an empty citation scheme is given" in {
    try {
      val urn  = CtsUrn("urn:cts:demo:g.w.ed:")
      CatalogEntry(urn,"","Demo texts","made up work","edition",None,false)
    } catch {
      case ex: IllegalArgumentException => assert(ex.getMessage() == "requirement failed: citation scheme cannot be empty")
    }
  }
  it should "throw an Ohco2 error if an empty group name is given" in {
    try {
      val urn  = CtsUrn("urn:cts:demo:g.w.ed:")
      CatalogEntry(urn,"book/chapter","","made up work","edition",None,false)
    } catch {
      case ex: IllegalArgumentException => assert(ex.getMessage() == "requirement failed: text group name cannot be empty")
    }
  }
  it should "throw an Ohco2 error if an empty work title is given" in {
    try {
      val urn  = CtsUrn("urn:cts:demo:g.w.ed:")
      CatalogEntry(urn,"book/chapter","Demo texts","","edition",None,false)
    } catch {
      case ex: IllegalArgumentException => assert(ex.getMessage() == "requirement failed: work title cannot be empty")
    }
  }
  it should "throw an Ohco2 error if an empty version label is given" in {
    try {
      val urn  = CtsUrn("urn:cts:demo:g.w.ed:")
      CatalogEntry(urn,"book/chapter","Demo texts","made up work","",None,false)
    } catch {
      case ex: IllegalArgumentException => assert(ex.getMessage() == "requirement failed: version label cannot be empty")
    }
  }

  it should "have a none option for exemplarLabel when the urn is a version-level URN " in {

    val urn  = CtsUrn("urn:cts:demo:g.w.ed:")
    val catalogEntry = CatalogEntry(urn,"book/chapter","Demo texts","made up work","edition",None,false)
    assert(catalogEntry.urn.workLevel == WorkLevel.Version)
    catalogEntry.exemplarLabel match {
      case None => assert(true)
      case _ => fail("Exemplar option must be None when URN is at version level")
    }
  }
  it should "have a non-empty option for exemplarLabel when the urn is an exemplar-level URN " in {
    val urn  = CtsUrn("urn:cts:demo:g.w.ed.exempl:")
    val catalogEntry = CatalogEntry(urn,"book/chapter","Demo texts","made up work","edition",Some("exemplar label"),false)
    assert(catalogEntry.urn.workLevel == WorkLevel.Exemplar)
    catalogEntry.exemplarLabel match {
      case None => fail("Exemplar option cannot be None when URN is at exemplar level")
      case _ => assert(true)
    }
  }

  it should "throw an Ohco2 exception if the URN is at a version level but an exemplar label is given" in {
    val urn  = CtsUrn("urn:cts:demo:g.w.ed:")
    try {
      val catalogEntry = CatalogEntry(urn,"book/chapter","Demo texts","made up work","edition",Some("exemplar label"),false)
      fail("Should not have created catalog entry")
    } catch {
      case oe: Ohco2Exception => assert(oe.message == "exemplar label must be none when URN is at version level")
      case e: Throwable => throw e
    }
  }
  it should "throw an Ohco2 exception if the URN is at an exemplar level but the exemplar option is none" in {
    val urn  = CtsUrn("urn:cts:demo:g.w.ed.exemplar:")
    try {
      val catalogEntry = CatalogEntry(urn,"book/chapter","Demo texts","made up work","edition",None,false)
      fail("Should not have created catalog entry")
    } catch {
      case oe: Ohco2Exception => assert(oe.message == "exemplar label cannot be none when URN is at exemplar level")
      case e: Throwable => throw e
    }
  }



}
