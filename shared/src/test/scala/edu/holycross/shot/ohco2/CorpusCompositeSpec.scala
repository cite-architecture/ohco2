package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class CorpusCompositeSpec extends FlatSpec {

  val piece1 = "urn:cts:ns:tg.w.v1:1.1#Version_1:1.1\n" +
  "urn:cts:ns:tg.w.v1:1.2#Version_1:1.2\n" +
  "urn:cts:ns:tg.w.v1:1.3#Version_1:1.3\n"
  val piece2 = "urn:cts:ns:tg.w.v1:2.1#Version_1:2.1\n" +
  "urn:cts:ns:tg.w.v1:2.2#Version_1:2.2\n" +
  "urn:cts:ns:tg.w.v1:2.3#Version_1:2.3\n"
  val piece3 = "urn:cts:ns:tg.w.v2:1.1#Version_2:1.1\n" +
  "urn:cts:ns:tg.w.v2:1.2#Version_2:1.2\n" +
  "urn:cts:ns:tg.w.v2:1.3#Version_2:1.3\n"




  "The Corpus object"  should  "create a composite Corpus from a Vector of corpora" in  {
    val c1 = Corpus(piece1,"#")
    assert (c1.size == 3)
    val c2 = Corpus(piece2,"#")
    assert (c2.size == 3)
    val c3 = Corpus(piece3,"#")
    assert (c3.size == 3)

    val corpusVector = Vector(c1, c2, c3)
    val composite = Corpus.composite(corpusVector)
    assert(composite.size == 9)

  }


}
