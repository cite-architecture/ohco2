package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._

class CorpusChunkingSpec extends FlatSpec {

  def splat(c: Corpus): Unit = {
    println(c.size)
    println(c.urns.mkString("\n"))
  }

val cex1:String = """urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.1#Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.2#οὐλομένην· ἡ μυρί' Ἀχαιοῖς ἄλγε' ἔθηκεν·
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.3#πολλὰς δ' ἰφθίμους ψυχὰς Ἄϊδι προΐαψεν
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.4#ἡρώων· αὐτοὺς δὲ ἑλώρια τεῦχε κύνεσσιν
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.5#οἰωνοῖσί τε πᾶσι· Διὸς δ' ἐτελείετο βουλή·
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:2.1#Ἄλλοι μέν ῥα θεοί τε καὶ ἀνέρες ἱπποκορυσταὶ
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:2.2#εὗδον παννύχιοι : Δία δ' οὐκ έχε , νήδυμος ὕπνος :
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:2.3#ἀλλ' ὅ γε μερμήριζε κατα φρένα : ὡς Ἀχιλῆα 
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:2.4#τιμήσῃ : ὀλέσῃ δὲ πολέας ἐπὶ νηυσὶν Ἀχαιῶν :
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.1.0#All
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.1.1#night
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.1.2#the
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.1.3#chiefs
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.1.4#before
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.1.5#their
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.1.6#vessels
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.1.7#lay,
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.2.0#And
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.2.1#lost
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.2.2#in
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.2.3#sleep
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.2.4#the
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.2.5#labours
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.2.6#of
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.2.7#the
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.2.8#day:
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.3.0#All
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.3.1#but
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.3.2#the
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.3.3#king:
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.3.4#with
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.3.5#various
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.3.6#thoughts
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.1.3.7#oppress'd,
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.2.1.0#A 
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.2.1.1#thousand 
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.2.1.2#cares 
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.2.1.3#his 
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.2.1.4#labouring 
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.2.1.5#breast 
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.2.1.6#revolves;
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.2.2.0#To 
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.2.2.1#seek 
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.2.2.2#sage 
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.2.2.3#Nestor 
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.2.2.4#now 
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.2.2.5#the 
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.2.2.6#chief 
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.2.2.7#resolves,
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.2.3.0#With 
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.2.3.1#him, 
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.2.3.2#in 
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.2.3.3#wholesome 
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.2.3.4#counsels, 
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.2.3.5#to 
urn:cts:fufolio:pope.iliad.fu2019_tokens:x.2.3.6#debate
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2_tokens:10.1.1#ἄλλοι
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2_tokens:10.1.2#μὲν
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2_tokens:10.1.3#παρὰ
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2_tokens:10.1.4#νηυσὶν
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2_tokens:10.1.5#ἀριστῆες
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2_tokens:10.1.6#Παναχαιῶν
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2_tokens:10.2.1#εὗδον
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2_tokens:10.2.2#παννύχιοι
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2_tokens:10.2.3#μαλακῷ
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2_tokens:10.2.4#δεδμημένοι
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2_tokens:10.2.5#ὕπνῳ
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2_tokens:10.2.6#·
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2_tokens:10.3.1#ἀλλʼ
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2_tokens:10.3.2#οὐκ
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2_tokens:10.3.3#Ἀτρεΐδην
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2_tokens:10.3.4#Ἀγαμέμνονα
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2_tokens:10.3.5#ποιμένα
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2_tokens:10.3.6#λαῶν
urn:cts:fufolio:pope.iliad.fu2019:1.1.1#Achilles' wrath, to Greece the direful spring
urn:cts:fufolio:pope.iliad.fu2019:1.1.2#Of woes unnumber'd, heavenly goddess, sing!
urn:cts:fufolio:pope.iliad.fu2019:1.1.3#That wrath which hurl'd to Pluto's gloomy reign
urn:cts:fufolio:pope.iliad.fu2019:1.1.4#The souls of mighty chiefs untimely slain;
urn:cts:fufolio:pope.iliad.fu2019:1.1.5#Whose limbs unburied on the naked shore,
urn:cts:fufolio:pope.iliad.fu2019:1.1.6#Devouring dogs and hungry vultures tore.
urn:cts:fufolio:pope.iliad.fu2019:1.1.7#Since great Achilles and Atrides strove,
urn:cts:fufolio:pope.iliad.fu2019:1.1.8#Such was the sovereign doom, and such the will of Jove!
urn:cts:fufolio:pope.iliad.fu2019:1.2.1#Declare, O Muse! in what ill-fated hour
urn:cts:fufolio:pope.iliad.fu2019:1.2.2#Sprung the fierce strife, from what offended power
urn:cts:fufolio:pope.iliad.fu2019:1.2.3#Latona's son a dire contagion spread,
urn:cts:fufolio:pope.iliad.fu2019:1.2.4#And heap'd the camp with mountains of the dead;
urn:cts:fufolio:pope.iliad.fu2019:1.2.5#The king of men his reverent priest defied,
urn:cts:fufolio:pope.iliad.fu2019:1.2.6#And for the king's offence the people died.
urn:cts:fufolio:pope.iliad.fu2019:1.3.1#For Chryses sought with costly gifts to gain
urn:cts:fufolio:pope.iliad.fu2019:1.3.2#His captive daughter from the victor's chain.
urn:cts:fufolio:pope.iliad.fu2019:1.3.3#Suppliant the venerable father stands;
urn:cts:fufolio:pope.iliad.fu2019:1.3.4#Apollo's awful ensigns grace his hands
urn:cts:fufolio:pope.iliad.fu2019:1.3.5#By these he begs; and lowly bending down,
urn:cts:fufolio:pope.iliad.fu2019:1.3.6#Extends the sceptre and the laurel crown
urn:cts:fufolio:pope.iliad.fu2019:1.3.7#He sued to all, but chief implored for grace
urn:cts:fufolio:pope.iliad.fu2019:1.3.8#The brother-kings, of Atreus' royal race
"""



  "A Corpus" should "be able to be chunked by distinct text" in {
      val corp:Corpus = Corpus(cex1,"#")
      val vc:Vector[Corpus] = corp.chunkByText
      assert(vc.size == 4)
      // test for proper grouping
      for (c <- vc) {
        assert (c.urns.map(_.dropPassage).distinct.size == 1)
      }
  }

  it should "chunk into corpora by citation-value" in {
      val corp:Corpus = Corpus(cex1,"#")
      val vc:Vector[Corpus] = corp.chunkByText
      val oneWork:Corpus = corp ~= CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:")
      assert( oneWork.size == 9)
      val chunked:Vector[Corpus] = oneWork.chunkByCitation(1)
      assert ( chunked.size == 2 )
      assert ( chunked(0).size == 5)
      assert ( chunked(1).size == 4)
  }

  // urn:cts:fufolio:pope.iliad.fu2019_tokens:
  // 
  it should "chunk into corpora by citation-value, at 3 levels" in {
      val corp:Corpus = Corpus(cex1,"#")
      val vc:Vector[Corpus] = corp.chunkByText
      val oneWork:Corpus = corp ~= CtsUrn("urn:cts:fufolio:pope.iliad.fu2019:")
      assert( oneWork.size == 22)
      val chunked:Vector[Corpus] = oneWork.chunkByCitation(1)
      assert ( chunked.size == 3 )
      assert ( chunked(0).size == 8)
      assert ( chunked(1).size == 6)
      assert ( chunked(2).size == 8)

  }
  it should "chunk into corpora by citation-value, on a 4-level text" in {
      val corp:Corpus = Corpus(cex1,"#")
      val vc:Vector[Corpus] = corp.chunkByText
      val oneWork:Corpus = corp ~= CtsUrn("urn:cts:fufolio:pope.iliad.fu2019_tokens:")
      assert( oneWork.size == 47)
      val chunked:Vector[Corpus] = oneWork.chunkByCitation(1)
      assert ( chunked.size == 6 )
      assert ( chunked(0).size == 8)
  }

  it should "chunk into corpora by citation-value, on a 4-level tokenized text" in {
      val corp:Corpus = Corpus(cex1,"#")
      val vc:Vector[Corpus] = corp.chunkByText
      val oneWork:Corpus = corp ~= CtsUrn("urn:cts:fufolio:pope.iliad.fu2019_tokens:")
      assert( oneWork.size == 47)
      val chunked:Vector[Corpus] = oneWork.chunkByCitation(2)
      assert ( chunked.size == 2 )
      assert ( chunked(0).size == 25)
      assert ( chunked(1).size == 22)
  }

  it should "not fail when the requested chunk-group exceeds the citation-depth" in {
      val corp:Corpus = Corpus(cex1,"#")
      val vc:Vector[Corpus] = corp.chunkByText
      val oneWork:Corpus = corp ~= CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:")
      assert( oneWork.size == 9)
      val chunked:Vector[Corpus] = oneWork.chunkByCitation(2)
      assert ( chunked.size == 1 )
      assert ( chunked(0).size == 9)
  }

}
