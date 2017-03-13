package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.io.Source
import java.io._

import com.quantifind.charts.Highcharts._




/** Create objects to use with WISP (https://github.com/quantifind/wisp)
* for plotting OHCO2 data objects from within a scala console.
*
* WISP automatically starts up a plot server. Before quitting a console
* session using WISP, it is best to stop the server with the command
* `stopServer` in the console.
*/
object WispDriver {

  /**  Plot a bar graph of a histogram.
  *
  * @param histo Histogram to plot.
  */
  /*def bar(histo: StringHistogram) = {
    bar (histo, histo.size - 1)
  }*/

  /** Create a bar graph of a histogram including a
  * given number of items.  If the histogram contains
  * fewer than the requested number of items, all are
  * plotted.
  *
  * @param histo Histogram to plot.
  * @param max Number of items to include.
  */
  def bar(histo: StringHistogram, max : Int) : com.quantifind.charts.highcharts.Highchart = {
    val chartSize = {
      max match {
        case n: Int if (n >= histo.size - 1) =>  histo.size - 1
        case _ => max
      }
    }

    val topWords = histo.histogram.map(scount => (scount.s, scount.count)).take(chartSize).toArray
    println("Using arrray with " + topWords.size + " elemens.")
    val numberedColumns = column(topWords.map(_._2).toList)
    delete
    val axisType: com.quantifind.charts.highcharts.AxisType.Type = "category"
    val namedColumns = numberedColumns.copy(
      xAxis = numberedColumns.xAxis.map {
        axisArray => axisArray.map { _.copy(
          axisType = Option(axisType),
          categories = Option(topWords.map(_._1)))
        }
      }
    )
    plot(namedColumns)
  }

}
