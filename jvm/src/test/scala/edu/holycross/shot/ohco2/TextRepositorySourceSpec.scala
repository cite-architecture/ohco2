package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._
import scala.io.Source
import java.io._


class TextRepositorySourceSpec extends FlatSpec {

  "The TextRepositorySource object"  should "produce file paths from a directory name and file name" in {
    val fileName = "textEdition"
    val trailing = "/usrs/username/"
    val noTrail = "/usrs/username"

    assert(
      TextRepositorySource.fileName(trailing,fileName) == TextRepositorySource.fileName(noTrail,fileName)
    )
  }


  it should "create CEF for an XML file" in {
    
  }
  it should "create CEF for a markdown file" in pending
  it should "create CEF for a two-column file" in pending
  it should "create CEF for an 82XF file" in pending
}
