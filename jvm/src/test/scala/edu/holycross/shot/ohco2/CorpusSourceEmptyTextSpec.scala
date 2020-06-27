package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._
import scala.io.Source
import java.io._


class CorpusSourceEmptyTextSpec extends FlatSpec {

  val srcFile = "jvm/src/test/resources/empty-text-nodes.cex"

  "Corpus file IO"  should "load a delimited text file with empty text nodes" in {
    val corpus = CorpusSource.fromFile(srcFile)
    val total = corpus.size
    val nonEmpty = corpus.nodes.filter(_.text.nonEmpty).size
    assert(total > nonEmpty) 
  }


}
