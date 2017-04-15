package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._

import edu.holycross.shot.orca._

class CorpusSearchingPunctuationSpec extends FlatSpec {

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


  it should "support a short-hand function for searching word tokens" in pending
  it should "support a short-hand function for searching white-space delimited tokens" in pending
}
