
import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import com.quantifind.charts.Highcharts._

// This script shows how to search a corpus, how to
// create ngrams and get histograms of their frequency
// in a corpus, and how to mix and match a few of these
// simpe functions productively.
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
val nHistoGr = corpus.ngramHisto(" γρ",n, threshhold, dropPunctuation)

// If you want to pre-filter by URN, do just create a selected corpus
// by twiddling.  This should be fast for any single work in the
// million-word corpus.
val hdt = corpus ~~ CtsUrn("urn:cts:greekLit:tlg0016.tlg001.grc:")
val hdtTrigr = hdt.ngramHisto(3,threshhold, dropPunctuation)


//val topWords = Array(("alpha", 14), ("beta", 23), ("omega", 18))
def ngramBar(histo: StringHistogram, max : Int) = {
  val topWords = histo.histogram.map(s => (s.s, s.count)).take(max).toArray
  val numberedColumns = column(topWords.map(_._2).toList)
  delete
  val axisType: com.quantifind.charts.highcharts.AxisType.Type = "category"
  val namedColumns = numberedColumns.copy(xAxis = numberedColumns.xAxis.map {
    axisArray => axisArray.map { _.copy(axisType = Option(axisType),
                                         categories = Option(topWords.map(_._1))) }
  })
  plot(namedColumns)
}
