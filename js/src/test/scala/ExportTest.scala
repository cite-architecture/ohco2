package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import org.scalatest._

class ExportTest extends FlatSpec {

  "The ohco2 library"  should "expose methods of CitableNode" in {
    val cn = CitableNode(CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1"),"Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος")

    assert(cn.urn == CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1"))
    assert(cn.text == "Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος")
  }

  it should "expose the text catalog" in {
    val urn  = CtsUrn("urn:cts:demo:g.w.ed:")
    val catalogEntry = CatalogEntry(urn,"chunk","eng","Demo texts","made up work",Some("edition"),None,false)
    assert(catalogEntry.urn == urn)
  }
  it should "expose the Corpus object" in {

    val hdtIntro = """urn:cts:greekLit:tlg0016.tlg001.eng:1.0#This is the Showing forth of the Inquiry of Herodotus of Halicarnassos , to the end that neither the deeds of men may be forgotten by lapse of time , nor the works great and marvellous , which have been produced some by Hellenes and some by Barbarians , may lose their renown ; and especially that the causes may be remembered for which these waged war with one another
urn:cts:greekLit:tlg0016.tlg001.eng:1.1#Those of the Persians who have knowledge of history declare that the Phenicians first began the quarrel . These , they say , came from that which is called the Erythraian Sea to this of ours ; and having settled in the land where they continue even now to dwell , set themselves forthwith to make long voyages by sea . And conveying merchandise of Egypt and of Assyria they arrived at other places and also at Argos ; now Argos was at that time in all points the first of the States within that land which is now called Hellas ; — the Phenicians arrived then at this land of Argos , and began to dispose of their ship's cargo : and on the fifth or sixth day after they had arrived , when their goods had been almost all sold , there came down to the sea a great company of women , and among them the daughter of the king ; and her name , as the Hellenes also agree , was Io the daughter of Inachos . These standing near to the stern of the ship were buying of the wares such as pleased them most , when of a sudden the Phenicians , passing the word from one to another , made a rush upon them ; and the greater part of the women escaped by flight , but Io and certain others were carried off . So they put them on board their ship , and forthwith departed , sailing away to Egypt .
"""
    val corpus = Corpus(hdtIntro,"#")
    assert (corpus.size == 2)
  }

  it should "expose the StringCount object" in {
    val stringCount =  StringCount("καὶ διὰ τὸ",2)
    assert (stringCount.count == 2)
  }
  it should "expose the StringHistogram object" in {
    val histo = StringHistogram(Vector( StringCount("καὶ διὰ τὸ",2)))
    assert(histo.size  == 1)
  }
  it should "expose the TextRepository" in {
    val scholiaDelimited = """
urn:cts:greekLit:tlg5026.msA.hmt:1.3.lemma#<div xmlns="http://www.tei-c.org/ns/1.0" n="lemma"> <p> ἄειδε</p></div>
urn:cts:greekLit:tlg5026.msA.hmt:1.3.comment#<div xmlns="http://www.tei-c.org/ns/1.0" n="comment"> <p> ὅτι κατὰ τὴν ποιητικὴν ἤτοι άδειαν ἠ συνήθειαν λαμβάνει τὰ προστακτικὰ ἀντι εὐκτικῶν· καὶ γὰρ <persName n="urn:cite:hmt:pers.pers4"> Ἡσίοδός</persName> φησι· <q> δεῦτε δὴ ἐννέπετε <note resp="Erbse"> opp.2</note></q> , καὶ <persName n="urn:cite:hmt:pers.pers5"> Πίνδαρος</persName> · <q> μαντεύο <persName n="urn:cite:hmt:pers.pers6"> Μοῦσα</persName> <note resp="Erbse"> fr. 150 sn. </note></q> , καὶ <persName n="urn:cite:hmt:pers.pers7"> Ἀντίμαχος</persName> ὁ <placeName n="urn:cite:hmt:place.place54"> <seg type="word"> Κολοφ <unclear> ώνιος.</unclear></seg></placeName> <q> ἐννέπετε <persName n="urn:cite:hmt:pers.pers8"> <seg type="word"> Κρονί <unclear> ανος</unclear></seg> Διὸς </persName> μεγάλοιο θύγατρες <note resp="Erbse"> fr. 1 w</note></q> · δευτερον δὲ ὅτι οὐ κατὰ ἀλήθειαν ταῖς <persName n="urn:cite:hmt:pers.pers6"> Μούσαις</persName> ἐπιτάσσουσιν, ἀλλ' ἑαυτοῖς⁑</p></div>
"""
    val corpus = Corpus(scholiaDelimited,"#")
    val catalogSource = """urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online
urn:cts:greekLit:tlg5026.msA.hmt:#book/scholion/part#Scholia to the Iliad#Main scholia of the Venetus A#HMT project edition##true#grc
"""
    val catalog = Catalog(catalogSource)
    val repo = TextRepository(corpus,catalog)
    assert(repo.catalog.size == 1)
    assert(repo.corpus.size == 2)
  }
}
