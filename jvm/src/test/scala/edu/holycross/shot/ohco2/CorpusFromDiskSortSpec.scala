package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._




class CorpusFromDiskSortSpec extends FlatSpec {
  val pgUrns = Vector(
  CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msB:9.222"),
  CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msB:9.217"),
  CtsUrn("urn:cts:greekLit:tlg5026.msB.hmt:9.msB_119r_6"),
  CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msB:9.231"),
  CtsUrn("urn:cts:greekLit:tlg5026.msB.hmt:9.msB_119r_5"),
  CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msB:9.236"),
  CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msB:9.213"),
  CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msB:9.230"),
  CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msB:9.229"),
  CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msB:9.218"),
  CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msB:9.227"),
  CtsUrn("urn:cts:greekLit:tlg5026.msB.hmt:9.msB_119r_14"),
  CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msB:9.232"),
  CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msB:9.234"),
  CtsUrn("urn:cts:greekLit:tlg5026.msB.hmt:9.msB_119r_2"),
  CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msB:9.216"),
  CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msB:9.221"),
  CtsUrn("urn:cts:greekLit:tlg5026.msB.hmt:9.msB_119r_11"),
  CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msB:9.220"),
  CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msB:9.235"),
  CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msB:9.219"),
  CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msB:9.215"),
  CtsUrn("urn:cts:greekLit:tlg5026.msB.hmt:9.msB_119r_7"),
  CtsUrn("urn:cts:greekLit:tlg5026.msB.hmt:9.msB_119r_12"),
  CtsUrn("urn:cts:greekLit:tlg5026.msB.hmt:9.msB_119r_9"),
  CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msB:9.226"),
  CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msB:9.228"),
  CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msB:9.224"),
  CtsUrn("urn:cts:greekLit:tlg5026.msB.hmt:9.msB_119r_3"),
  CtsUrn("urn:cts:greekLit:tlg5026.msB.hmt:9.msB_119r_15"),
  CtsUrn("urn:cts:greekLit:tlg5026.msB.hmt:9.msB_119r_1"),
  CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msB:9.214"),
  CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msB:9.225"),
  CtsUrn("urn:cts:greekLit:tlg5026.msB.hmt:9.msB_119r_10"),
  CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msB:9.233"),
  CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msB:9.223"),
  CtsUrn("urn:cts:greekLit:tlg5026.msB.hmt:9.msB_119r_13"),
  CtsUrn("urn:cts:greekLit:tlg5026.msB.hmt:9.msB_119r_8"),
  CtsUrn("urn:cts:greekLit:tlg5026.msB.hmt:9.msB_119r_16"),
  CtsUrn("urn:cts:greekLit:tlg5026.msB.hmt:9.msB_119r_4")
  )
  "A Corpus" should "correctly handle whatever is going on in HMT Iliad 9 work" in pending /*{
    val baseDir = "jvm/src/test/resources/twins-alpha/editions"
    val corpus = TextRepositorySource.fromFiles(baseDir + "/catalog.cex", baseDir + "/citation.cex", baseDir).corpus
    println("CORPUS OF " + corpus.size + " nodes")
    println("Sort list of " + pgUrns.size + " reff")
    val sorted = corpus.sortPassages(pgUrns)
    println("Result:\n " + sorted.mkString("\n"))
  }*/
}
