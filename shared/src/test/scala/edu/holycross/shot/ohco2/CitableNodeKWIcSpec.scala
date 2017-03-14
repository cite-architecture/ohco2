package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class CitableNodeKWICSpec extends FlatSpec {
  val hdtproem = CitableNode(CtsUrn("urn:cts:greekLit:tlg0016.tlg001.eng:1.0"),  "This is the Showing forth of the Inquiry of Herodotus of Halicarnassos , to the end that neither the deeds of men may be forgotten by lapse of time , nor the works great and marvellous , which have been produced some by Hellenes and some by Barbarians , may lose their renown ; and especially that the causes may be remembered for which these waged war with one another .")



  "A citable text node" should "offer a KWIC display for leading text" in {
    assert (hdtproem.kwic("This is") == "**This is** the Showing forth o...")
  }

  it should "allow specifiying context size in KWIC display" in{
    assert (hdtproem.kwic("This is",3) == "**This is** th...")
  }
  it should "gracefully handle context size greater than preceding text" in {
     assert (hdtproem.kwic(" is",18) == "This** is** the Showing forth...")
   }

  it should "offer a KWIC display for trailing text" in {
    assert (hdtproem.kwic("with one another .") == "...ich these waged war **with one another .**")
  }

  it should "gracefully handle context size greater than trailing text" in {
    assert (hdtproem.kwic("with one another") == "...ich these waged war **with one another**" + " .")
  }

  it should "gracefully handle context size equal to trailing text size" in {
    assert (hdtproem.kwic("with one another",2) == "...r **with one another**" + " .")
  }


}
