package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._
import scala.io.Source
import java.io._


class CorpusUrlSourceSpec extends FlatSpec {

  "The CorpusSource object"  should "instantiate a corpus from a URL to a CEX source" in {
    val srcUrl = "https://raw.githubusercontent.com/neelsmith/nomisma/master/cex/ric-1-3-cts.cex"
    val coins = CorpusSource.fromUrl(srcUrl)

    assert(coins.size > 27000)
  }


}
