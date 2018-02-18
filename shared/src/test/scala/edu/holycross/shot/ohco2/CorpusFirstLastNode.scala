package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class CorpusFirstLastNodeSpec extends FlatSpec {


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
urn:cts:greekLit:g.w.v.e2:1.1.1#two
urn:cts:greekLit:g.w.v.e2:1.1.2#tokens
urn:cts:greekLit:g.w.v.e2:1.2.1#two
urn:cts:greekLit:g.w.v.e2:1.2.2#tokens
urn:cts:greekLit:g.w.v.e2:1.3.1#two
urn:cts:greekLit:g.w.v.e2:1.3.2#tokens
urn:cts:greekLit:g.w.v.e2:2.1.1#two
urn:cts:greekLit:g.w.v.e2:2.1.2#tokens
urn:cts:greekLit:g.w.v.e2:2.2.1#two
urn:cts:greekLit:g.w.v.e2:2.2.2#tokens
"""

  val corpus = Corpus(scholiaDelimited,"#")
  val exemplarCorpus = Corpus(versionsAndExemplars,"#")

  "A corpus of citable nodes"  should "find the first node of a version" in {
    val u = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:")
    val fn = corpus.firstNode(u)
    val expectedUrn =  CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1.lemma")
    assert(fn.urn == expectedUrn)
  }

  it should "find the first node of a specified range if containing elements" in {
    val u = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.2-1.4")
    val fn = corpus.firstNode(u)
    val expectedUrn =  CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.2.lemma")
    assert(fn.urn == expectedUrn)
  }

  it should "find the first node of a specified range of leaf nodes" in {
    val u = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.2.comment-1.4.lemma")
    val fn = corpus.firstNode(u)
    val expectedUrn =  CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.2.comment")
    assert(fn.urn == expectedUrn)
  }

  it should "find the first node of a specified mixed range of leaf nodes" in {
    val u = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.2-1.4.lemma")
    val fn = corpus.firstNode(u)
    val expectedUrn =  CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.2.lemma")
    assert(fn.urn == expectedUrn)
  }

  it should "find the first node of a specified containing citation" in {
    val u = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1")
    val fn = corpus.firstNode(u)
    val expectedUrn =  CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1.lemma")
    assert(fn.urn == expectedUrn)
  }

  it should "find the first node of a version when there are exemplars" in {
    val u = CtsUrn("urn:cts:greekLit:g.w.v:")
    val fn = exemplarCorpus.firstNode(u)
    val expectedUrn =  CtsUrn("urn:cts:greekLit:g.w.v:1.1")
    assert(fn.urn == expectedUrn)
  }

  it should "find the first node of a an exemplar when there is a version" in {
    val u = CtsUrn("urn:cts:greekLit:g.w.v.e:")
    val fn = exemplarCorpus.firstNode(u)
    val expectedUrn =  CtsUrn("urn:cts:greekLit:g.w.v.e:1.1.1")
    assert(fn.urn == expectedUrn)
  }

  it should "find the first node of a an exemplar when there is a version and more than one exemplar" in {
    val u = CtsUrn("urn:cts:greekLit:g.w.v.e2:")
    val fn = exemplarCorpus.firstNode(u)
    val expectedUrn =  CtsUrn("urn:cts:greekLit:g.w.v.e2:1.1.1")
    assert(fn.urn == expectedUrn)
  }

 it should "find the first node of a an exemplar container when there is a version" in {
    val u = CtsUrn("urn:cts:greekLit:g.w.v.e:1.2")
    val fn = exemplarCorpus.firstNode(u)
    val expectedUrn =  CtsUrn("urn:cts:greekLit:g.w.v.e:1.2.1")
    assert(fn.urn == expectedUrn)
  }

 it should "find the first node of a an exemplar range when there is a version and more than one exemplar" in {
    val u = CtsUrn("urn:cts:greekLit:g.w.v.e2:1.2")
    val fn = exemplarCorpus.firstNode(u)
    val expectedUrn =  CtsUrn("urn:cts:greekLit:g.w.v.e2:1.2.1")
    assert(fn.urn == expectedUrn)
  }

  it should "do all of this for lastNode, too" in {
    var u = CtsUrn("urn:cts:greekLit:g.w.v:")
    var fn = exemplarCorpus.lastNode(u)
    var expectedUrn =  CtsUrn("urn:cts:greekLit:g.w.v:2.2")
    assert(fn.urn == expectedUrn)
    u = CtsUrn("urn:cts:greekLit:g.w.v.e:")
    fn = exemplarCorpus.lastNode(u)
    expectedUrn =  CtsUrn("urn:cts:greekLit:g.w.v.e:2.2.2")
    assert(fn.urn == expectedUrn)
    u = CtsUrn("urn:cts:greekLit:g.w.v.e2:")
    fn = exemplarCorpus.lastNode(u)
    expectedUrn =  CtsUrn("urn:cts:greekLit:g.w.v.e2:2.2.2")
    assert(fn.urn == expectedUrn)
    u = CtsUrn("urn:cts:greekLit:g.w.v.e2:1")
    fn = exemplarCorpus.lastNode(u)
    expectedUrn =  CtsUrn("urn:cts:greekLit:g.w.v.e2:1.3.2")
    assert(fn.urn == expectedUrn)
    u = CtsUrn("urn:cts:greekLit:g.w.v.e2:1.1-1.2")
    fn = exemplarCorpus.lastNode(u)
    expectedUrn =  CtsUrn("urn:cts:greekLit:g.w.v.e2:1.2.2")
    assert(fn.urn == expectedUrn)
  }

}
