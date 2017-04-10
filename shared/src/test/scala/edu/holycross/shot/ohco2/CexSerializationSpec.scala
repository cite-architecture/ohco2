package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._


/**
*/
class CexSerializationSpec extends FlatSpec {

  val catalogData = """urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online
urn:cts:greekLit:tlg5026.msA.hmt:#book/scholion/part#Scholia to the Iliad#Main scholia of the Venetus A#HMT project edition##true
urn:cts:greekLit:tlg5026.msAim.hmt:#book/scholion/part#Scholia to the Iliad#Intermarginal scholia of the Venetus A#HMT project edition##true
urn:cts:greekLit:tlg5026.msAint.hmt:#book/scholion/part#Scholia to the Iliad#Interior scholia of the Venetus A#HMT project edition##true
urn:cts:greekLit:tlg5026.msAext.hmt:#book/scholion/part#Scholia to the Iliad#Exterior scholia of the Venetus A#HMT project edition##true
urn:cts:greekLit:tlg5026.msAil.hmt:#book/scholion/part#Scholia to the Iliad#Interlinear scholia of the Venetus A#HMT project edition##true
urn:cts:greekLit:tlg5026.msAimlater.hmt:#book/scholion/part#Scholia to the Iliad#Later intermarginal scholia of the Venetus A#HMT project edition##true
"""


  val catalog = Catalog(catalogData)


  val hdtIntro = """urn:cts:greekLit:tlg0016.tlg001.eng:1.0#This is the Showing forth of the Inquiry of Herodotus of Halicarnassos , to the end that neither the deeds of men may be forgotten by lapse of time , nor the works great and marvellous , which have been produced some by Hellenes and some by Barbarians , may lose their renown ; and especially that the causes may be remembered for which these waged war with one another
urn:cts:greekLit:tlg0016.tlg001.eng:1.1#Those of the Persians who have knowledge of history declare that the Phenicians first began the quarrel . These , they say , came from that which is called the Erythraian Sea to this of ours ; and having settled in the land where they continue even now to dwell , set themselves forthwith to make long voyages by sea . And conveying merchandise of Egypt and of Assyria they arrived at other places and also at Argos ; now Argos was at that time in all points the first of the States within that land which is now called Hellas ; — the Phenicians arrived then at this land of Argos , and began to dispose of their ship's cargo : and on the fifth or sixth day after they had arrived , when their goods had been almost all sold , there came down to the sea a great company of women , and among them the daughter of the king ; and her name , as the Hellenes also agree , was Io the daughter of Inachos . These standing near to the stern of the ship were buying of the wares such as pleased them most , when of a sudden the Phenicians , passing the word from one to another , made a rush upon them ; and the greater part of the women escaped by flight , but Io and certain others were carried off . So they put them on board their ship , and forthwith departed , sailing away to Egypt .
urn:cts:greekLit:tlg0016.tlg001.eng:1.2#In this manner the Persians report that Io came to Egypt , not agreeing therein with the Hellenes , and this they say was the first beginning of wrongs . Then after this , they say , certain Hellenes (but the name of the people they are not able to report ) put in to the city of Tyre in Phenicia and carried off the king's daughter Europa ; — these would doubtless be Cretans ; — and so they were quits for the former injury . After this however the Hellenes , they say , were the authors of the second wrong ; for they sailed in to Aia of Colchis and to the river Phasis with a ship of war , and from thence , after they had done the other business for which they came , they carried off the king's daughter Medea : and the king of Colchis sent a herald to the land of Hellas and demanded satisfaction for the rape and to have his daughter back ; but they answered that , as the Barbarians had given them no satisfaction for the rape of Io the Argive , so neither would they give satisfaction to the Barbarians for this .
urn:cts:greekLit:tlg0016.tlg001.eng:1.3#In the next generation after this , they say , Alexander the son of Priam , having heard of these things , desired to get a wife for himself by violence from Hellas , being fully assured that he would not be compelled to give any satisfaction for this wrong , inasmuch as the Hellenes gave none for theirs . So he carried off Helen , and the Hellenes resolved to send messengers first and to demand her back with satisfaction for the rape ; and when they put forth this demand , the others alleged to them the rape of Medea , saying that the Hellenes were now desiring satisfaction to be given to them by others , though they had given none themselves nor had surrendered the person when demand was made .
urn:cts:greekLit:tlg0016.tlg001.eng:1.4#Up to this point , they say , nothing more happened than the carrying away of women on both sides ; but after this the Hellenes were very greatly to blame ; for they set the first example of war , making an expedition into Asia before the Barbarians made any into Europe . Now they say that in their judgment , though it is an act of wrong to carry away women by force , it is a folly to set one's heart on taking vengeance for their rape , and the wise course is to pay no regard when they have been carried away ; for it is evident that they would never be carried away if they were not themselves willing to go . And the Persians say that they , namely the people of Asia , when their women were carried away by force , had made it a matter of no account , but the Hellenes on account of a woman of Lacedemon gathered together a great armament , and then came to Asia and destroyed the dominion of Priam ; and that from this time forward they had always considered the Hellenic race to be their enemy : for Asia and the Barbarian races which dwell there the Persians claim as belonging to them ; but Europe and the Hellenic race they consider to be parted off from them .
urn:cts:greekLit:tlg0016.tlg001.eng:1.5#The Persians for their part say that things happened thus ; and they conclude that the beginning of their quarrel with the Hellenes was on account of the taking of Ilion : but as regards Io the Phenicians do not agree with the Persians in telling the tale thus ; for they deny that they carried her off to Egypt by violent means , and they say on the other hand that when they were in Argos she was intimate with the master of their ship , and perceiving that she was with child , she was ashamed to confess it to her parents , and therefore sailed away with the Phenicians of her own will , for fear of being found out . These are the tales told by the Persians and the Phenicians severally : and concerning these things I am not going to say that they happened thus or thus , but when I have pointed to the man who first within my own knowledge began to commit wrong against the Hellenes , I shall go forward further with the story , giving an account of the cities of men , small as well as great : for those which in old times were great have for the most part become small , while those that were in my own time great used in former times to be small : so then , since I know that human prosperity never continues steadfast , I shall make mention of both indifferently .
urn:cts:greekLit:tlg0016.tlg001.eng:1.6#Croesus was Lydian by race , the son of Alyattes and ruler of the nations which dwell on this side of the river Halys ; which river , flowing from the South between the Syrians and the Paphlagonians , runs out towards the North Wind into that Sea which is called the Euxine . This Croesus , first of all the Barbarians of whom we have knowledge , subdued certain of the Hellenes and forced them to pay tribute , while others he gained over and made them his friends . Those whom he subdued were the Ionians , the Aiolians , and the Dorians who dwell in Asia ; and those whom he made his friends were the Lacedemonians . But before the reign of Croesus all the Hellenes were free ; for the expedition of the Kimmerians , which came upon Ionia before the time of Croesus , was not a conquest of the cities but a plundering incursion only .
urn:cts:greekLit:tlg0016.tlg001.eng:1.7#Now the supremacy which had belonged to the Heracleidai came to the family of Croesus , called Mermnadai , in the following manner : — Candaules , whom the Hellenes call Myrsilos , was ruler of Sardis and a descendant of Alcaios , son of Heracles : for Agron , the son of Ninos , the son of Belos , the son of Alcaios , was the first of the Heracleidai who became king of Sardis , and Candaules the son of Myrsos was the last ; but those who were kings over this land before Agrond , were descendants of Lydos the son of Atys , whence this whole nation was called Lydian , having been before called Meonian . From these the Heracleidai , descended from Heracles and the slave-girl of Iardanos , obtained the government , being charged with it by reason of an oracle ; and they reigned for two-and-twenty generations of men , five hundred and five years , handing on the power from father to son , till the time of Candaules the son of Myrsos .
"""

  "A CatalogEntry" should "offer .cex formatting" in {
    val entry = catalog.texts(0)

    val expected = s"""urn:cts:greekLit:tlg5026.msA.hmt:#book/scholion/part#Scholia to the Iliad#Main scholia of the Venetus A#HMT project edition##true"""
    assert(entry.cex("#") == expected)
  }

  "A Catalog" should "offer a .cex seralization of the catalog" in {
    val cex = catalog.cex("#")
    val rows = cex.split("\n")
    assert(rows.size == 7)
    assert(rows(0) == "urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online")

    assert (rows(1) == "urn:cts:greekLit:tlg5026.msA.hmt:#book/scholion/part#Scholia to the Iliad#Main scholia of the Venetus A#HMT project edition##true")
  }


  "A CitableNode" should "offer .cex formatting" in {
    val iliad1_1 = CitableNode(CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1"),"Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος")

    assert(iliad1_1.cex("#") == "urn:cts:greekLit:tlg0012.tlg001.msA:1.1#Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος")
  }



  "A Corpus" should "offer a .cex serialization of the corpus" in {
    val corpus = Corpus(hdtIntro,"#")
    val hdtCex = corpus.cex("#")
    assert(hdtIntro == hdtCex)
  }

  "A TextRepository" should "offer .cex serialization of the whole repository" in {

    val shortCatalogText = s"""urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online
urn:cts:greekLit:tlg0012.tlg001.msA:#book/line#Homeric poetry#The Iliad#HMT edition of the Venetus A manuscript##true"""
    val shortCatalog = Catalog(shortCatalogText)

    val urn = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1")

    val shortTextV = Vector(CitableNode(urn,"Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος"))

    val tinyCorpus = Corpus(shortTextV)

    val repo = TextRepository(tinyCorpus,shortCatalog)
    val cex = repo.cex("#")
    val cexLines = cex.split("\n").toVector
    assert(cexLines.size == 5)
    assert (cexLines(0) == "#!ctscatalog")
    assert (cexLines(3) == "#!ctsdata")
  }
}
