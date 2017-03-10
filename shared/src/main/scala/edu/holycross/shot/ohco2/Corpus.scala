package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import edu.holycross.shot.orca._
import scala.annotation.tailrec

/** A corpus of citable texts.
*
* @constructor create a new corpus with a vector of CitableNode objects.
* @param nodes contents of the citable corpus
*/
case class Corpus (nodes: Vector[CitableNode]) {

  /** Project all URNs in the corpus to a vector.
  */
  def urns : Vector[CtsUrn] = {
    nodes.map(_.urn)
  }

  /** Project text contents of the corpus to a vector of Strings.
  */
  def contents : Vector[String] = {
    nodes.map(_.text)
  }

  /** Number of citable nodes in the corpus.
  */
  def size: Int = {
    nodes.size
  }

  /** True if citable nodes vector is empty.
  */
  def isEmpty: Boolean = {
    nodes.isEmpty
  }

  /** List all versions or exemplars cited in a corpus.
  */
  def citedWorks: Vector[CtsUrn] = {
    nodes.map(_.urn.dropPassage).distinct
  }

  /** Create a new corpus by adding a second corpus to this one.
  *
  * @param corpus2 second corpus with contents to be added.
  */
  def ++(corpus2: Corpus) : Corpus = {
    val newNodes = nodes ++ corpus2.nodes
    Corpus(newNodes.distinct)
  }

  /** Create a new corpus by subtracting a second corpus from this one.
  *
  * @ corpus2 second corpus with contents to be removed from this one.
  */
  def --(corpus2: Corpus) : Corpus = {
    Corpus( nodes diff corpus2.nodes)
  }


  /** Create a new corpus of texts matching an OrcaCollection.
  * The resulting corpus contains all text nodes in the current
  * corpus appearing as analyzed texts in orca.  Before filtering
  * a Corpus with an OrcaCollection, the OrcaCollection should
  * be expanded to explicit mapping of leaf-node values.
  *
  * @param orca a collection of orca analyses.
  */
  def ~~(orca: OrcaCollection) : Corpus = {
    this ~~ orca.texts
  }


  /** Create a new corpus of nodes matching a given URN.
  * Note that chaining these filters therefore successively
  * filters the corpus and can be thought of as filtering by
  * logically ANDing the URNs.
  *
  * @param filterUrn URN identifying a set of nodes to select from this corpus.
  */
  def ~~(filterUrn: CtsUrn) : Corpus = {
    filterUrn.isRange match {
      // range filter:
      case true => {
        val u1 = CtsUrn(filterUrn.dropPassage.toString + filterUrn.rangeBeginRef)
        val u2 = CtsUrn(filterUrn.dropPassage.toString + filterUrn.rangeEndRef)

        try {
          val filt1 = this ~~ u1
          val filt2 = this ~~ u2

          val idx1 = nodes.indexOf(firstNode(u1))
          val idx2 = nodes.indexOf(lastNode(u2)) + 1

          Corpus(nodes.slice(idx1,idx2))

        } catch {
          case oe: Ohco2Exception => Corpus(Vector.empty[CitableNode])
        }
      }
      //node filter:
      case false =>  {
       Corpus(nodes.filter(_.urn ~~ filterUrn))
     }
    }
  }

  /** Create a new corpus of nodes matching any of URN in a given vector of URNs.
  * Note that this can be thought of as filtering by
  * logically ORing the URNs in the Vector.
  *
  * @param urnV vector of URNs to use in filtering the corpus.
  */
  def  ~~(urnV: Vector[CtsUrn]): Corpus = {
    val rslts = Vector.empty
    this.~~(urnV, Corpus(rslts))
  }

  /** Recursively add to a given corpus all nodes in the present corpus matching the first URN in a given vector of URNs.
  * When all nodes in the vector have been applied, the
  * result is the final accumulation of all added nodes.
  *
  * @param urnV vector of URNs to use in filtering the corpus.
  * @param resultCorpus
  */
  @tailrec final  def ~~(urnV : Vector[CtsUrn], resultCorpus: Corpus): Corpus = {
    if (urnV.isEmpty ) {
      resultCorpus

    } else {
      val subVect =   Corpus(nodes.filter(_.urn ~~ urnV.head))
      val newTotal = resultCorpus ++ subVect
      this ~~(urnV.tail, newTotal)
    }
  }

  /** Extract all URNs for all citable nodes identified by a URN.
  * Note that it is not an error if the resulting Vector is empty.
  *
  * @param filterUrn URN identifying passage for which to find node URNs.
  */
  def validReff(filterUrn: CtsUrn): Vector[CtsUrn]
 = {
   val filtered = nodes.filter(_.urn.~~(filterUrn))
   filtered.map(_.urn)
  }

  /** Format text contents of a passage identified by a URN
  * as a single string.
  *
  * @param filterUrn URN identifying passage to select
  * @param connector String value separating citable nodes in the resulting string.
  */
  def textContents(filterUrn: CtsUrn, connector: String = "\n"): String = {
    val matching = this ~~ filterUrn
    matching.nodes.map(_.text).mkString(connector)
  }


  /** Find first citable node in a passage.
  * Option is None if no citable nodes are found for
  * the requested passage.
  *
  * @param filterUrn URN identifying the passage.
  */
  def firstNodeOption(filterUrn: CtsUrn): Option[CitableNode] = {
    val matching = this ~~ filterUrn
    matching.nodes.isEmpty match {
      case true => None
      case false => Some(matching.nodes.head)
    }
  }

  /** Find first citable node in a passage.
  * It is an exception if the passage does not include
  * at least one citable node.
  *
  * @param filterUrn URN identifying the passage.
  */
  def firstNode(filterUrn: CtsUrn): CitableNode = {
    firstNodeOption(filterUrn) match {
      case None => throw Ohco2Exception("No node matching " + filterUrn)
      case n: Some[CitableNode] => n.get
    }
  }

  /** Find first citable node in the corpus.
  * It is an exception if the passage does not include
  * at least one citable node.
  */
  def firstNode: CitableNode = {
    nodes(0)
  }

  /** Find the last citable node in the corpus.
  * It is an exception if the passage does not include
  * at least one citable node.
  */
  def lastNode: CitableNode = {
    nodes(nodes.size - 1)
  }

  /** Find the last citable node in a passage.
  * Option is None if no citable nodes are found for
  * the requested passage.
  *
  * @param filterUrn URN identifying the passage.
  */
  def lastNodeOption(filterUrn: CtsUrn): Option[CitableNode] = {
    val matching = this ~~ filterUrn
    matching.nodes.isEmpty match {
      case true => None
      case false => Some(matching.nodes.last)
    }
  }

  /** Find the last citable node in a passage.
  * It is an exception if the passage does not include
  * at least one citable node.
  *
  * @param filterUrn URN identifying the passage.
  */
  def lastNode(filterUrn: CtsUrn): CitableNode = {
    lastNodeOption(filterUrn) match {
      case None => throw Ohco2Exception("No node matching " + filterUrn)
      case n: Some[CitableNode] => n.get
    }
  }


  /** Find nodes preceding a passage.
  * The number of nodes will equal the number of
  * nodes in the passage unless fewer than that number of nodes precede
  * the passage.  In that case, all preceding nodes will be
  * returned.  If no nodes precede the passage, an empty
  * vector is returned.
  *
  * @param filterUrn passage to find nodes before
  */
  def prev(filterUrn: CtsUrn): Vector[CitableNode] = {
    val subselection = this ~~ filterUrn
    if (subselection.nodes.isEmpty) {
     Vector.empty

    } else {
      val idx = nodes.indexOf(subselection.firstNode)
      val min = idx - subselection.size


      min match {
        case n if n >= 0 => {
          nodes.slice(min,idx)
        }
        case _ => {
          nodes.slice(0,idx)
        }
      }
    }
  }


  /** Find nodes following a passage.
  * The number of nodes will equal the number of
  * nodes in the passage unless fewer than that number of nodes follow
  * the passage.  In that case, all following nodes will be
  * returned.  If no nodes follow the passage, an empty
  * vector is returned.
  *
  * @param filterUrn passage to find nodes before
  */
  def next(filterUrn: CtsUrn): Vector[CitableNode] = {
    val subselection = this ~~ filterUrn
    if (subselection.nodes.isEmpty) {
     Vector.empty

    } else {
      val idx = nodes.indexOf(subselection.lastNode) + 1
      val max = idx + subselection.size
      max match {
        case n if n < nodes.size => nodes.slice(idx,max)
        case _ => nodes.slice(idx,nodes.size)
      }
    }
  }

  /** Create a vector of [[edu.holycross.shot.ohco2.XfRow]]
  * instances equivalent to the present corpus.
  */
  def to82xfVector: Vector[XfRow] = {
    val ids = nodes.map ( n => n.urn)
    val templateVector = Vector.empty[String]
    val nextColumn = templateVector  ++ ids.drop(1) ++ Vector("")
    val prevColumn = templateVector ++ Vector("") ++ ids.dropRight(1)
    val nextPrev = nextColumn.zip(prevColumn)
    nodes.zip(nextPrev).map {
      case (n,(nxt,prv)) => XfRow(n.urn.toString,nxt.toString,prv.toString,n.text)
    }
  }


  /** Represent the Corpus in 82XF format.
  *
  * @param delimiter String value to use as a column separator.
  */
  def to82xfString(delimiter: String): String = {
    to82xfVector.map(_.rowString(delimiter)).mkString("\n")
  }

  /** Represent the Corpus in two-column delimited-text format.
  *
  * @param delimiter String value to use as to separate URN strings from text contents.
  */
  def to2colString(delimiter: String): String = {
    nodes.map(cn => cn.urn + delimiter + cn.text).mkString("\n")
  }




  /** Convert strings to vectors of words, tokenizing on whitespace.
  *
  * @param skipPunct true if punctuation should be omitted.
  * @param return sequence of word vectors.
  */
  def passagesToWords(skipPunct: Boolean = true): Vector[Vector[String]] = {
    val punctList = "·.,:⁚‡·".toList.map(_.toString)
    if (skipPunct) {
      contents.map(_.split("\\s+").toVector.filterNot(punctList.contains(_)))
    } else {
      contents.map(_.split("\\s+").toVector)
    }
  }

  /** Find passages, identified by URN, where a given ngram occurs.
  * The value of n is derived from the number of whitespace-delimited
  * tokens in gram.
  *
  * @param gram The desired ngram, with white space separating tokens.
  * @param dropPunctuation True if punctuation should be omitted.
  */
  def urnsForNGram(gram: String, threshhold: Int = 2,dropPunctuation: Boolean = true): Vector[CtsUrn] = {
    val n = gram.split(" ").size
    val words = passagesToWords(dropPunctuation)
    val allGrams = words.map(v => Corpus.ngrams(v,n))
    val citableGrams = urns.zip(allGrams)
    citableGrams.filter(_._2.contains(gram)).map(_._1)
  }

  /** Create a histogram of ngrams of size [[n]],
  * occurring more than [[threshold]] times.
  *
  * @param strings Vector  of strings
  * @param n size of ngram desired
  * @param threshhold
  * @param dropPunctuation true if punctuation should be omitted from ngrams
  * @return a vector of word+count pairs sorted from high to low
  */
  def ngramHisto(n: Int, threshhold: Int = 2, dropPunctuation: Boolean = true): StringHistogram = {
    val words = passagesToWords(dropPunctuation)
    val allGrams = words.map(v => Corpus.ngrams(v,n)).filterNot(_.isEmpty).flatten
    // guarantee length after filtering empties:
    val grams = allGrams.filter(_.split(" ").size == n)

    val histogram = grams.groupBy(phr => phr).map{ case (k,v) => StringCount(k,v.size) }.toSeq.filter(_.count > threshhold).sortBy(_.count).reverse
    StringHistogram(histogram.toVector)
  }

}

/** Factory for [[edu.holycross.shot.ohco2.Corpus]] instances.
*/
object Corpus {

  /** Create a Corpus from a two-column data source.
  *
  * @param data string serialization of a corpus as delimited text, with one citable node per line.
  * @param separator delimiting value separating URN from text contents of citable node.
  */
  def apply(data: String, separator: String = "\t"): Corpus = {
    val stringPairs = data.split("\n").toVector.filter(_.nonEmpty).map(_.split(separator).toVector)
    // should be exclusively 2-column data
    val checkFormat = stringPairs.filter(_.size != 2)
    if (checkFormat.size > 0) {
      throw Ohco2Exception("Badly formatted input.  Did not find 2 columns in the following source: " + checkFormat.map(_.mkString(" ")).mkString("\n"))
    }

    val citableNodes = stringPairs.map( arr => CitableNode(CtsUrn(arr(0)), arr(1))  )

    Corpus(citableNodes)
  }



  /** Create a sequence of ngrams for a sequence of toknes.
  *
  * @param v Strings to make ngrams from
  * @param n size of ngram
  * @return vector of Strings containing ngrams of the requested size.
  */
  def ngrams(v: Vector[String], n: Int): Vector[String] = {
    v.sliding(n,1).toVector.map(_.mkString(" "))
  }

}

/** A citable node in the model of the 82XF format.
*
* @param urn string value of the node's URN.
* @param next string value of the following node's URN, or an empty string if this node is the last node of a text.
* @param prv string value of the preceding node's URN, or an empty string if this node is the first node of a text.
* @param txt text contents of the node.
*/
case class XfRow(urn: String, nxt: String, prv: String, txt: String) {

  /** Create a string representation in 82XF format.
  *
  * @param delimiter delimiting value separating the four columns of the 82XF format.
  */
  def rowString(delimiter: String ): String = {
    urn + delimiter + nxt + delimiter + prv + delimiter + txt
  }
}
