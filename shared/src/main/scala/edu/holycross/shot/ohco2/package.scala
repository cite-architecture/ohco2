package edu.holycross.shot

package object ohco2 {

  import edu.holycross.shot.cite._

  /** Create two-column OHCO2 representation from
  * a string in 82XF form. Note that 82XF input
  * must already be ordered.
  *
  * @param xf String in 82XF form.
  * @param inputDelimiter String delimiting columns
  * of 82XF input.
  * @param outputDelimiter String to use to delimit
  * columns in two-column output.
  */
  def twoColumnsFrom82xf(xf: String, inputDelimiter: String = "#", outputDelimiter: String = "#"): String = {
    val lines = xf.split("\n").toVector.map(_.split(inputDelimiter))


    val twoColumns = lines.map(v => {
      v.size match {
        case n if n == 5 =>  v(0) + outputDelimiter + v(4)
        //case n if n ==
        case _ => ""
        }
      }
    )
    twoColumns.mkString("\n")
  }

  /** Create [[OnlineDocument]] from its
  * representation in a text configuraiton file
  * as an XML `online` element.
  *
  * @param n Parsed `online` node.
  */
  def documentFromNode(n: scala.xml.Node, baseDirectory: String) : OnlineDocument = {
    val urnSeq = n \\ "@urn"
    val typeSeq = n \\ "@type"
    val nameSeq = n \\ "@docname"

    val urn = CtsUrn(urnSeq(0).text)
    val format = formatFromString(typeSeq(0).text).get
    val fName = fileName(baseDirectory, nameSeq(0).text)

    OnlineDocument(urn,format,fName)
  }

  /** Format directory plus file as a path String.
  *
  * @param dir Directory name.
  * @param f File name
  */
  def fileName(dir: String, f: String) : String = {
    if (dir.last == '/') {
      dir + f
    } else {
      dir + "/" + f
    }
  }


  /** Find [[DocumentFormat]] value for
  * a String name.
  *
  * @param value String name of format.
  */
  def formatFromString(value: String): Option[DocumentFormat] = {
    Vector(Wf_Xml, Two_Column, Markdown, Oxf).find(_.name == value)
  }


  /** Create a vector of [[OnlineDocument]]s from a cataloged
  * set of files in a local file system.
  *
  * @param configFileName Name of file with details about location and format of files.
  * @param baseDirectoryName Name of root directory where local files are found.
  */
  def onlineVector(
    configFileName: String,
    baseDirectoryName: String
  ): Vector[OnlineDocument] = {
    val configRoot = scala.xml.XML.loadFile(configFileName)
    val files = configRoot \\ "online"
    val docs = files.map(documentFromNode(_, baseDirectoryName))
    docs.toVector
  }

}
