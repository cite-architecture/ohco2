package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._

import edu.holycross.shot.orca._

class CorpusSearchingPunctuationSpec extends FlatSpec {

  "A corpus of citable nodes"  should   "support optionally ignoring punctuation in searching for whole word tokens" in {
    val proem = "urn:cts:greekLit:tlg0016.tlg001.eng:1.0#This is the Showing forth of the Inquiry of Herodotus of Halicarnassos, to the end that neither the deeds of men may be forgotten by lapse of time, nor the works great and marvellous, which have been produced some by Hellenes and some by Barbarians, may lose their renown; and especially that the causes may be remembered for which these waged war with one another"
    val proemCorpus = Corpus(proem, "#")

    val bareToken = "Halicarnassos"
    val bareTokenLiteralMatch = proemCorpus.findToken(bareToken, false)
    assert(bareTokenLiteralMatch.size == 0)
    val bareTokenIgnorePunct = proemCorpus.findToken(bareToken, true)
    assert(bareTokenIgnorePunct.size == 1)

    val punctuatedToken  ="Halicarnassos,"
    val punctuatedTokenLiteralMatch = proemCorpus.findToken(punctuatedToken, false)
    assert(punctuatedTokenLiteralMatch.size == 1)
    val punctuatedTokeIgnorePunctuation = proemCorpus.findToken(punctuatedToken, true)
    assert(punctuatedTokeIgnorePunctuation.size == 0)
  }
}
