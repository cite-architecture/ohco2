package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._
import scala.io.Source
import java.net.URL


class CorpusSpec extends FlatSpec {


  val scholiaDelimited = """urn:cts:greekLit:tlg5026.msA.hmt:1.1.lemma#<div xmlns="http://www.tei-c.org/ns/1.0" n="lemma"> <p> μῆνιν ἄειδε</p></div>
urn:cts:greekLit:tlg5026.msA.hmt:1.1.comment#<div xmlns="http://www.tei-c.org/ns/1.0" n="comment"> <p> ζητοῦσι δια τί ἀπὸ τῆς μήνιδος ἤρξατο οὕτως δυσφήμου ὀνόματος· δια δύο ταῦτα· πρῶτον μὲν ἵν' ἐκ τοῦ πάθους ἀπκαταῥρεύσῃ τὸ τοιοῦτο μόριον τῆς ψυχῆς καὶ προσεκτικωτέρους τοὺς ἀκροατὰς ἐπι τοῦ μεγέθους ποιήσῃ καὶ προσεθίζῃ φέρειν γενναίως ἡμᾶς τὰ πάθη. μέλλων πολλέμους ἀπαγγέλλειν· δεύτερον. ἵνα τὰ ἐγκώμια τῶν Ἑλλήνων πιθανώτερα ποιήσῃ· ἐπεὶ δὲ ἔμελλε, νικωντας ἀποφαίνειν τοὺς Ἑλληνας, εἰκότως οὐ κατατρέχει ἀξιοπιστότερον ἐκ τοῦ μὴ παντα χαρίζεσθαι τῷ εκείνων ἐπαίνῳ· ἤρξατο μὲν ἀπὸ μήνιδος ἐπείπερ αὕτη τοῖς πρακτικοῖς ὑπόθεσις γέγονεν· ἄλλῳς τε καὶ τραγῳδίαις τραγικὸν ἐξεῦρε προοίμιον· καὶ γὰρ προσεκτικοὺς ἡμᾶς ἡ τῶν ἀτυχημάτων διήγησις ἐργάζεται· καὶ ὡς ἄριστος ϊατρὸς, πρῶτον ἀναστέλλων τὰ νοσήματα τῆς ψυχῆς ὕστερον τὴν ΐασιν ἐπάγει. <placeName n="urn:cite:hmt:place.place3"> Ἑλληνικὸν</placeName> δὲ τὸ προ τέλει τὰς ηδονὰς ἐπάγειν· ῾ϊστέον δέ, ὥσπερ ἐπι συκῆς πρωτον μέν ἐστιν όλυνθος εἶτα φίλιξ σύκον ϊσχάς. οὕτω πρῶτον· <rs type="waw"> ὀργή</rs> · <rs type="waw"> θυμός</rs> · <rs type="waw"> χόλος</rs> · <rs type="waw"> κότος</rs> · <rs type="waw"> μῆνις</rs> · ὅμως ὁ ποιητὴς ὡς συνωνύμοις ὀνόμασιν ἐπὶ <persName n="urn:cite:hmt:pers.pers1"> Ἀχιλλεως</persName> χρῆται <cit> <q> ἢἐ <seg type="word"> χόλ <unclear> ο</unclear> ν</seg> παύσειεν, ἐρητύσειε τε θυμόν</q> <ref type="urn"> urn:cts:greekLit:tlg0012.tlg001:1.192</ref></cit> · <cit> <q> οὐδ' ὄθομαι κοτέοντος</q> <ref type="urn"> urn:cts:greekLit:tlg0012.tlg001:1.181</ref></cit> , <cit> <q> αὐτὰρ ὁ μήνιε νηυσίν</q> <ref type="urn"> urn:cts:greekLit:tlg0012.tlg001:1.488</ref></cit> ·</p></div>
urn:cts:greekLit:tlg5026.msA.hmt:1.2.lemma#<div xmlns="http://www.tei-c.org/ns/1.0" n="lemma"> <p> μῆνις</p></div>
urn:cts:greekLit:tlg5026.msA.hmt:1.2.comment#<div xmlns="http://www.tei-c.org/ns/1.0" n="comment"> <p> παρὰ τὸ <rs type="waw"> μένω</rs> <rs type="waw"> μῆνις</rs> ὡς <rs type="waw"> ἐνὸς</rs> <rs type="waw"> ἦνις</rs> · οἱ δὲ περὶ <persName n="urn:cite:hmt:pers.pers3"> Γλαύκωνα</persName> τὸν <placeName n="urn:cite:hmt:place.place47"> <seg type="word"> Ταρ <unclear> σέα</unclear></seg></placeName> ἠξίουν ὀξύνειν τὸ ὄνομα οὐκ ὀρθῶς.</p></div>
urn:cts:greekLit:tlg5026.msA.hmt:1.3.lemma#<div xmlns="http://www.tei-c.org/ns/1.0" n="lemma"> <p> ἄειδε</p></div>
urn:cts:greekLit:tlg5026.msA.hmt:1.3.comment#<div xmlns="http://www.tei-c.org/ns/1.0" n="comment"> <p> ὅτι κατὰ τὴν ποιητικὴν ἤτοι άδειαν ἠ συνήθειαν λαμβάνει τὰ προστακτικὰ ἀντι εὐκτικῶν· καὶ γὰρ <persName n="urn:cite:hmt:pers.pers4"> Ἡσίοδός</persName> φησι· <q> δεῦτε δὴ ἐννέπετε <note resp="Erbse"> opp.2</note></q> , καὶ <persName n="urn:cite:hmt:pers.pers5"> Πίνδαρος</persName> · <q> μαντεύο <persName n="urn:cite:hmt:pers.pers6"> Μοῦσα</persName> <note resp="Erbse"> fr. 150 sn. </note></q> , καὶ <persName n="urn:cite:hmt:pers.pers7"> Ἀντίμαχος</persName> ὁ <placeName n="urn:cite:hmt:place.place54"> <seg type="word"> Κολοφ <unclear> ώνιος.</unclear></seg></placeName> <q> ἐννέπετε <persName n="urn:cite:hmt:pers.pers8"> <seg type="word"> Κρονί <unclear> ανος</unclear></seg> Διὸς </persName> μεγάλοιο θύγατρες <note resp="Erbse"> fr. 1 w</note></q> · δευτερον δὲ ὅτι οὐ κατὰ ἀλήθειαν ταῖς <persName n="urn:cite:hmt:pers.pers6"> Μούσαις</persName> ἐπιτάσσουσιν, ἀλλ' ἑαυτοῖς⁑</p></div>
urn:cts:greekLit:tlg5026.msA.hmt:1.4.lemma#<div xmlns="http://www.tei-c.org/ns/1.0" n="lemma"> <p> θεά</p></div>
urn:cts:greekLit:tlg5026.msA.hmt:1.4.comment#<div xmlns="http://www.tei-c.org/ns/1.0" n="comment"> <p> οὕτως εἴωθε τὴν <persName n="urn:cite:hmt:pers.pers6"> Μοῦσαν</persName> καλεῖν· ἀμέλει καὶ ἐν <title> Ὀδυσσεία</title> · <cit> <q> ἄνδρα μοι ἔννεπε <persName n="urn:cite:hmt:pers.pers6"> Μοῦσα</persName> <ref type="urn"> urn:cts:greekLit:tlg0012.tlg002:1.1</ref></q></cit> ⁑</p></div>
urn:cts:greekLit:tlg5026.msA.hmt:1.5.lemma#<div xmlns="http://www.tei-c.org/ns/1.0" n="lemma"> <p> <persName n="urn:cite:hmt:pers.pers1"> Πηληϊάδεω</persName> <persName n="urn:cite:hmt:pers.pers1"> Ἀχιλῆος</persName></p></div>
urn:cts:greekLit:tlg5026.msA.hmt:1.5.comment#<div xmlns="http://www.tei-c.org/ns/1.0" n="comment"> <p> οὕτως ἀναγνωστεον δια τοῦ ενὸς <rs type="waw"> λ</rs> καὶ διὰ τὸ μέτρον καὶ διὰ τὸ ἄχος ὅ ἐστι λύπην ἐπενεγκεῖν τοῖς Ἰλιεῦσιν, οἱ δὲ παρὰ τὸ μὴ θιγεῖν χείλεσι θηλῆς ὅλως γὰρ οὐ μετέσχε γάλακτος⁑</p></div>
"""


  val corpus = Corpus(scholiaDelimited,"#")

  "A corpus of citable nodes"  should "offer a constructor signature for  a corpus from a string value for a URL identifying a 2-column delimited text source" in  {
    corpus match {
      case c: Corpus => assert(true)
      case _ => fail("Failed to create Corpus object")
    }
  }


  it should "have a non-empty vector of citable nodes" in {
    assert(corpus.nodes.size > 0)
  }


  // filtering on single node URNS:
  it should "filter the corpus contents against a URN with matching work and passage hierarchies" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1.lemma")
    val filtered = corpus.urnMatch(urn)
    // result should be a single node with same URN:
    assert (filtered.size == 1)
    assert(filtered(0).urn == urn)
  }

  it should "filter the corpus contents against a  URN with matching work hierarchy and containing passage hierarchy" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1")
    val filtered = corpus.urnMatch(urn)
    // result should be two nodes with:
    assert (filtered.size == 2)
    // add test on trimmed version of URN...
  }

  it should "filter the corpus contents against a URN with containing work hierarchy and matching passage hierarchy" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.1.lemma")
    val filtered = corpus.urnMatch(urn)
    assert (filtered.size == 1)
    // urns differ only in version:
    assert (filtered(0).urn.textGroup == urn.textGroup)
    assert (filtered(0).urn.work == urn.work)
    assert (filtered(0).urn.passageComponent == urn.passageComponent)
  }


  it should "filter the corpus contents against a URN with containing work hierarchy and containing passage hierarchy" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.1")
    val filtered = corpus.urnMatch(urn)
    assert (filtered.size == 2)
    for (txtnode <- filtered) {
      assert (txtnode.urn.textGroup == urn.textGroup)
      assert (txtnode.urn.work == urn.work)
    }
  }

  it should "return an empty vector if the filtering URN does not appear in the corpus" in {
      val urn = CtsUrn("urn:cts:fake:group:none")
      val filtered = corpus.urnMatch(urn)
      assert(filtered.isEmpty)
  }

  // filtering on range URNS:
  it should "filter the corpus contents against a range URN with matching work hierarchy and matching range end points" in  {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1.lemma-1.2.comment")
    val filtered = corpus.urnMatch(urn)
    assert (filtered.size == 4)
    for (txtnode <- filtered) {
      assert (txtnode.urn.textGroup == urn.textGroup)
      assert (txtnode.urn.work == urn.work)
      assert (txtnode.urn.version == urn.version)
    }
  }

  it should "filter the corpus contents against a range URN with matching work hierarchy and containing range beginning point" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1-1.2.comment")
    val filtered = corpus.urnMatch(urn)
    assert (filtered.size == 4)
    for (txtnode <- filtered) {
      assert (txtnode.urn.textGroup == urn.textGroup)
      assert (txtnode.urn.work == urn.work)
      assert (txtnode.urn.version == urn.version)
    }
  }

  it should "filter the corpus contents against a range URN with matching work hierarchy and containing range ending point" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1.lemma-1.2")
    val filtered = corpus.urnMatch(urn)
    assert (filtered.size == 4)
    for (txtnode <- filtered) {
      assert (txtnode.urn.textGroup == urn.textGroup)
      assert (txtnode.urn.work == urn.work)
      assert (txtnode.urn.version == urn.version)
    }
  }

  it should "filter the corpus contents against a range URN with matching work hierarchy and containing points for range beginning and ending" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1-1.2")
    val filtered = corpus.urnMatch(urn)
    assert (filtered.size == 4)
    for (txtnode <- filtered) {
      assert (txtnode.urn.textGroup == urn.textGroup)
      assert (txtnode.urn.work == urn.work)
      assert (txtnode.urn.version == urn.version)
    }
  }

  it should "filter the corpus contents against a range URN with containing work hierarchy and matching range end points" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.1.lemma-1.2.comment")
    val filtered = corpus.urnMatch(urn)
    assert (filtered.size == 4)
    for (txtnode <- filtered) {
      assert (txtnode.urn.textGroup == urn.textGroup)
      assert (txtnode.urn.work == urn.work)
    }
  }

  it should "filter the corpus contents against a range URN with containing work hierarchy and containing range beginning point" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.1-1.2.comment")
    val filtered = corpus.urnMatch(urn)
    assert (filtered.size == 4)
    for (txtnode <- filtered) {
      assert (txtnode.urn.textGroup == urn.textGroup)
      assert (txtnode.urn.work == urn.work)
    }
  }

  it should "filter the corpus contents against a range URN with containing work hierarchy and containing range ending point" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.1.lemma-1.2")
    val filtered = corpus.urnMatch(urn)
    assert (filtered.size == 4)
    for (txtnode <- filtered) {
      assert (txtnode.urn.textGroup == urn.textGroup)
      assert (txtnode.urn.work == urn.work)
    }
  }

  it should "filter the corpus contents against a range URN with containing work hierarchy and containing points for range beginning and ending" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.1-1.2")
    val filtered = corpus.urnMatch(urn)
    assert (filtered.size == 4)
    for (txtnode <- filtered) {
      assert (txtnode.urn.textGroup == urn.textGroup)
      assert (txtnode.urn.work == urn.work)
    }
  }

  it should "return an empty vector if the first node of the filtering URN does not appear in the corpus" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA:FAKE-1.2")
    val filtered = corpus.urnMatch(urn)
    assert (filtered.isEmpty)
  }
  it should "return an empty vector if the second node of the filtering URN does not appear in the corpus" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.1-FAKE")
    val filtered = corpus.urnMatch(urn)
    assert (filtered.isEmpty)
  }

  // CTS-like convenience methods
  it should "offer a convenience method for finding the first citable node in a filtered vector" in {
    val filterUrn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.2.lemma-1.5.comment")
    val expectedFirst = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.2.lemma")
    assert(corpus.getFirstNode(filterUrn).urn == expectedFirst)
  }

  it should "offer a convenience method for finding the last citable node in a filtered vector" in {
    val filterUrn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.2")
    val expectedLast = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.2.comment")
    assert(corpus.getLastNode(filterUrn).urn == expectedLast)
  }
  it should "offer a convenience method for reducing a list of citable nodes to a list of URN" in {
    val filterUrn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.2")
    val expectedReff = Vector(CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.2.lemma"),
    CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.2.comment"))
    assert(corpus.getValidReff(filterUrn) == expectedReff)
  }

  it should "offer a convenience method for extracting the string contents from a list of citable nodes as a single string" in {
    val filterUrn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.2")
    val actualText = corpus.getTextContents(filterUrn)

    // sample beginning and end of long text:
    val expectedOpening = """<div xmlns="http://www.tei-c.org/ns/1.0" n="lemma"> <p> μῆνις</p>"""
    val expectedClosing = """οὐκ ὀρθῶς.</p></div>"""

    assert(actualText.startsWith(expectedOpening))
    assert(actualText.endsWith(expectedClosing))
  }

  it should "offer a convenience method to extract a list of works cited in the corpus" in {
    val urn = CtsUrn("urn:cts:greekLit:tlg5026.msA:1.1-1.2")
    val expectedWorks = Set(CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:") )
    assert (corpus.citedWorks.toSet == expectedWorks)
  }

  it should "have a method to get 82xf format in a string" in {
    val xfString = corpus.to82xfString("#")
    assert(xfString.split("\n").size == 10)
  }
  it should "have a method to write 2 column format in a string" in {
    val twoColStr = corpus.to2colString("#")
    assert(twoColStr.split("\n").size == 10)
  }

}
