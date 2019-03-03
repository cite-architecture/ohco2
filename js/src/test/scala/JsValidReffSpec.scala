package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.scalajs.js
import scala.scalajs.js._
import org.scalatest._

class JsValidReffSpec extends FlatSpec {

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

  "The ohco2 library"  should "perform well in ScalaJS with validReff()" in {

    val urn1:CtsUrn = CtsUrn("urn:cts:ns:tg.w.v1:1.2")
    val urn2:CtsUrn = CtsUrn("urn:cts:ns:tg.w.v1:1.1-1.2")
    val urn3:CtsUrn = CtsUrn("urn:cts:ns:tg.w.v1:1")
    val urn4:CtsUrn = CtsUrn("urn:cts:ns:tg.w.v1:")

    val sv:Vector[CtsUrn] = Vector(urn1, urn2, urn3, urn4) 

    val uv:Vector[CtsUrn] = {
        (1 to 2).map( i => {
            sv
        }).toVector.flatten
    }

    val startTime1 = new js.Date().getTime 
    for (u <- uv) {
      val v = corpus.validReff(u)
    }
    val endTime1 = new js.Date().getTime 
    //println("------")
    //println(s"new validReff in ${(endTime1 - startTime1) / 1000 }.")
    val startTime2 = new js.Date().getTime 
    for (u <- uv) {
      val v = corpus.validReff2(u)
    }
    val endTime2 = new js.Date().getTime 
    //println("------")
    //println(s"old validReff in ${(endTime2 - startTime2) / 1000 }.")
    assert ( true )
    
  }

}
