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

/*
  def formatForString(s: String): DocumentFormat = {
    s match {
      case "xml" => Wf_Xml
      case "markdown" => Markdown
      case "twocol" => Two_Column
      case "82xf" => Oxf
    }
  }*/

  /** Find [[DocumentFormat]] value for
  * a String name.
  *
  * @param value String name of format.
  */
  def formatForString(value: String): Option[DocumentFormat] = {
    Vector(Wf_Xml, Two_Column, Markdown, Oxf).find(_.name == value)
  }
}
