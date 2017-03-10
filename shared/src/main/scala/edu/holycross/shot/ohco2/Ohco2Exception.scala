package edu.holycross.shot
package ohco2 {

  /** Exception thrown by a class in this library.
  */
  case class Ohco2Exception(message: String = "", cause: Option[Throwable] = None) extends Exception(message) {
    cause.foreach(initCause)
  }

}
