package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._


/**
*/
class TokenSpec extends FlatSpec {


  "Classes implementing the Token  trais" should "deliver a CtiableNode from a citableNode function" in {
    case class TraitSpecImpl(cn: CitableNode) extends Token {
      def citableNode = cn
      def lexical = true
    }
    val demoCN =  CitableNode(CtsUrn("urn:cts:demo:units.tests.token1:1.1"), "word")
    val tsi = TraitSpecImpl(demoCN)
    assert(tsi.lexical)

  }


}
