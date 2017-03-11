
import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._

// This script shows how to create ngrams and get histograms
// of their frequency in a corpus.
//
// We'll use the "million-words" corpus:
val srcFile = "shared/src/test/resources/million.tsv"
val corpus = CorpusSource.fromFile(srcFile,"\t")



// Settings:
val n = 2
val threshhold = 3
val dropPunctuation = true
// For the million word histo, this may take a moment, depending
// in part on value you choose for n
val nHisto = corpus.ngramHisto(n, threshhold, dropPunctuation)

//BUT... searching is almost instantaneous and returns a new
// corpus, so this is wicked fast:
val gr = corpus.find(" γρ")
val grBigraHist = gr.ngramHisto(n,threshhold, dropPunctuation)



//val nHistoGr = corpus.ngramHisto(n, threshhold, dropPunctuation," γρ")
