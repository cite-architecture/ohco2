package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.io.Source
import java.io._





/** A utility class for creating [[Corpus]] objects from
* various kinds of concrete sources available in the JVM,
* and serializing [[Corpus]] objects to various kinds of output.
*/
object CorpusSource {

  /** Create a [[Corpus]] from a two-column delimited-text file.
  * @param f Name of the file.
  * @param delimiter String value of column delimiter.
  */
  def fromFile(f: String, delimiter: String = "#", encoding: String = "UTF-8", cexHeader: Boolean = false): Corpus = {

    val lines = Source.fromFile(f, encoding).getLines.toVector.filter(_.nonEmpty)


    val stringPairs =  if (cexHeader) {
      lines.tail.map(_.split(delimiter))
    } else {
      lines.map(_.split(delimiter))
    }

    val citableNodes = stringPairs.map( arr => CitableNode(CtsUrn(arr(0)), arr(1)))
    Corpus(citableNodes)
  }


  /** Serialize a corpus to a two-column delimited-text file.
  *
  * @param c Corpus to serialize.
  * @param fName Name of file to write results to.
  * @param delimiter String value of column delimiter.
  */
  def to2colFile(c: Corpus, fName: String, delimiter: String) {
    to2colFile(c, new File(fName), delimiter)
  }

  /** Serialize a corpus to a two-column delimited-text file.
  *
  * @param c Corpus to serialize.
  * @param f File object to write results to.
  * @param delimiter String value of column delimiter.
  */
  def to2colFile(c: Corpus, f: File, delimiter: String) {
    val pw = new PrintWriter(f)
    pw.write(c.to2colString(delimiter) + "\n")
    pw.close
  }

  /** Serialize a corpus to a delimited-text in 82XF format.
  *
  * @param c Corpus to serialize.
  * @param f File object to write results to.
  * @param delimiter String value of column delimiter.
  */
  def to82xfFile(c: Corpus, f: File, delimiter: String) {
    val pw = new PrintWriter(f)
    pw.write(c.to82xfString(delimiter) + "\n")
    pw.close
  }

  /** Serialize a corpus to a delimited-text in 82XF format.
  *
  * @param c Corpus to serialize.
  * @param fName Name of file to write results to.
  * @param delimiter String value of column delimiter.
  */
  def to82xfFile(c: Corpus, fileName: String, delimiter: String) {
    to82xfFile(c, new File(fileName), delimiter)
  }

}
