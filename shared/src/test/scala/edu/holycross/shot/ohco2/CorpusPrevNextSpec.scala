package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class CorpusPrevNextSpec2 extends FlatSpec {


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

val versionsAndExemplars = """urn:cts:greekLit:g.w.v:1.1#two tokens
urn:cts:greekLit:g.w.v:1.2#two tokens
urn:cts:greekLit:g.w.v:1.3#two tokens
urn:cts:greekLit:g.w.v:2.1#two tokens
urn:cts:greekLit:g.w.v:2.2#two tokens
urn:cts:greekLit:g.w.v.e:1.1.1#two
urn:cts:greekLit:g.w.v.e:1.1.2#tokens
urn:cts:greekLit:g.w.v.e:1.2.1#two
urn:cts:greekLit:g.w.v.e:1.2.2#tokens
urn:cts:greekLit:g.w.v.e:1.3.1#two
urn:cts:greekLit:g.w.v.e:1.3.2#tokens
urn:cts:greekLit:g.w.v.e:2.1.1#two
urn:cts:greekLit:g.w.v.e:2.1.2#tokens
urn:cts:greekLit:g.w.v.e:2.2.1#two
urn:cts:greekLit:g.w.v.e:2.2.2#tokens
"""

  val corpus = Corpus(scholiaDelimited,"#")

  "A corpus of citable nodes"  should "find the single previous node before a node" in {
    val u = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1.comment")
    val prv = corpus.prev(u)
    assert(prv.size == 1)
    val expectedUrn =  CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1.lemma")
    assert(prv(0).urn == expectedUrn)

  }
  it should "find the URN for a single previous node before a node" in {
    val u = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1.comment")
    val prv = corpus.prevUrn(u)
    assert(prv.get == CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1.lemma"))
  }

  it should  "find the previous passage of equal size before a passage" in {
    val middle = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.3")
    val prv = corpus.prev(middle)

    assert(prv.size == 2)
    assert(prv(0).urn ==  CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.2.lemma"))
    assert(prv(1).urn ==  CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.2.comment"))
  }

  it should  "find the URN for the previous passage of equal size before a passage" in {
    val middle = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.3")
    val prvUrn = corpus.prevUrn(middle)

    assert(prvUrn.get == CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.2.lemma-1.2.comment"))
  }


  it should "find all previous nodes before a passage if they are fewer than the size of the passage"  in {
    val middle = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.2-1.4")
    val prv = corpus.prev(middle)

    assert(prv.size == 2)
    assert(prv(0).urn ==  CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1.lemma"))
    assert(prv(1).urn ==  CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1.comment"))
  }
  it should "find the URN for all previous nodes before a passage if they are fewer than the size of the passage"  in {
    val middle = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.2-1.4")
    val prv = corpus.prevUrn(middle)
    assert(prv.get == CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1.lemma-1.1.comment"))
  }



  it should "find the single next node after a node" in {
    val firstU = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1.lemma")
    val nxt = corpus.next(firstU)
    assert(nxt.size == 1)
    assert(nxt(0).urn == CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1.comment"))
  }
  it should "find the URN for the single next node after a node" in {
    val firstU = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1.lemma")
    val nxt = corpus.nextUrn(firstU)
    assert(nxt.get == CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1.comment"))
  }


  it should "find the next passage of equal size after a containing reference" in {
    val middle = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.3")
    val nxt = corpus.next(middle)
    assert(nxt.size == 2)
    assert(nxt(0).urn ==  CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.4.lemma"))
    assert(nxt(1).urn ==  CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.4.comment"))
  }
  it should "find the URN for the next passage of equal size after a containing reference" in {
    val middle = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.3")
    val nxt = corpus.nextUrn(middle)
    assert(nxt.get ==
    CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.4.lemma-1.4.comment"))
  }

  it should "find all following nodes after a range of containers if they are fewer than the size of the passage"  in {
    val middle = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.2-1.4")
    val nxt = corpus.next(middle)
    assert(nxt.size == 2)
    assert(nxt(0).urn == CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.5.lemma"))
    assert(nxt(1).urn == CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.5.comment"))
  }
  it should "find the URN for all following nodes after a range of containers if they are fewer than the size of the passage"  in {
    val middle = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.2-1.4")
    val nxt = corpus.nextUrn(middle)
    assert(nxt.get == CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.5.lemma-1.5.comment"))
  }

  it should "honor limits of other texts within the nodes vector" in {

    val cdata = "urn:cts:greekLit:tlg5026.msAext.hmt:18.8.comment#πὸλλ' ἐπάλυνον\n" +
    "urn:cts:greekLit:tlg5026.msAext.hmt:18.9.comment#ὁμαρτῇ\n" +
    "urn:cts:greekLit:tlg5026.msAext.hmt:18.10.comment#ἄλλοτε δ'\n" +
    "urn:cts:greekLit:tlg5026.msAext.hmt:19.2015office10_1.comment#ἡ δ' ἐκύει εἱστήκει\n" +
    "urn:cts:greekLit:tlg5026.msAext.hmt:19.hc_1.comment#εὖτε\n" +
    "urn:cts:greekLit:tlg5026.msAext.hmt:19.hc_2.comment#ἡνιοχῆας\n" +
    "urn:cts:greekLit:tlg5026.msAext.hmt:19.hc_3.comment#ἕωμεν\n" +
    "urn:cts:greekLit:tlg5026.msAext.hmt:19.hc_4.comment#αὐτόθι\n" +
    "urn:cts:greekLit:tlg5026.msAil.hmt:1.143.comment#Αιολικον\n" +
    "urn:cts:greekLit:tlg5026.msAil.hmt:1.144.comment#ορκικον ἐπίρρημα\n" +
    "urn:cts:greekLit:tlg5026.msAil.hmt:1.145.comment#προθύμως\n"

    val corpus = Corpus(cdata,"#")
    val rng = CtsUrn("urn:cts:greekLit:tlg5026.msAil.hmt:1.143-1.144")

    assert(corpus.prevUrn(rng) == None)
    val nxt = corpus.nextUrn(rng)
    val expected  = CtsUrn("urn:cts:greekLit:tlg5026.msAil.hmt:1.145.comment")
    assert(nxt.get == expected)
  }



  it should "ignore exemplars when getting a preceding URN for a version-level reference" in {
      val corpus = Corpus(versionsAndExemplars,"#")
      val versionLevel = CtsUrn("urn:cts:greekLit:g.w.v:1.2")
      val expected = CtsUrn("urn:cts:greekLit:g.w.v:1.1")
      val prev = corpus.prevUrn(versionLevel)
      assert ( prev.get == expected)
  }

  it should "ignore exemplars when finding preceding nodes in a version-level container" in {
    val corpus = Corpus(versionsAndExemplars,"#")
    // contains 2 nodes:
    val container = CtsUrn("urn:cts:greekLit:g.w.v:2")
    // reference to previous URN should identify two nodes:
    val expected = CtsUrn("urn:cts:greekLit:g.w.v:1.2-1.3")
    val prev = corpus.prevUrn(container)
    assert(prev.get == expected)
  }

  it should "ignore exemplars when getting a following URN for a version-level reference" in {
    val corpus = Corpus(versionsAndExemplars,"#")
    // contains two nodes
    val versionContainer = CtsUrn("urn:cts:greekLit:g.w.v:1")

    val expected = CtsUrn("urn:cts:greekLit:g.w.v:2.1-2.2")
    val nxt = corpus.nextUrn(versionContainer)

    println("NEXT: " + nxt)
    //assert(nxt.get == expected)
  }




  it should "ignore exemplars when getting a following URN from a version-level container" in {

      val corpus = Corpus(versionsAndExemplars,"#")
      // contains two nodes
      val versionContainer = CtsUrn("urn:cts:greekLit:g.w.v:1")
      val expected = CtsUrn("urn:cts:greekLit:g.w.v:2.1-2.2")
      val nxt = corpus.nextUrn(versionContainer)
      assert(nxt.get == expected)
  }



  it should "ignore exemplars when getting a preceding URN from version-level range" in {

      val corpus = Corpus(versionsAndExemplars,"#")
      val rnge = CtsUrn("urn:cts:greekLit:g.w.v:2.1-2.2")
      val expected = CtsUrn("urn:cts:greekLit:g.w.v:1.2-1.3")
      val prev = corpus.prevUrn(rnge)
      assert(prev.get == expected)
  }

  it should "ignore exemplars when getting a following URN from a version-level range" in {

      val corpus = Corpus(versionsAndExemplars,"#")
      val rnge = CtsUrn("urn:cts:greekLit:g.w.v:1.1-1.2")
      val expected = CtsUrn("urn:cts:greekLit:g.w.v:1.3-2.1")
      val nxt = corpus.nextUrn(rnge)
      assert(nxt.get == expected)
  }


  "The companion object" should "make range URNs for a vector of citable nodes"  in {
    val range =  Corpus.passageUrn(corpus.nodes).get
    assert (range == CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1.lemma-1.5.comment"))
  }



}
