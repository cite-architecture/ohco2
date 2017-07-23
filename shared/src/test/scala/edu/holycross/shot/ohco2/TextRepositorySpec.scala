package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class TextRepositorySpec extends FlatSpec {

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
    val catalogSource = """urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online
urn:cts:greekLit:tlg5026.msA.hmt:#book/scholion/part#Scholia to the Iliad#Main scholia of the Venetus A#HMT project edition##true#grc
"""
  val catalog = Catalog(catalogSource)

  "A repository of citable texts"  should "have a corpus of citable nodes" in {
    val repo = TextRepository(corpus, catalog)
    repo.corpus match {
      case c: Corpus => assert (true)
      case _ => fail("Failed to create corpus object")
    }
  }
  it should "have a catalog of citable texts" in {
    val repo = TextRepository(corpus, catalog)
    repo.catalog match {
      case c: Catalog => assert (true)
      case _ => fail("Failed to create catalog object")
    }
  }
  it should "validate there is a 1-1 relation between texts cataloged as online and texts cited in the corpus" in  {
    val repo = TextRepository(corpus, catalog)

    val catalogTexts = repo.catalog.texts.map(_.urn)
    assert(repo.corpus.citedWorks.toSet == catalogTexts.toSet)
  }
  it should "throw an Ohco2 exception if works cataloged as online do not appear in the corpus"  in {
    val hdr = "urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online"

    val entry = "urn:cts:greekLit:tlg0012.tlg001.msA:#book/line#Homeric poetry#the Iliad A#the Venetus A manuscript##true#grc"

    val badCatalogSrc =  hdr + "\n" + entry + "\n"
    val wrongCatalog = Catalog(badCatalogSrc)

    try {
      TextRepository(corpus, wrongCatalog)
      fail ("Should not have made a repository.")
    } catch {
      case exc: IllegalArgumentException => assert(exc.getMessage() ==   "requirement failed: Online catalog (1 texts) did not match works appearing in corpus (1 texts)")
      case otherExc: Throwable => throw otherExc
    }
  }

  it should "throw an Ohco2 exception if works in the corpus do not appear in the catalog as online"  in {
    val hdr = "urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online"

    val entry = "urn:cts:greekLit:tlg5026.msA.hmt:#book/scholion/part#Scholia to the Iliad#Main scholia of the Venetus A#HMT project edition##false#grc"

    val badCatalogSrc =  hdr + "\n" + entry + "\n"
    val shortCatalog = Catalog(badCatalogSrc)

    try {
      TextRepository(corpus, shortCatalog)
      fail ("Should not have made a repository.")
    } catch {
      case exc: IllegalArgumentException => assert(exc.getMessage() ==   "requirement failed: Online catalog (0 texts) did not match works appearing in corpus (1 texts)")
      case otherExc: Throwable => throw otherExc
    }
  }

  it should "ignore blank lines in making a repository" in {

    val tinyData = """urn:cts:greekLit:tlg5026.msA.hmt:1.4.lemma#<div xmlns="http://www.tei-c.org/ns/1.0" n="lemma"> <p> θεά</p></div>
urn:cts:greekLit:tlg5026.msA.hmt:1.4.comment#<div xmlns="http://www.tei-c.org/ns/1.0" n="comment"> <p> οὕτως εἴωθε τὴν <persName n="urn:cite:hmt:pers.pers6"> Μοῦσαν</persName> καλεῖν· ἀμέλει καὶ ἐν <title> Ὀδυσσεία</title> · <cit> <q> ἄνδρα μοι ἔννεπε <persName n="urn:cite:hmt:pers.pers6"> Μοῦσα</persName> <ref type="urn">
"""
    val tinyCatalog = """
urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online
urn:cts:greekLit:tlg5026.msA.hmt:#book/scholion/part#Scholia to the Iliad#Main scholia of the Venetus A#HMT project edition##true#grc
"""
    val catalog = Catalog(tinyCatalog)
    catalog match {
      case cat: Catalog => assert(true)
      case _ => fail("Failed to make a catalog")
    }
  }

  it should "provide labels for URNs" in {
    val repo = TextRepository(corpus, catalog)
    val psg = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.3.lemma")
    assert (repo.label(psg) == "Scholia to the Iliad, Main scholia of the Venetus A (HMT project edition) 1.3.lemma")
  }

  it should "be constructable from CEX data" in {
    val cex = """#!citelibrary
name#demo
version#2017.1
license#CC Share Alike.  For details, see more info.
#!ctscatalog
urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online
urn:cts:citedemo:arabic.quran.v1:#surah/ayah#Classical Arabic examples#The Quran#Arabic. Text from http://tanzil.net. Creative Commons Attribution 3.0 License##true#ara
#!ctsdata
urn:cts:citedemo:arabic.quran.v1:1.1#بِسْمِ اللَّهِ الرَّحْمَنِ الرَّحِيمِ
urn:cts:citedemo:arabic.quran.v1:1.2#الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ
urn:cts:citedemo:arabic.quran.v1:1.3#الرَّحْمَنِ الرَّحِيمِ
urn:cts:citedemo:arabic.quran.v1:1.4#مَالِكِ يَوْمِ الدِّينِ
urn:cts:citedemo:arabic.quran.v1:1.5#إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ
urn:cts:citedemo:arabic.quran.v1:1.6#اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ
urn:cts:citedemo:arabic.quran.v1:1.7#صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ
urn:cts:citedemo:arabic.quran.v1:2.1#بِسْمِ اللَّهِ الرَّحْمَنِ الرَّحِيمِ الم
urn:cts:citedemo:arabic.quran.v1:2.2#ذَلِكَ الْكِتَابُ لَا رَيْبَ فِيهِ هُدًى لِلْمُتَّقِينَ
"""
    val repo = TextRepository(cex,"#")
  }


  it should "offer a function to join two repositories" in pending
  it should "offer a function to diff two repositories" in pending

  it should "offer a function to join two catalogs" in pending
  it should "offer a function to diff two catalogs" in pending
}
