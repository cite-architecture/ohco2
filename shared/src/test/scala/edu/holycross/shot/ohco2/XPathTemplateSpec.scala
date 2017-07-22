package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec

import scala.annotation.tailrec


class XPathTemplateSpec extends FlatSpec {


  val xptString = "/tei:TEI/tei:text/tei:body/tei:div[@n = '?']/tei:l[@n = '?']"



  "An XPathTemplate"  should "create a vector of local XML element names" in {
    val xpt = XPathTemplate(xptString)
    val expected = Vector("TEI", "text", "body", "div", "l")
    assert(xpt.localNames == expected)
  }

  it should "create a vector of possibly prefixed (qualified) XML element names" in {
      val xpt = XPathTemplate(xptString)
      val expected = Vector("tei:TEI", "tei:text", "tei:body", "tei:div", "tei:l")
      assert(xpt.qNames == expected)
  }

  it should "retrieve a citation value template for an index" in {
      val xpt = XPathTemplate(xptString)
      assert (xpt.keyForIndex(3) == "@n")
      assert (xpt.keyForIndex(4) == "@n")
  }

  it should "throw an exception if a citation template is requested for an invalid index" in {
    val xpt = XPathTemplate(xptString)
    try {
      xpt.keyForIndex(0)
    } catch {
      case e: Exception => assert(e.getMessage() == "XPathTemplate /tei:TEI/tei:text/tei:body/tei:div[@n = '?']/tei:l[@n = '?'] does not have a citation template at position 0.")
      case _ : Throwable => fail("Should have thrown exception")
    }
  }

it should "create a vector of integers indexing citation value templates" in {
    val xpt = XPathTemplate(xptString)
    val expected = Vector(3,4)
    assert (xpt.indexOfKeys(Vector.empty[Int]) == expected)
  }

  it should "determine if an expression at a given index contains a citation key" in {
    val xpt = XPathTemplate(xptString)
    assert(xpt.isKey(3))
    assert(xpt.isKey(1) == false)
  }

  "The XPathTemplate object" should "extract attribute expressions from an element" in {
    val s = "tei:l[@n = '?']"
    val expected = "@n"
    assert (XPathTemplate.citationKey(s) == expected)
  }

  it should "strip attribute templates from element expressions" in {
    val elem = "l[@n = '?']"
    assert(XPathTemplate.stripAttributes(elem) == "l")
  }


  it should "recognize a valid attribute expression" in {
    val goodTemplate = "@n = '?'"
    assert(XPathTemplate.validAttributeTemplate(goodTemplate))
  }

  it should "reject  expressions with invalid  attribute syntax" in {
    val bad = "@n = '?' = '2'"
    assert(XPathTemplate.validAttributeTemplate(bad) == false)
  }

  it should "reject attribute templates lacking template expression '?'" in {
    val bad = "@n = '2'"
    assert(XPathTemplate.validAttributeTemplate(bad) == false)
  }


  it should "extract the local name component from an element expression" in {
    val qualified = "tei:TEI"
    assert(XPathTemplate.localName(qualified ) == "TEI")
  }

  it should "extract unqualified local names unchanged" in {
    val localName = "TEI"
    assert(XPathTemplate.localName(localName ) == "TEI")
  }

  it should "omit any attribute expression" in {
    val withAttr = "tei:div[@n = '?']"
    assert(XPathTemplate.localName(withAttr ) == "div")

  }


}
