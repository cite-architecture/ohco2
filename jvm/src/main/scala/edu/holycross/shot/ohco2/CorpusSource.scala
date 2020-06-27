package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.io.Source
import java.io._


import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter


/** A utility class for creating [[Corpus]] objects from
* various kinds of concrete sources available in the JVM,
* and serializing [[Corpus]] objects to various kinds of output.
*/
object CorpusSource extends LogSupport {

  def fromString(cex: String,  delimiter: String = "#", cexHeader: Boolean = false): Corpus = {
    val lines = cex.split("\n").toVector
    val stringPairs =  if (cexHeader) {
      lines.tail.map(_.split(delimiter).toVector).toVector
    } else {
      lines.map(_.split(delimiter).toVector).toVector
    }

    val citableNodes = stringPairs.map( lines => {

      // first col. should always be URN:
      val u = try  { CtsUrn(lines(0)) } catch {
        case t: Throwable => {
          warn(s"CorpusSource: unable to parse URN in ${lines(0)} from text line ${lines}")
          throw t
        }
      }

      val cn = lines.size match {
        case 2 => CitableNode(u, lines(1))
        case 1 => CitableNode(u, "")
        case _ => {
          val msg = s"Corpus source. Bad input: " + lines + ". Wrong number of columns ${lines.size}."
          warn(msg)
          throw new Exception(msg)
        }}

      cn
    })
    Corpus(citableNodes)
  }

  /** Create a [[Corpus]] from a two-column delimited-text file.
  * @param f Name of the file.
  * @param delimiter String value of column delimiter.
  */
  def fromFile(f: String, delimiter: String = "#", encoding: String = "UTF-8", cexHeader: Boolean = false): Corpus = {
    val txt = Source.fromFile(f, encoding).getLines.toVector.filter(_.nonEmpty).mkString("\n")
    fromString(txt, delimiter, cexHeader)
  }

  def fromUrl(url: String,  delimiter: String = "#", encoding: String = "UTF-8", cexHeader: Boolean = false): Corpus = {
    val txt = Source.fromURL(url, encoding).getLines.toVector.filter(_.nonEmpty).mkString("\n")
    fromString(txt, delimiter, cexHeader)
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
