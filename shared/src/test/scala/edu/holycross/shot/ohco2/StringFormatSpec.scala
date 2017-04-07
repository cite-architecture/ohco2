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

  it should "convert hocuspocus column format Strings to 2-column Strings" in {

    val hp = """urn:cts:greekLit:tlg0016.tlg001.grcTest:1.0#1##2#/tei:TEI/tei:text/tei:body/tei:div[@type='book' and @n='1']#<div xmlns="http://www.tei-c.org/ns/1.0" n="0" type="chapter"> <p> Ἡροδότου Ἁλικαρνησσέος ἱστορίης ἀπόδεξις ἥδε , ὡς μήτε τὰ γενόμενα ἐξ ἀνθρώπων τῷ χρόνῳ ἐξίτηλα γένηται , μήτε ἔργα μεγάλα τε καὶ θωμαστά , τὰ μὲν Ἕλλησι τὰ δὲ βαρβάροισι ἀποδεχθέντα , ἀκλεᾶ γένηται , τά τε ἄλλα καὶ δι᾽ ἣν αἰτίην ἐπολέμησαν ἀλλήλοισι . </p> </div>#/tei:TEI/tei:text/tei:body/tei:div[@type='book' and @n='?']/tei:div[@type='chapter' and @n='?']# xmlns:tei='http://www.tei-c.org/ns/1.0'
urn:cts:greekLit:tlg0016.tlg001.grcTest:1.1#2#1#3#/tei:TEI/tei:text/tei:body/tei:div[@type='book' and @n='1']#<div xmlns="http://www.tei-c.org/ns/1.0" n="1" type="chapter"> <p> Περσέων μέν νυν οἱ λόγιοι Φοίνικας αἰτίους φασὶ γενέσθαι τῆς διαφορῆς · τούτους γάρ , ἀπὸ τῆς Ἐρυθρῆς καλεομένης θαλάσσης ἀπικομένους ἐπὶ τήνδε τὴν θάλασσαν καὶ οἰκήσαντας τοῦτον τὸν χῶρον τὸν καὶ νῦν οἰκέουσι , αὐτίκα ναυτιλίῃσι μακρῇσι ἐπιθέσθαι , ἀπαγινέοντας δὲ φορτία Αἰγύπτιά τε καὶ Ἀσσύρια τῇ τε ἄλλῃ χώρῃ ἐσαπικνέεσθαι καὶ δὴ καὶ ἐς Ἄργος · τὸ δὲ Ἄργος τοῦτον τὸν χρόνον προεῖχε ἅπασι τῶν ἐν τῇ νῦν Ἑλλάδι καλεομένῃ χώρῃ . Ἀπικομένους δὲ τοὺς Φοίνικας ἐς δὴ τὸ Ἄργος τοῦτο διατίθεσθαι τὸν φόρτον . Πέμπτῃ δὲ ἢ ἕκτῃ ἡμέρῃ ἀπ᾽ ἧς ἀπίκοντο , ἐξεμπολημένων σφι σχεδὸν πάντων , ἐλθεῖν ἐπὶ τὴν θάλασσαν γυναῖκας ἄλλας τε πολλὰς καὶ δὴ καὶ τοῦ βασιλέος θυγατέρα · τὸ δέ οἱ οὔνομα εἶναι , κατὰ τὠυτὸ τὸ καὶ Ἕλληνες λέγουσι , Ἰοῦν τὴν Ἰνάχου . Ταύτας στάσας κατὰ πρύμνην τῆς νεὸς ὠνέεσθαι τῶν φορτίων τῶν σφι ἦν θυμὸς μάλιστα , καὶ τοὺς Φοίνικας διακελευσαμένους ὁρμῆσαι ἐπ᾽ αὐτάς . Τὰς μὲν δὴ πλέονας τῶν γυναικῶν ἀποφυγεῖν , τὴν δὲ Ἰοῦν σὺν ἄλλῃσι ἁρπασθῆναι · ἐσβαλομένους δὲ ἐς τὴν νέα οἴχεσθαι ἀποπλέοντας ἐπ᾽ Αἰγύπτου . </p> </div>#/tei:TEI/tei:text/tei:body/tei:div[@type='book' and @n='?']/tei:div[@type='chapter' and @n='?']# xmlns:tei='http://www.tei-c.org/ns/1.0'
"""
    val twos = twoColumnsFromHocusPocus(hp)
    assert (twos.split("\n").size == 2)

  }

}
