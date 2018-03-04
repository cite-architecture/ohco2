package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class CorpusSearchingPunctuationSpec extends FlatSpec {

    val puncText:String = """urn:cts:greekLit:tlg0012.tlg001.perseus_grc2.tokens:1.1.1#μῆνιν
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2.tokens:1.1.2#ἄειδε
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2.tokens:1.1.3#θεὰ
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2.tokens:1.1.4#Πηληϊάδεω
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2.tokens:1.1.5#Ἀχιλῆος
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2.tokens:1.2.1#οὐλομένην
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2.tokens:1.2.2#,
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2.tokens:1.2.3#ἣ
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2.tokens:1.2.4#μυρίʼ
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2.tokens:1.2.5#Ἀχαιοῖς
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2.tokens:1.2.6#ἄλγεʼ
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2.tokens:1.2.7#ἔθηκε
urn:cts:greekLit:tlg0012.tlg001.perseus_grc2.tokens:1.2.8#,
"""
  val punc = Corpus(puncText, "#")
  val proemCex = "urn:cts:greekLit:tlg0016.tlg001.eng:1.0#This is the Showing forth of the Inquiry of Herodotus of Halicarnassos, to the end that neither the deeds of men may be forgotten by lapse of time, nor the works great and marvellous, which have been produced some by Hellenes and some by Barbarians, may lose their renown; and especially that the causes may be remembered for which these waged war with one another"
  val proem = Corpus(proemCex, "#")

  "A corpus of citable nodes"  should   "support optionally ignoring punctuation in searching for whole word tokens" in {

    val bareToken = "Halicarnassos"
    val bareTokenLiteralMatch = proem.findToken(bareToken, false)
    assert(bareTokenLiteralMatch.size == 0)
    val bareTokenIgnorePunct = proem.findToken(bareToken, true)
    assert(bareTokenIgnorePunct.size == 1)

    val punctuatedToken  ="Halicarnassos,"
    val punctuatedTokenLiteralMatch = proem.findToken(punctuatedToken, false)
    assert(punctuatedTokenLiteralMatch.size == 1)
    val punctuatedTokeIgnorePunctuation = proem.findToken(punctuatedToken, true)
    assert(punctuatedTokeIgnorePunctuation.size == 0)
  }

  it should "not fail if a node has only punctuation" in {
    val realToken = "ἔθηκε" 
    val puncToken = ","
    val realTokenMatch = punc.findToken(realToken,true)
    assert(realTokenMatch.size == 1)
    val realTokenLitMatch = punc.findToken(realToken,false)
    assert(realTokenMatch.size == 1)

    val puncTokenMatch = punc.findToken(puncToken,true)
    assert(puncTokenMatch.size == 0)
    val puncTokenLitMatch = punc.findToken(puncToken,false)
    assert(puncTokenLitMatch.size == 2)
  }

  it should "support a short-hand function for searching word tokens" in pending
  it should "support a short-hand function for searching white-space delimited tokens" in pending
}
