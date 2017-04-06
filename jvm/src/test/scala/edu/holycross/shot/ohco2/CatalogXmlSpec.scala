package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._


/**
*/
class CatalogXmlSpec extends FlatSpec {


  val catalogXml= """<TextInventory  tiid="hmt"  tiversion="6.0.0"  xmlns="http://chs.harvard.edu/xmlns/cts"  xmlns:dc="http://purl.org/dc/elements/1.1/">
    <ctsnamespace    abbr="greekLit"    ns="http://chs.harvard.edu/ctsns/greekLit">
      <description      xml:lang="eng"> The &quot;First Thousand Years of Greek&quot; project's inventory of Greek      texts. </description>
    </ctsnamespace>
    <ctsnamespace    abbr="test1"    ns="http://dev/null">
      <description      xml:lang="eng"> Namespace for made up test works.</description>
    </ctsnamespace>
    <textgroup urn="urn:cts:greekLit:tlg0016:">
      <groupname xml:lang="eng">Herodotus</groupname>
      <work urn="urn:cts:greekLit:tlg0016.tlg001:" xml:lang="grc">
        <title xml:lang="eng">Histories</title>
        <edition urn="urn:cts:greekLit:tlg0016.tlg001.grcTest:">
          <label xml:lang="eng">Greek. For testing</label>
          <description xml:lang="eng">3 books, 10 sections.</description>
          <online/>
      </edition>
    </work>
  </textgroup>
  </TextInventory>
"""
  val citeConfXml = """<CitationConfiguration xmlns="http://chs.harvard.edu/xmlns/hocuspocus" tiversion="6.0.0">
    <online        urn="urn:cts:greekLit:tlg0016.tlg001.grcTest:"        type="xml"        docname="test-hdt-grc.xml">
      <namespaceMapping abbreviation="tei" nsURI="http://www.tei-c.org/ns/1.0"/>
      <citationMapping>
        <citation label="Book" scope="/tei:TEI/tei:text/tei:body" xpath="/tei:div[@type='book' and @n='?']">
          <citation label="Chapter" scope="/tei:TEI/tei:text/tei:body/tei:div[@type='book' and @n='?']"                    xpath="/tei:div[@type='chapter' and @n='?']"/>
        </citation>
      </citationMapping>
    </online>
  </CitationConfiguration>
"""


  val catalog = TextRepositorySource.catalogFromXml(catalogXml, citeConfXml)

  "A catalog of citable nodes" should "be instantiated from an XML string" in  {
    assert(catalog.size > 0)
  }



}
