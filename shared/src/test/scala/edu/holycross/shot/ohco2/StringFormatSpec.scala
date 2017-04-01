package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._


/**
*/
class StringFormatSpec extends FlatSpec {

val sample82XF = """urn:cts:aflibre:aflibre.ah.hc:19420614.h1##1#urn:cts:aflibre:aflibre.ah.hc:19420614.p1#Zondag, 14 Juni 1942
urn:cts:aflibre:aflibre.ah.hc:19420614.p1#urn:cts:aflibre:aflibre.ah.hc:19420614.h1#2#urn:cts:aflibre:aflibre.ah.hc:19420614.p2#Vrijdag 12 Juni was ik al om 6 uur wakker en dat is heel begrijpelijk, daar ik jarig was. Maar om 6 uur mocht ik toch nog niet opstaan, dus moest ik mijn nieuwsgierigheid bedwingen tot kwart voor zeven. Toen ging het niet langer, ik ging naar de eetkamer, waar ik door Moortje (de kat) met kopjes verwelkomd werd.
urn:cts:aflibre:aflibre.ah.hc:19420614.p2#urn:cts:aflibre:aflibre.ah.hc:19420614.p1#3#urn:cts:aflibre:aflibre.ah.hc:19420614.p3#Om even na zevenen ging ik naar papa en mama en dan naar de huiskamer, om mijn cadeautjes uit te pakken. Het was in de eerste plaats jou die ik te zien kreeg, wat misschien wel een van mijn jnste cadeau's is. Dan een bos rozen, een plantje, twee takken pinkster-rozen, dat waren die ochtend de kinderen van Flora, die op mijn tafel stonden, maar er kwam nog veel meer.
"""

  "The package object" should "convert 82xf Strings to 2-column Strings" in {
    val twos = twoColumnsFrom82xf(sample82XF)
    assert (twos.split("\n").size == 3)
  }

  it should "convert hocuspocus column format Strings to 2-column Strings" in pending

}
