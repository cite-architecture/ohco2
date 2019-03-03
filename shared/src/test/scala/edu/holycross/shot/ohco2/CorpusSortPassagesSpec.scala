package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class CorpusSortPassagesSpec extends FlatSpec {

val textDelimited = "urn:cts:ns:tg.w.v1:1.1#Version_1:1.1\n" +
  "urn:cts:ns:tg.w.v1:1.2#Version_1:1.2\n" +
  "urn:cts:ns:tg.w.v1:1.3#Version_1:1.3\n" +
  "urn:cts:ns:tg.w.v1:2.1#Version_1:2.1\n" +
  "urn:cts:ns:tg.w.v1:2.2#Version_1:2.2\n" +
  "urn:cts:ns:tg.w.v1:2.3#Version_1:2.3\n" +
  "urn:cts:ns:tg.w.v2:1.1#Version_2:1.1\n" +
  "urn:cts:ns:tg.w.v2:1.2#Version_2:1.2\n" +
  "urn:cts:ns:tg.w.v2:1.3#Version_2:1.3\n" +
  "urn:cts:ns:tg.w.v2:2.1#Version_2:2.1\n" +
  "urn:cts:ns:tg.w.v2:2.2#Version_2:2.2\n" +
  "urn:cts:ns:tg.w.v2:2.3#Version_2:2.3\n" +
  "urn:cts:ns:tg.w.v1.ex1:1.1.1#Version_1_exemp_1:1.1.1\n" +
  "urn:cts:ns:tg.w.v1.ex1:1.1.2#Version_1_exemp_1:1.1.2\n" +
  "urn:cts:ns:tg.w.v1.ex1:1.1.3#Version_1_exemp_1:1.1.3\n" +
  "urn:cts:ns:tg.w.v1.ex1:1.2.1#Version_1_exemp_1:1.2.1\n" +
  "urn:cts:ns:tg.w.v1.ex1:1.2.2#Version_1_exemp_1:1.2.2\n" +
  "urn:cts:ns:tg.w.v1.ex1:1.2.3#Version_1_exemp_1:1.2.3\n" +
  "urn:cts:ns:tg.w.v1.ex1:1.3.1#Version_1_exemp_1:1.3.1\n" +
  "urn:cts:ns:tg.w.v1.ex1:1.3.2#Version_1_exemp_1:1.3.2\n" +
  "urn:cts:ns:tg.w.v1.ex1:1.3.3#Version_1_exemp_1:1.3.3\n" +
  "urn:cts:ns:tg.w.v1.ex1:2.1.1#Version_1_exemp_1:2.1.1\n" +
  "urn:cts:ns:tg.w.v1.ex1:2.1.2#Version_1_exemp_1:2.1.2\n" +
  "urn:cts:ns:tg.w.v1.ex1:2.1.3#Version_1_exemp_1:2.1.3\n" +
  "urn:cts:ns:tg.w.v1.ex1:2.2.1#Version_1_exemp_1:2.2.1\n" +
  "urn:cts:ns:tg.w.v1.ex1:2.2.2#Version_1_exemp_1:2.2.2\n" +
  "urn:cts:ns:tg.w.v1.ex1:2.2.3#Version_1_exemp_1:2.2.3\n" +
  "urn:cts:ns:tg.w.v1.ex1:2.3.1#Version_1_exemp_1:2.3.1\n" +
  "urn:cts:ns:tg.w.v1.ex1:2.3.2#Version_1_exemp_1:2.3.2\n" +
  "urn:cts:ns:tg.w.v1.ex1:2.3.3#Version_1_exemp_1:2.3.3\n" +
  "urn:cts:ns:tg.w.v1.ex2:1.1.1#Version_1_exemp_2:1.1.1\n" +
  "urn:cts:ns:tg.w.v1.ex2:1.1.2#Version_1_exemp_2:1.1.2\n" +
  "urn:cts:ns:tg.w.v1.ex2:1.1.3#Version_1_exemp_2:1.1.3\n" +
  "urn:cts:ns:tg.w.v1.ex2:1.2.1#Version_1_exemp_2:1.2.1\n" +
  "urn:cts:ns:tg.w.v1.ex2:1.2.2#Version_1_exemp_2:1.2.2\n" +
  "urn:cts:ns:tg.w.v1.ex2:1.2.3#Version_1_exemp_2:1.2.3\n" +
  "urn:cts:ns:tg.w.v1.ex2:1.3.1#Version_1_exemp_2:1.3.1\n" +
  "urn:cts:ns:tg.w.v1.ex2:1.3.2#Version_1_exemp_2:1.3.2\n" +
  "urn:cts:ns:tg.w.v1.ex2:1.3.3#Version_1_exemp_2:1.3.3\n" +
  "urn:cts:ns:tg.w.v1.ex2:2.1.1#Version_1_exemp_2:2.1.1\n" +
  "urn:cts:ns:tg.w.v1.ex2:2.1.2#Version_1_exemp_2:2.1.2\n" +
  "urn:cts:ns:tg.w.v1.ex2:2.1.3#Version_1_exemp_2:2.1.3\n" +
  "urn:cts:ns:tg.w.v1.ex2:2.2.1#Version_1_exemp_2:2.2.1\n" +
  "urn:cts:ns:tg.w.v1.ex2:2.2.2#Version_1_exemp_2:2.2.2\n" +
  "urn:cts:ns:tg.w.v1.ex2:2.2.3#Version_1_exemp_2:2.2.3\n" +
  "urn:cts:ns:tg.w.v1.ex2:2.3.1#Version_1_exemp_2:2.3.1\n" +
  "urn:cts:ns:tg.w.v1.ex2:2.3.2#Version_1_exemp_2:2.3.2\n" +
  "urn:cts:ns:tg.w.v1.ex2:2.3.3#Version_1_exemp_2:2.3.3"
  val corpus = Corpus(textDelimited,"#")


  "A Corpus"  should  "offer a method to sort an Iterable[CtsUrn]" in {
    val passages:Set[CtsUrn] = Set(
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.3"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.2"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.1")
    )
    val sorted:Vector[CtsUrn] = Vector(

      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.1"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.2"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.3")
    )
    assert(corpus.sortPassages(passages) == sorted)
  }

  it should  "offer a method to sort an Iterable[CtsUrn] when some of the citations are ranges" in {
    val passages:Set[CtsUrn] = Set(
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.3"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.2"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.1"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.2-1.1.3")
    )
    val sorted:Vector[CtsUrn] = Vector(

      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.2"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.3"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.1"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.2"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.3")
    )
    assert(corpus.sortPassages(passages) == sorted)
  }

  it should "compress a vector of URNs by converting continuous runs to ranges where possible" in {
    val passages:Vector[CtsUrn] = Vector(
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.1"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.2"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.3"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.1"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.3")
    )
    val compressed:Vector[CtsUrn] = Vector(
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.1.1-1.2.1"),
      CtsUrn("urn:cts:ns:tg.w.v1.ex1:1.2.3")
    )
    assert(corpus.compressReff(passages) == compressed)

  }



}
