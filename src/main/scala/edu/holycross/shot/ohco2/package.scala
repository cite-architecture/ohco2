
package org.homermultitext



/** Provides classes modelling HMT editions of texts.
 *
 *  ==Overview==
 *  The starting point is the factory object [[org.homermultitext.edmodel.TeiReader]], that can read a two-column OHCO2 file to produce a Vector of tuples, pairing a CtsUrn for the citable text node with a [[org.homermultitext.edmodel.HmtToken]].  Example:
 *  {{{
 *  val tokenPairs = TeiReader.fromTwoColumns("SOURCEFILENAME.tsv")
 *  }}}
 *
 * The crucial structure is the [[org.homermultitext.edmodel.HmtToken]], which
 * captures everything known about a token from an HMT edition.
 */
 package object edmodel {

   import java.text.Normalizer.Form
   import java.text.Normalizer
   import edu.holycross.shot.cite._

   val collectionId = "urn:cite:hmt:urtoken"
   val versionId = "v1"

   case class ReadingConfig(title: String, description: String)


   val exemplarLabels = Map(
     "dipl" -> ReadingConfig("Pure diplomatic reading","description"),
     "ednorm" -> ReadingConfig("Editorially normalized, morphologically parseable reading","description"),
     "token" -> ReadingConfig("Full analysis of HMT tokens","description"),
     "tm" -> ReadingConfig("Reading optimized for topic modelling","description")
   )

   val analyticalCollections = Map(
     "tlg0012.tlg001.msA_tkns"-> CiteUrn("urn:cite:hmt:va_il_tkns"),

     "tlg5026.msA.hmt_tkns" -> CiteUrn("urn:cite:hmt:va_schA_tkns"),
     "tlg5026.msAim.hmt_tkns" -> CiteUrn("urn:cite:hmt:va_schAim_tkns"),
     "tlg5026.msAint.hmt_tkns" -> CiteUrn("urn:cite:hmt:va_schAint_tkns"),
     "tlg5026.msAext.hmt_tkns" -> CiteUrn("urn:cite:hmt:va_schAext_tkns"),
     "tlg5026.msAil.hmt_tkns" -> CiteUrn("urn:cite:hmt:va_schAil_tkns"),
     "tlg5026.msAimlater.hmt_tkns" -> CiteUrn("urn:cite:hmt:va_schAimlater_tkns")


   )




  // perhaps should be a function retrieving
  // list by text group and lexical category?
  val punctuation = Vector(",",".",";",":","⁑")

  // only need list of elements *not* explicitly
  // caught in big case match below
  val validElements = Vector(
    "div", "l","p", "choice",
    "foreign",
    "num",
    "unclear","add","orig","reg","sic","corr",
    "abbr","expan",
    "cite","q","ref",
    "persName","placeName",
    "rs"
  )



  /** Recursively collects contents of all text-node
  * descendants of a given node.
  * @param n Node to collect from.
  * @param buff Buffer for collecting text contents.
  * @return A single String with all text from n.
  */
  def collectText(n: xml.Node, s: String): String = {
    var buff = StringBuilder.newBuilder
    buff.append(s)
    n match {
      case t: xml.Text => {
        buff.append(t.text)
      }

      case e: xml.Elem => {
        for (ch <- e.child) {
          buff = new StringBuilder(collectText(ch, buff.toString))
        }
      }
    }
    buff.toString
  }

  def collectText(n: xml.Node): String = {
    collectText(n,"")
  }


  def hmtNormalize(s: String): String = {
    Normalizer.normalize(s,Form.NFC).trim.replaceAll("[ ]+"," ")
  }
}
