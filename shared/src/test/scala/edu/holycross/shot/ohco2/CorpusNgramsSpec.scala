package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class CorpusNgramsSpec extends FlatSpec {


  val scholiaDelimited = """urn:cts:greekLit:tlg5026.msA.hmt:1.1.lemma#μῆνιν ἄειδε
urn:cts:greekLit:tlg5026.msA.hmt:1.1.comment#ζητοῦσι διά τί ἀπὸ τῆς μήνιδος ἤρξατο οὕτως δυσφήμου ὀνόματος · διά δύο ταῦτα · πρῶτον μὲν ἵν' ἐκ τοῦ πάθους ἀπκαταῥρεύσῃ τὸ τοιοῦτο μόριον τῆς ψυχῆς καὶ προσεκτικωτέρους τοὺς ἀκροατὰς ἐπί τοῦ μεγέθους ποιήσῃ καὶ προσεθίζῃ φέρειν γενναίως ἡμᾶς τὰ πάθη . μέλλων πολλέμους ἀπαγγέλλειν · δεύτερον . ἵνα τὰ ἐγκώμια τῶν Ἑλλήνων πιθανώτερα ποιήσῃ · ἐπεὶ δὲ ἔμελλε , νικωντας ἀποφαίνειν τοὺς Ἑλληνας , εἰκότως οὐ κατατρέχει ἀξιοπιστότερον ἐκ τοῦ μὴ πάντα χαρίζεσθαι τῷ ἐκείνων ἐπαίνῳ · ἤρξατο μὲν ἀπὸ μήνιδος ἐπείπερ αὕτη τοῖς πρακτικοῖς ὑπόθεσις γέγονεν · ἄλλῳς τε καὶ τραγῳδίαις τραγικὸν ἐξεῦρε προοίμιον · καὶ γὰρ προσεκτικοὺς ἡμᾶς ἡ τῶν ἀτυχημάτων διήγησις ἐργάζεται · καὶ ὡς ἄριστος ἰατρὸς , πρῶτον ἀναστέλλων τὰ νοσήματα τῆς ψυχῆς ὕστερον τὴν ΐασιν ἐπάγει . Ἑλληνικὸν δὲ τὸ πρό τέλει τὰς ηδονὰς ἐπάγειν · ῾ϊστέον δέ , ὥσπερ ἐπί συκῆς πρῶτον μέν ἐστιν όλυνθος εἶτα φίλιξ σύκον ϊσχάς . οὕτω πρῶτον · ὀργή · θυμός · χόλος · κότος · μῆνις · ὅμως ὁ ποιητὴς ὡς συνωνύμοις ὀνόμασιν ἐπὶ Ἀχιλλεως χρῆται ἢἐ χόλον παύσειεν , ἐρητύσειε τε θυμόν · οὐδ' ὄθομαι κοτέοντος , αὐτὰρ ὁ μήνιε νηυσίν ·
urn:cts:greekLit:tlg5026.msA.hmt:1.2.lemma#μῆνις
urn:cts:greekLit:tlg5026.msA.hmt:1.2.comment#παρὰ τὸ μένω μῆνις ὡς ἐνὸς ἦνις · οἱ δὲ περὶ Γλαύκωνα τὸν Ταρσέα ἠξίουν ὀξύνειν τὸ ὄνομα οὐκ ὀρθῶς .
urn:cts:greekLit:tlg5026.msA.hmt:1.3.lemma#ἄειδε
urn:cts:greekLit:tlg5026.msA.hmt:1.3.comment#ὅτι κατὰ τὴν ποιητικὴν ἤτοι άδειαν ἢ συνήθειαν λαμβάνει τὰ προστακτικὰ ἀντί εὐκτικῶν · καὶ γὰρ Ἡσίοδός φησι · δεῦτε δὴ ἐννέπετε , καὶ Πίνδαρος · μαντεύο Μοῦσα , καὶ Ἀντίμαχος ὁ Κολοφώνιος . ἐννέπετε Κρονίανος Διὸς μεγάλοιο θύγατρες · δεύτερον δὲ ὅτι οὐ κατὰ ἀλήθειαν ταῖς Μούσαις ἐπιτάσσουσιν , ἀλλ' ἑαυτοῖς ⁑
urn:cts:greekLit:tlg5026.msA.hmt:1.4.lemma#θεά
urn:cts:greekLit:tlg5026.msA.hmt:1.4.comment#οὕτως εἴωθε τὴν Μοῦσαν καλεῖν · ἀμέλει καὶ ἐν Ὀδυσσεία · ἄνδρα μοι ἔννεπε Μοῦσα ⁑
urn:cts:greekLit:tlg5026.msA.hmt:1.5.lemma#Πηληϊάδεω Ἀχιλῆος
urn:cts:greekLit:tlg5026.msA.hmt:1.5.comment#οὕτως ἀναγνωστέον διά τοῦ ἑνός λ καὶ διὰ τὸ μέτρον καὶ διὰ τὸ ἄχος ὅ ἐστι λύπην ἐπενεγκεῖν τοῖς Ἰλιεῦσιν , οἱ δὲ παρὰ τὸ μὴ θιγεῖν χείλεσι θηλῆς ὅλως γὰρ οὐ μετέσχε γάλακτος ⁑
urn:cts:greekLit:tlg5026.msA.hmt:1.6.lemma#μυρία
urn:cts:greekLit:tlg5026.msA.hmt:1.6.comment#τινὲς θρηνητικά · παρὰ τὸ μύρεσθαι καὶ ἐπίθετον αὐτὸ τῶν ἀλγέων ήκουσαν , ἐφ' οἷς μύριᾶσθαι καὶ τὸ κλαῦσαι , οὐδὲν δὲ ἄτοπον εἰ παρὰ Μουσῶν ταῦτα ἐρωτᾷ φρόνησις μὲν γάρ ἐστιν ἡ πάντ εἴδησις · προαίρεσις δὲ ἀκριβῆς ἡ τῶν ἀμεινόνων πραξεων αἵρεσις ⁑
urn:cts:greekLit:tlg5026.msA.hmt:1.7.lemma#Ἀχαιοῖς
urn:cts:greekLit:tlg5026.msA.hmt:1.7.comment#Ξοῦθος ὁ Αἰόλου παῖς , ἀγόμενος Κρέουσαν τὴν Ἐρεχθεως θυγατέρα ἔσχεν ἐξ αὐτῆς δύο παῖδας , ΄Ϊονα καὶ Ἀχαιόν · ὧν ὁ μὲν ΄Ϊων ᾤκησεν Ἀθήνας , ὁ δὲ Ἀχαιὸς φονον ἐμφύλιον δράσας , παρεγένετο εἰς Θεσσαλιαν καὶ κυριεύσας τῆς χώρας τοὺς ὑποτεταγμένους , ἀφ' ἑαυτοῦ προσηγόρευσεν Ἀχαιούς , Ἕλληνες δὲ κοινῶς πάντες οἱ τῆς Ἑλλάδος ἐκλήθησαν ἀπό Ελληνος τοῦ Διός · πρῶτοι οὖν οὗτως ἐλέγοντο οἱ ἐν Θεσσαλία ἄνθρωποι καὶ οὖτοι οὐ πάντες , ἀλλά μόνοι οἱ ἐν Ἑλλάδι τῇ πόλει ἔτειτα μεγάλα δυναστευθέντος τοῦ Ἕλληνος , καὶ τῶν τούτου παίδων ἀπ' αὑτοῦ πάντες ἐκλήθησαν Ἕλληνες ⁑
urn:cts:greekLit:tlg5026.msA.hmt:1.8.lemma#ἡρώων αὐτοὺς δὲ
urn:cts:greekLit:tlg5026.msA.hmt:1.8.comment#ὅτι Ζηνόδοτος τοὺς δύο ἀθετεῖ · γίνεται δὲ τὸ προοίμιον κόλον καὶ πρὸς τὰς ψυχὰς ἀντιδιέσταλκεν τὸ αὐτοὺς δὲ ἐπί τινῶν σωμάτων · ἥρωας δὲ φασὶ κλνθῆναι . ἀπό τῆς ἀρετῆς , ἢ ἀπό τοῦ ἀέρος . ὡς φησιν Ἱσίοδος ἠέρα ἐσσαμενοι , ἢ ἀπό τῆς ἐράσεως τουτέστι τῆς μίξεως τῶν θεῶν · οἱ γὰρ θεοὶ θνηταῖς γυναιξὶ συνερχόμενοι . ἐποίουν γένος τὸ τῶν ἡρώων . ἢ ἀπό τῆς ερας , ἔρᾳ δὲ ἡ γῆ κατὰ διάλεκτον · ἐκ δὲ τῆς γῆς ἐπλάσθη τὸ γένος τῶν ἀνθρώπων ⁑
urn:cts:greekLit:tlg5026.msA.hmt:1.9.lemma#Διὸς δ' ἐτελείετο βουλή
urn:cts:greekLit:tlg5026.msA.hmt:1.9.comment#τινὲς σὺν τῷ ϊ κατὰ δοτικὴν οἷον τῆ βουλῇ τοῦ Διὸς ἐτελειοῦτο ⁑
urn:cts:greekLit:tlg5026.msA.hmt:1.10.lemma#Διὸς δ' ἐτελείετο βουλή ⁑ ἐξ οὖ δὴ τὰ πρῶτα
urn:cts:greekLit:tlg5026.msA.hmt:1.10.comment#Ἀρίσταρχος συνάπτει ἵνα μὴ παροῦσα τίς φαίνηται βουλῆ καθ' Ἑλληνων , ἀλλ' ἀφ' οὖ χρόνου ἐγένετο ἡ μῆνις ἵνα μὴ τὰ παρά τοῖς νεωτέροις πλάσματα δεξώμεθα ⁑
"""

  val corpus = Corpus(scholiaDelimited,"#")



  "A citable corpus" should "compute n-grams over the corpus" in {
    val histo = corpus.ngramHisto(3,1)
    val expectedVect = Vector(
      StringCount("καὶ διὰ τὸ",2),
      StringCount("Διὸς δ' ἐτελείετο", 2),
      StringCount("δ' ἐτελείετο βουλή",2),
      StringCount("ἢ ἀπό τῆς",2)
    )
    val expected = StringHistogram(expectedVect)

    assert (histo == expected)
  }


}
