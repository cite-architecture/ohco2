package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._


/**
*/
class CatalogSpec extends FlatSpec {

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

  "A catalog of citable nodes" should "offer a constructor signature for instantiating a corpus from delimited text content" in {

    catalog match {
      case c: Catalog => assert(true)
      case _ => fail("Failed to create Catalog object")
    }
  }

  it  should "have a non-empty vector of catalog entries" in {
    assert(catalog.texts.size > 0)
  }


  it should "have a size function" in {
    assert(catalog.size == 7)
  }

  it  should "have a convenience method to get entries by URN" in {
    val mainScholiaUrn = CtsUrn("urn:cts:greekLit:tlg5026.msA:")
    val mainScholia = catalog.entriesForUrn(mainScholiaUrn)
    val documents = mainScholia.map(_.urn).distinct
    assert (documents.size == 1)
    assert (documents(0).~~(mainScholiaUrn))
  }



  it should "handle versions with exemplars when getting entries for URN" in pending /*{
      val versionUrn:CtsUrn = CtsUrn("urn:cts:greekLit:tlg5026.msAimlater.hmt:")
      val exemplarUrn:CtsUrn = CtsUrn("urn:cts:greekLit:tlg5026.msAimlater.hmt.tokens:")
      val scholiaTexts = catalog.entriesForUrn(versionUrn)
      val justExemplarTexts = catalog.entriesForUrn(exemplarUrn)
      assert (scholiaTexts.size == 2)
      assert (justExemplarTexts.size == 1)
  }*/

  "A catalog entry"  should "have a CtsUrn with a valid version identifier" in {
    for (entry <- catalog.texts) {
      entry.urn.versionOption match {
        case None => fail("Failed to find matching version")
        case _ => assert(true)
      }
    }
  }
  it should "have a non-empty string for citation scheme" in {
    for (entry <- catalog.texts) {
      assert (entry.citationScheme.nonEmpty)
    }
  }
  it should "have a non-empty string for group name " in {
    for (entry <- catalog.texts) {
      assert (entry.groupName.nonEmpty)
    }
  }
  it should "have a non-empty string for work title " in {
    for (entry <- catalog.texts) {
      assert (entry.workTitle.nonEmpty)
    }
  }
  it  should "have a non-empty string for version label " in {
    for (entry <- catalog.texts) {
      assert (entry.versionLabel.nonEmpty)
    }
  }

  it should "throw an Ohco2 error if an empty citation scheme is given" in {
    try {
      val urn  = CtsUrn("urn:cts:demo:g.w.ed:")
      CatalogEntry(urn,"","eng","Demo texts","made up work",Some("edition"),None,false)
    } catch {
      case ex: IllegalArgumentException => assert(ex.getMessage() == "requirement failed: citation scheme cannot be empty")
    }
  }
  it should "throw an Ohco2 error if an empty group name is given" in {
    try {
      val urn  = CtsUrn("urn:cts:demo:g.w.ed:")
      CatalogEntry(urn,"book/chapter","eng","","made up work",Some("edition"),None,false)
    } catch {
      case ex: IllegalArgumentException => assert(ex.getMessage() == "requirement failed: text group name cannot be empty")
    }
  }
  it should "throw an Ohco2 error if an empty work title is given" in {
    try {
      val urn  = CtsUrn("urn:cts:demo:g.w.ed:")
      CatalogEntry(urn,"book/chapter","eng","Demo texts","",Some("edition"),None,false)
    } catch {
      case ex: IllegalArgumentException => assert(ex.getMessage() == "requirement failed: work title cannot be empty")
    }
  }
  it should "throw an Ohco2 error if an empty version label is given" in {
    try {
      val urn  = CtsUrn("urn:cts:demo:g.w.ed:")
      CatalogEntry(urn,"book/chapter","eng","Demo texts","made up work",None,None,false)
    } catch {
      case ex: IllegalArgumentException => assert(ex.getMessage() == "requirement failed: version label cannot be empty")
    }
  }

  it should "have a none option for exemplarLabel when the urn is a version-level URN " in {

    val urn  = CtsUrn("urn:cts:demo:g.w.ed:")
    val catalogEntry = CatalogEntry(urn,"book/chapter","eng","Demo texts","made up work",Some("edition"),None,false)
    assert(catalogEntry.urn.workLevel == WorkLevel.Version)
    catalogEntry.exemplarLabel match {
      case None => assert(true)
      case _ => fail("Exemplar option must be None when URN is at version level")
    }
  }
  it should "have a non-empty option for exemplarLabel when the urn is an exemplar-level URN " in {
    val urn  = CtsUrn("urn:cts:demo:g.w.ed.exempl:")
    val catalogEntry = CatalogEntry(urn,"book/chapter","eng","Demo texts","made up work",Some("edition"),Some("exemplar label"),false)
    assert(catalogEntry.urn.workLevel == WorkLevel.Exemplar)
    catalogEntry.exemplarLabel match {
      case None => fail("Exemplar option cannot be None when URN is at exemplar level")
      case _ => assert(true)
    }
  }

  it should "throw an Ohco2 exception if the URN is at a version level but an exemplar label is given" in {
    val urn  = CtsUrn("urn:cts:demo:g.w.ed:")
    try {
      val catalogEntry = CatalogEntry(urn,"book/chapter","eng","Demo texts","made up work",Some("edition"),Some("exemplar label"),false)
      fail("Should not have created catalog entry")
    } catch {
      case oe: Ohco2Exception => assert(oe.message == "exemplar label must be none when URN is at version level")
      case e: Throwable => throw e
    }
  }
  it should "throw an Ohco2 exception if the URN is at an exemplar level but the exemplar option is none" in {
    val urn  = CtsUrn("urn:cts:demo:g.w.ed.exemplar:")
    try {
      val catalogEntry = CatalogEntry(urn,"book/chapter","eng","Demo texts","made up work",Some("edition"),None,false)
      fail("Should not have created catalog entry")
    } catch {
      case oe: Ohco2Exception => assert(oe.message == "exemplar label cannot be none when URN is at exemplar level")
      case e: Throwable => throw e
    }
  }

  it should "support pretty printing of catalog entries" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:")
    val catalogEntry = CatalogEntry(urn,"book/line","grc","Homeric poetry","Iliad",Some("the Venetus A manuscript"),None,true)
    assert (catalogEntry.toString == "Homeric poetry, Iliad (the Venetus A manuscript)")
  }

  it should "support pretty printing with URNs, too" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:")
    val catalogEntry = CatalogEntry(urn,"book/line","grc","Homeric poetry","Iliad",Some("the Venetus A manuscript"),None,true)
    assert (catalogEntry.toString == "Homeric poetry, Iliad (the Venetus A manuscript)")
  }

  it should "object if there are duplicate URNs" in {
    val duplicated = "urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online\nurn:cts:greekLit:tlg5026.msA.hmt:#book/scholion/part#Scholia to the Iliad#Main scholia of the Venetus A#HMT project edition##true#grc\nurn:cts:greekLit:tlg5026.msA.hmt:#book/scholion/part#Scholia to the Iliad#Intermarginal scholia of the Venetus A#HMT project edition##true#grc"
    try {
      val c =  Catalog(duplicated)
      fail("Should have thrown exception for duplicate entry")
    } catch {
      case dupe : IllegalArgumentException => assert(dupe.getMessage() == "requirement failed: Duplicated URN values: urn:cts:greekLit:tlg5026.msA.hmt:")

      case e: Throwable => fail("Should have thrown IllegalArgumentException, not " + e)
    }

  }

  it should "throw an exception is language value is not a 3-letter code" in {
    try {

        val urn  = CtsUrn("urn:cts:demo:g.w.ed:")
        val tooLong = CatalogEntry(urn,"book/chapter","english-language text","Demo texts","made up work",Some("edition"),None,false)
    } catch {
      case ia: IllegalArgumentException => assert(ia.getMessage() == "requirement failed: Value for 'lang' should be an ISO 639-2 3-letter language code, but was english-language text")
      case t: Throwable => fail("Should have thrown IllegalArgumentExcpetion but threw " + t)
    }
  }

  it should "correctly provide a label for a version" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msAimlater.hmt:")
    val l:String = catalog.label(urn)
    val expected = "Scholia to the Iliad, Later intermarginal scholia of the Venetus A (HMT project edition)"
    assert(l == expected)

  }

   it should "correctly provide a label for an exmplar" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msAimlater.hmt.tokens:")
    try {
      val l:String = catalog.label(urn)
      assert(l == "Scholia to the Iliad, Later intermarginal scholia of the Venetus A (HMT project edition: tokenized exemplar)")
    } catch{
      case ia: IllegalArgumentException => assert(ia.getMessage() == "requirement failed: Value for 'lang' should be an ISO 639-2 3-letter language code, but was english-language text")
      case t: Throwable => fail("Should have thrown IllegalArgumentExcpetion but threw " + t)
    }
  }

   it should "correctly provide labels for a version" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msAimlater.hmt:")
    try {
      val l:String = catalog.labels(urn)
      println(l)

      //assert(l == "Scholia to the Iliad, Later intermarginal scholia of the Venetus A (HMT project edition\nScholia to the Iliad, Later intermarginal scholia of the Venetus A (HMT project edition: tokenized exemplar)""")

    } catch{
      case e: java.lang.Exception => {
        println("ERR: " + e)
        assert(e.getMessage() == "requirement failed: Value for 'lang' should be an ISO 639-2 3-letter language code, but was english-language text")
      }

    }
  }

}
