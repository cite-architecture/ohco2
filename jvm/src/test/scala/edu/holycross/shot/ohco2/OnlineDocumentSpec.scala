package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._

import scala.xml._
/**
*/
class OnlineDocumentSpec extends FlatSpec {


/*
  val citeConfXml = """<CitationConfiguration xmlns="http://chs.harvard.edu/xmlns/hocuspocus" tiversion="6.0.0">
    <online        urn="urn:cts:greekLit:tlg0016.tlg001.grcTest:"        type="xml"        docname="test-hdt-grc.xml">
      <namespaceMapping abbreviation="tei" nsURI="http://www.tei-c.org/ns/1.0"/>
      <citationMapping>
        <citation label="Book" scope="/tei:TEI/tei:text/tei:body" xpath="/tei:div[@type='book' and @n='?']">
          <citation label="Chapter" scope="/tei:TEI/tei:text/tei:body/tei:div[@type='book' and @n='?']"                    xpath="/tei:div[@type='chapter' and @n='?']"/>
        </citation>
      </citationMapping>
    </online>
    <online        urn="urn:cts:greekLit:tlg0016.tlg001.engTest:"        type="xml"        docname="test-hdt-grc.xml">
      <namespaceMapping abbreviation="tei" nsURI="http://www.tei-c.org/ns/1.0"/>
      <citationMapping>
        <citation label="Book" scope="/tei:TEI/tei:text/tei:body" xpath="/tei:div[@type='book' and @n='?']">
          <citation label="Chapter" scope="/tei:TEI/tei:text/tei:body/tei:div[@type='book' and @n='?']"                    xpath="/tei:div[@type='chapter' and @n='?']"/>
        </citation>
      </citationMapping>
    </online>
  </CitationConfiguration>
  """
*/
  val cexSpec = "urn:cts:greekLit:tlg0016.tlg001.grcTest::#xml#test-hdt-grc.xml#tei->http://www.tei-c.org/ns/1.0#/tei:TEI/tei:text/tei:body/tei:div[@n = '?']/tei:div[@n = '?']"



  "A TextRepositorySource" should "recognize  OnlineDocuments from a delimited-text citation configuration" in  pending  /*{



    val root = XML.loadString(citeConfXml)
    val online = TextRepositorySource.onlineDocsFromXml(root)
    assert(online.size == 2)
  }
*/

  it should "be able to twiddle the resulting vector" in  pending /*{
    val root = XML.loadString(citeConfXml)
    val online = TextRepositorySource.onlineDocsFromXml(root)

    val hdt = online.filter(_.urn ~~ CtsUrn("urn:cts:greekLit:tlg0016.tlg001:"))
    val eng = online.filter(_.urn ~~ CtsUrn("urn:cts:greekLit:tlg0016.tlg001.engTest:"))

    assert (hdt.size == 2)
    assert (eng.size == 1)
  }*/

}
