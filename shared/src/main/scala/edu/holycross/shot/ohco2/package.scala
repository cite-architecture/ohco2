package edu.holycross.shot

package object ohco2 {

  import edu.holycross.shot.cite._

  /** Regular expression defining recognized punctuation characters.
  * The list incudes all characters with the Unicode `Punctuation` prooerty
  * plus a selection of other non-alphabetic characters known to be
  * used as punctuation values in some digital editions.
  */
  val punctuationListRE = """[!"#$%&*+,-./:;?@^_`|~···,‡·—-]""".r

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


  /** Create two-column OHCO2 representation from
  * a string formatted in the tabular format of the hocuspocus class. Note that input
  * must already be ordered.
  *
  * @param xf String in tabular form used by hocuspocus.
  * @param inputDelimiter String delimiting columns
  * of hocuspocus input.
  * @param outputDelimiter String to use to delimit
  * columns in two-column output.
  */
  def twoColumnsFromHocusPocus(hpString: String, inputDelimiter: String = "#", outputDelimiter: String = "#"): String = {
    val lines = hpString.split("\n").toVector.map(_.split(inputDelimiter).toVector)

    val twoColumns = lines.map(v => {
      v.size match {
        case n if n == 8 =>  {
          val two = v(0) + outputDelimiter + v(5)
          two
        }
        case firstlast if firstlast == 7 =>  {
          val two = v(0) + outputDelimiter + v(4)
          two
        }
        //ignore empty or ill-formed lines
        case _ => ""
        }
      }
    )
    twoColumns.mkString("\n")
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


  /** Find [[DocumentFormat]] value for a String name.
  *
  * @param value String name of format.
  */
  def formatForString(value: String): Option[DocumentFormat] = {
    Vector(Wf_Xml, Two_Column, Markdown, Oxf).find(_.name == value)
  }
}
