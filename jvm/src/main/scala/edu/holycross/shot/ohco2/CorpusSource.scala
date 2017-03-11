package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.io.Source
import java.io._






object CorpusSource {
  def fromFile(f: String, delimiter: String = "\t"): Corpus = {
    val stringPairs = Source.fromFile(f).getLines.toVector.filter(_.nonEmpty).map(_.split(delimiter))
    val citableNodes = stringPairs.map( arr => CitableNode(CtsUrn(arr(0)), arr(1)))
    Corpus(citableNodes)
  }


  def to2colFile(c: Corpus, fName: String, delimiter: String) {
    to2colFile(c, new File(fName), delimiter)
  }
  def to2colFile(c: Corpus, f: File, delimiter: String) {
    val pw = new PrintWriter(f)
    pw.write(c.to2colString(delimiter) + "\n")
    pw.close
  }
  def to82xfFile(c: Corpus, f: File, delimiter: String) {
    val pw = new PrintWriter(f)
    pw.write(c.to82xfString(delimiter) + "\n")
    pw.close
  }
  def to82xfFile(c: Corpus, fileName: String, delimiter: String) {
    to82xfFile(c, new File(fileName), delimiter)
  }

}
