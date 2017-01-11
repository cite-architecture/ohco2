package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class CorpusSpec extends FlatSpec {

  "A corpus of citable texts"  should "have a non-empty vector of citable nodes" in pending


  // filtering on single node URNS:
  it should "filter the corpus contents against a URN with matching work and passage hierarchies" in pending

  it should "filter the corpus contents against a  URN with matching work hierarchy and containing passage hierarchy" in pending

  it should "filter the corpus contents against a URN with containing work hierarchy and matching passage hierarchy" in pending


  it should "filter the corpus contents against a URN with containing work hierarchy and containing passage hierarchy" in pending


  // filtering on range URNS:
  it should "filter the corpus contents against a range URN with matching work hierarchy and matching range end points" in pending

  it should "filter the corpus contents against a range URN with matching work hierarchy and containing range beginning point" in pending

  it should "filter the corpus contents against a range URN with matching work hierarchy and containing range ending point" in pending

  it should "filter the corpus contents against a range URN with matching work hierarchy and containing points for range beginning and ending" in pending

  it should "filter the corpus contents against a range URN with containing work hierarchy and matching range end points" in pending

  it should "filter the corpus contents against a range URN with containing work hierarchy and containing range beginning point" in pending

  it should "filter the corpus contents against a range URN with containing work hierarchy and containing range ending point" in pending

  it should "filter the corpus contents against a range URN with containing work hierarchy and containing points for range beginning and ending" in pending

  it should "offer a convenience method for finding the first citable node in a filtered vector"

  it should "offer a convenience method for finding the last citable node in a filtered vector"
  it should "offer a convenience method for reducing a list of citable nodes to a list of URN"
  it should "offer a convenience method for extracting the string contents from a list of citable nodes as a single string"
  it should "offer a constructor signature for instantiating a corpus from a 2-column delimited text file"
}
