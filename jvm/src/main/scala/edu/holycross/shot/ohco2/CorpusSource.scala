package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.io.Source
import java.io._

/** A utility class for creating [[Corpus]] objects from
* various kinds of concrete sources available in the JVM,
* and serializing [[Corpus]] objects to various kinds of output.
*/
object CorpusSource {

  /*
  def fromString(cex: String,  delimiter: String = "#", cexHeader: Boolean = false): Corpus = {
    val lines = cex.split("\n").toVector
    val stringPairs =  if (cexHeader) {
      lines.tail.map(_.split(delimiter).toVector)
    } else {
      lines.map(_.split(delimiter).toVector)
    }

    val citableNodes = stringPairs.map( arr =>
      {
        try {
          val u = CtsUrn(arr(0))
          CitableNode(u, arr(1))
        } catch {
          case t: Throwable => {
            println("Failed on input line " + arr)
            throw t
          }
        }
      })
    Corpus(citableNodes)
  }*/

  /** Create a [[Corpus]] from a two-column delimited-text file.
  * @param f Name of the file.
  * @param delimiter String value of column delimiter.

  def fromFile(f: String, delimiter: String = "#", encoding: String = "UTF-8", cexHeader: Boolean = false): Corpus = {
    val txt = Source.fromFile(f, encoding).getLines.toVector.filter(_.nonEmpty).mkString("\n")
    fromString(txt, delimiter, cexHeader)
  }

  def fromUrl(url: String,  delimiter: String = "#", encoding: String = "UTF-8", cexHeader: Boolean = false): Corpus = {
    val txt = Source.fromURL(url, encoding).getLines.toVector.filter(_.nonEmpty).mkString("\n")
    fromString(txt, delimiter, cexHeader)
  }
  */


  /** Serialize a corpus to a two-column delimited-text file.
  *
  * @param c Corpus to serialize.
  * @param fName Name of file to write results to.
  * @param delimiter String value of column delimiter.

  def to2colFile(c: Corpus, fName: String, delimiter: String) {
    to2colFile(c, new File(fName), delimiter)
  }*/

  /** Serialize a corpus to a two-column delimited-text file.
  *
  * @param c Corpus to serialize.
  * @param f File object to write results to.
  * @param delimiter String value of column delimiter.

  def to2colFile(c: Corpus, f: File, delimiter: String) {
    val pw = new PrintWriter(f)
    pw.write(c.to2colString(delimiter) + "\n")
    pw.close
  }*/

  /** Serialize a corpus to a delimited-text in 82XF format.
  *
  * @param c Corpus to serialize.
  * @param f File object to write results to.
  * @param delimiter String value of column delimiter.

  def to82xfFile(c: Corpus, f: File, delimiter: String) {
    val pw = new PrintWriter(f)
    pw.write(c.to82xfString(delimiter) + "\n")
    pw.close
  }*/

  /** Serialize a corpus to a delimited-text in 82XF format.
  *
  * @param c Corpus to serialize.
  * @param fName Name of file to write results to.
  * @param delimiter String value of column delimiter.

  def to82xfFile(c: Corpus, fileName: String, delimiter: String) {
    to82xfFile(c, new File(fileName), delimiter)
  }*/

}
