package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._


/**
*/
class CorpusFlatteningSpec extends FlatSpec {

  val tokenCorpusSrc =  """urn:cts:demo:g.w.v.token:1.1#Now
urn:cts:demo:g.w.v.token:1.2#is
urn:cts:demo:g.w.v.token:1.3#the
urn:cts:demo:g.w.v.token:1.4#time.
urn:cts:demo:g.w.v.token:2.1#Tomorrow
urn:cts:demo:g.w.v.token:2.2#is
urn:cts:demo:g.w.v.token:2.3#too
urn:cts:demo:g.w.v.token:2.4#late.
"""



  "A Corpus"  should "be able to generate a new corpus by flattening exemplar-level nodes to version-level nodes" in {

    val corpus = Corpus(tokenCorpusSrc,"#")
    val flattened = corpus.exemplarToVersion("flatVersion")
    println(flattened.nodes.mkString("\n"))

    assert(corpus.size == 8)
    assert(flattened.size == 2)
    assert(flattened.nodes(0).text == "Now is the time.")
    assert(flattened.nodes(1).text == "Tomorrow is too late.")
  }
}
