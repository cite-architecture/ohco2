package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class OnlineDocumentObjectSpec extends FlatSpec {




    "The OnlineDocument object"  should "create an OnlineDocument instance from a delimited-text line" in {
    val oneEntry = "urn:cts:greekLit:tlg0016.tlg001.grc:#xml#test-hdt-grc.xml#tei->http://www.tei-c.org/ns/1.0#/tei:TEI/tei:text/tei:body/tei:div[@n = '?']/tei:div[@n = '?']"
    val online = OnlineDocument(oneEntry,"#", ",")
    online match {
      case od: OnlineDocument => assert(true)
      case _ => fail("Failed to create OnlineDocument object")
    }
  }

  it should "map a string of namespace mappings to a scala map structure" in {
    val delimited = "tei->http://www.tei-c.org/ns/1.0"
    val mapped = OnlineDocument.namespacesFromString(delimited).get
    assert (mapped("tei") == "http://www.tei-c.org/ns/1.0")
  }

  it should "throw an Exception if an invalid value for format is given" in {
    val badFormat = "urn:cts:greekLit:tlg0016.tlg001.grc:#ill-formed xml#test-hdt-grc.xml#tei->http://www.tei-c.org/ns/1.0#/tei:TEI/tei:text/tei:body/tei:div[@n = '?']/tei:div[@n = '?']"

    try {
      val onlineDoc = OnlineDocument(badFormat,"#",",")
      fail("Should not have created document.")
    } catch {
      case oe: Ohco2Exception => assert(oe.message == "Bad value for format: ill-formed xml")
      case t: Throwable => fail("Should have thrown Ohco2Exception instead of " + t)
    }

  }

  it should "throw an Exception if an invalid document URN" in {
    val badUrn = "NOT_A_URN"
    val badCex = s"${badUrn}#xml#test-hdt-grc.xml#tei->http://www.tei-c.org/ns/1.0#/tei:TEI/tei:text/tei:body/tei:div[@n = '?']/tei:div[@n = '?']"

    try {
      val onlineDoc = OnlineDocument(badUrn,"#",",")
      fail("Should not have created document.")
    } catch {
      case e: java.lang.Exception => assert (e.getMessage().contains(badUrn))
    }

  }

  it should "ensure that XML documents have a citation template" in {
    val noXPtemplate = "urn:cts:greekLit:tlg0016.tlg001.grc:#xml#test-hdt-grc.xml#tei->http://www.tei-c.org/ns/1.0#"

      try {
        val onlineDoc = OnlineDocument(noXPtemplate,"#",",")
        fail("Should not have created document.")
      } catch {
        case iae: IllegalArgumentException => assert (iae.getMessage() ==  "requirement failed: OnlineDocument: XML configuration must include XPathTemplate")
        case t: Throwable => fail("Should have thrown IllegalArgumentException instead of " + t)
      }

  }

  it should "ensure that only XML documents can have a citation template" in {
    val illegitimateXPath = "urn:cts:greekLit:tlg0016.tlg001.grc:#markdown#test-hdt.md##/tei:TEI/tei:text/tei:body/tei:div[@n = '?']/tei:div[@n = '?']"
    try {
      val onlineDoc = OnlineDocument(illegitimateXPath,"#",",")
      fail("Should not have created document.")
    } catch {
      case iae: IllegalArgumentException => assert (iae.getMessage() ==  "requirement failed: OnlineDocument format Markdown may not include XPathTemplate or XML namespaces")
      case t: Throwable => fail("Should have thrown IllegalArgumentException instead of " + t)
    }
  }

  it should "ensure that only XML documents can have a namespace mapping" in {
    val illegitimateNS = "urn:cts:greekLit:tlg0016.tlg001.grc:#markdown#test-hdt.md#tei->http://tei.org/something#"

    try {
      val onlineDoc = OnlineDocument(illegitimateNS,"#",",")
      fail("Should not have created document.")
    } catch {
      case iae: IllegalArgumentException => assert (iae.getMessage() ==  "requirement failed: OnlineDocument format Markdown may not include XPathTemplate or XML namespaces")
      case t: Throwable => fail("Should have thrown IllegalArgumentException instead of " + t)
    }
  }



}
