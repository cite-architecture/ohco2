package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class SimpleTabulatorSpec extends FlatSpec {


  // URN#TYPE#DOCUMENT#NSMAP#XPathTemplate
  val singleSpec = "urn:cts:greekLit:tlg0012.tlg001.msA:#xml#VenetusA/VenetusA-Iliad.xml#tei->http://www.tei-c.org/ns/1.0#/tei:TEI/tei:text/tei:body/tei:div[@n = '?']/tei:l[@n = '?']"


  val urn = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.madeUpVersion:")
  val spec = "/tei:TEI/tei:text/tei:body/tei:div[@n = '?']/tei:l[@n = '?']"
  val xpt = XPathTemplate(spec)
  val xml = """<?xml version="1.0" encoding="UTF-8"?>
<TEI  xmlns="http://www.tei-c.org/ns/1.0"
xmlns:tei="http://www.tei-c.org/ns/1.0">
<junkToIgnore>Like teiHeader...</junkToIgnore>
<text>
<body>
<div n="24">
<l n="611">So they buried Hector, tamer of horses.</l>
<l n="612">An Amazon came</l>
</div>
</body>
</text>
</TEI>
"""

  "The SimpleTabulator object"  should "collect citable nodes from a configured XML source" in {
    val root = scala.xml.XML.loadString(xml)
    val citableNodes = SimpleTabulator.collectCitableNodes(urn,root,0,xpt)
    assert(citableNodes.size == 2)
  }

  "A SimpleTabulator"  should "create a Corpus object from a configured XML source" in {
    val corpus = SimpleTabulator(urn, xpt, xml)
    corpus match {
      case c: Corpus => assert(c.size == 2)
      case _ => fail("Should have created a Corpus object")
    }
  }


}
