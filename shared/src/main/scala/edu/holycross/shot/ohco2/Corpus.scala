package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import edu.holycross.shot.orca._
import scala.annotation.tailrec

import scala.scalajs.js
import js.annotation.JSExport

/** A corpus of citable texts.
*
* @constructor create a new corpus with a vector of CitableNode objects.
* @param nodes contents of the citable corpus
*/
@JSExport  case class Corpus (nodes: Vector[CitableNode]) {

  /** Project all URNs in the corpus to a vector.
  */
  def urns : Vector[CtsUrn] = {
    nodes.map(_.urn)
  }

  /** Erroneously duplicated URN values.
  */
  val dupes = urns.groupBy(identity).collect { case (x,ys) if ys.lengthCompare(1) > 0 => x }
  require(dupes.size == 0, s"""Duplicated URN values: ${dupes.mkString(",")}""")


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
  * Note that `filterUrn` must identify a passage at the
  * level of the work hierarchy where it is cited in this
  * corpus (either Version or Exemplar level).  Use the
  * [[~~]] function to create a corpus at any level of the
  * the hierarchy.
  *
  *  @param filterUrn URN identifying a set of nodes to select from this corpus.
  */
  private def versionTwiddle(filterUrn: CtsUrn): Corpus = {
    filterUrn.isRange match {

      case false =>  {
        Corpus(nodes.filter(_.urn ~~ filterUrn))
      }
      // range filter:
      case true => {
        val singleWork = this ~~ filterUrn.dropPassage
        val originUrn = CtsUrn(filterUrn.dropPassage.toString + filterUrn.rangeBeginRef)
        val terminalUrn = CtsUrn(filterUrn.dropPassage.toString + filterUrn.rangeEndRef)


        val c1 = singleWork ~~ originUrn
        val c2 = singleWork ~~ terminalUrn
        if (c1.isEmpty || c2.isEmpty) {
          Corpus(Vector.empty)

        } else {
          val u1 = c1.firstNode.urn
          val u2 = c2.lastNode.urn

          var u1seen = false
          var u2NotSeen = true

          val matchedNodes = singleWork.nodes.withFilter{ cn => if (cn.urn == u1) u1seen = true; if (cn.urn == u2) u2NotSeen = false; (u1seen && u2NotSeen)}.map(x => x)

          val lastNode = singleWork ~~ u2
          Corpus(matchedNodes) ++ lastNode
        }
      }
    }
  }


  /** Create a single [[Corpus]] by summing up the contents of
  * a vector of corpora.
  *
  * @param corpora [[Corpus]] instances to concatenate.
  */
  @tailrec final def sumCorpora(corpora: Vector[Corpus], sumCorpus: Corpus): Corpus = {
    if (corpora.isEmpty) {
      sumCorpus
    } else {
      sumCorpora(corpora.drop(1), sumCorpus ++ corpora(0))
    }
  }

  /** Create a new corpus of nodes matching a given URN.
  * Collect all texts where this URN is cited, then
  * collect citable nodes for the cited version by
  * invoking `versionTwiddle`.
  * Note that chaining these filters therefore successively
  * filters the corpus and can be thought of as filtering by
  * logically ANDing the URNs.
  *
  * @param filterUrn URN identifying a set of nodes to select from this corpus.
  */
  def ~~(filterUrn: CtsUrn) : Corpus = {
    val psgRef = filterUrn.passageComponentOption match {
      case None => ""
      case s: Option[String] => s.get
    }

    val corpora = for (cw <- this.citedWorks.filter(_ ~~ filterUrn)) yield {
      versionTwiddle(CtsUrn(cw.toString + psgRef))
    }
    sumCorpora(corpora,Corpus(Vector.empty))

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
   val filtered = nodes.filter(_.urn ~~ filterUrn)
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


  /** Find URN for nodes preceding a passage.
  *
  * @param filterUrn Passage to find nodes before.
  */
  def prevUrn(filterUrn: CtsUrn): Option[CtsUrn] = {
    Corpus.passageUrn(prev(filterUrn))
  }

  /** Find URN for nodes following a passage.
  *
  * @param filterUrn Passage to find nodes after.
  */
  def nextUrn(filterUrn: CtsUrn): Option[CtsUrn] = {
    Corpus.passageUrn(next(filterUrn))
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
      val workCorpus = this ~~ filterUrn.dropPassage
      val idx = workCorpus.nodes.indexOf(subselection.lastNode) + 1
      val max = idx + subselection.size
      max match {
        case n if n < workCorpus.nodes.size => workCorpus.nodes.slice(idx,max)
        case _ => workCorpus.nodes.slice(idx,workCorpus.nodes.size)
      }
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
        val workCorpus = this ~~ filterUrn.dropPassage
        val idx = workCorpus.nodes.indexOf(subselection.firstNode)
        val min = idx - subselection.size


        min match {
          case n if n >= 0 => {
            workCorpus.nodes.slice(min,idx)
          }
          case _ => {
            workCorpus.nodes.slice(0,idx)
          }
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
  * Optionally, omit puncutation characters from result.
  *
  * @param skipPunct true if punctuation should be omitted.
  * @param return sequence of word vectors.
  */
  def passagesToWords(skipPunct: Boolean = true): Vector[Vector[String]] = {
    if (skipPunct) {
      contents.map(punctuationListRE.replaceAllIn(_,"")).map(_.split("\\s+").toVector.filter(_.nonEmpty))
    } else {
      contents.map(_.split("\\s+").toVector.filter(_.nonEmpty))
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

  /** Create a histogram of ngrams of size n,
  * occurring more than threshold times.
  *
  * @param strings Vector  of strings
  * @param n size of ngram desired
  * @param threshhold
  * @param dropPunctuation true if punctuation should be omitted from ngrams
  * @return a vector of word+count pairs sorted from high to low
  */
  def ngramHisto(n: Int, threshhold: Int, dropPunctuation: Boolean): StringHistogram = {
    val words = passagesToWords(dropPunctuation)
    val allGrams = words.map(v => Corpus.ngrams(v,n)).filterNot(_.isEmpty).flatten
    // guarantee length after filtering empties:
    val grams = allGrams.filter(_.split(" ").size == n)

    val histogram = grams.groupBy(phr => phr).map{ case (k,v) => StringCount(k,v.size) }.toSeq.filter(_.count > threshhold).sortBy(_.count).reverse
    StringHistogram(histogram.toVector)
  }

  /** Create a corpus containing only citable nodes
  * with content matching a given string.
  *
  * @param str String to search for.
  * @return A Corpus object.
  */
  def find(str: String): Corpus = {
    Corpus(this.nodes.filter(_.text.contains(str)))
  }


  /** Filter a corpus for nodes containing each of a list
  * of strings by recursively finding matches for the first
  * string in the list.
  *
  * @param v Strings to search for.
  * @param currentCorpus Corpus to search in.
  */
  @tailrec final  def find(v: Vector[String], currentCorpus: Corpus): Corpus = {
    if (v.isEmpty) {
      currentCorpus
    } else {
      find(v.drop(1), currentCorpus.find(v(0)))
    }
  }

  /** Create a new corpus containing citable nodes
  * with content matching all of a list of strings.
  * This is equivalent to successively filtering
  * from a given corpus for nodes matching each string.
  * E.g., corpus.find (Vector[s1,s2]) is equivalent to
  * corpus.find(s1).find(s2).
  *
  * @param v Strings to search for.
  */
  def find(v: Vector[String]): Corpus = {
    if (v.isEmpty) {
      Corpus(Vector.empty)
    } else {
      find(v.drop(1), this.find(v(0)))
    }
  }

  /** Create a new corpus containing citable nodes
  * with content matching a white-space delimited token.
  * Optionally, ignore punctuation characters.
  *
  * @param v Strings to search for.
  * @param omitPunctuation True if punctuation should be ignored.
  */
  def findToken(t: String, omitPunctuation: Boolean = true): Corpus = {
    if (omitPunctuation) {
      val stripped = nodes.map(CitableNode.stripPunctuation(_))
      Corpus(stripped.filter(_.tokenMatches(t)))
    } else {
      Corpus(nodes.filter(_.tokenMatches(t)))
    }
  }

  /** Filter a corpus for nodes containing each of a list
  * of whitespace-delimited tokens by recursively finding matches
  * for the first token in the list.
  *
  * @param v Tokens to search for.
  * @param currentCorpus Corpus to search in.
  */
  @tailrec final  def findTokens(v: Vector[String], currentCorpus: Corpus, omitPunctuation: Boolean = true): Corpus = {
    if (v.isEmpty) {
      currentCorpus
    } else {

      findTokens(v.drop(1), currentCorpus.findToken(v(0)), omitPunctuation)
    }
  }

  /** Create a new corpus containing citable nodes
  * with content matching all of a list of
  * whitespace-delimited tokens.
  * This is equivalent to successively filtering
  * from a given corpus for nodes matching each token.
  * E.g., corpus.findTokens (Vector[s1,s2]) is equivalent to
  * corpus.findTokens(s1).findTokens(s2).
  *
  * @param v Strings to search for.
  */


  /*
  def findTokens(v: Vector[String], omitPunctuation: Boolean = true): Corpus = {
    if (v.isEmpty) {
      Corpus(Vector.empty)
    } else {
      findTokens(v.drop(1), this.findToken(v(0)),omitPunctuation)
    }
  }*/
  def findWSTokens(v: Vector[String]): Corpus = {
    findTokens(v.drop(1),this.findToken(v(0)),false )
  }

  def findWordTokens(v: Vector[String]): Corpus = {
    findTokens(v.drop(1),this.findToken(v(0)),true )
  }

  /** Create a new corpus containing citable nodes
  * with content matching all of a list of
  * whitespace-delimited tokens with a given number of words
  * of each other.
  *
  * @param v Vector of tokens.
  * @param distance Maximum size of consecutive tokens all tokens
  * in v must fall within.
  */
  def findTokensWithin(v: Vector[String], distance: Int, omitPunctuation: Boolean = true): Corpus = {
    if (omitPunctuation) {


      val matches = findWordTokens(v)
      val closeBy = Corpus(matches.nodes.filter(_.tokensWithin(v,distance)))
      closeBy
    } else {
      val matches = findWSTokens(v)
      val closeBy = Corpus(matches.nodes.filter(_.tokensWithin(v,distance)))
      closeBy
    }
  }




/** Create a histogram of ngrams of size n,
* occurring more than threshold times, and including
* a specified string.
*
* @param str String that must be part of indexed ngram.
* @param n size of ngram desired
* @param threshhold
* @param dropPunctuation true if punctuation should be omitted from ngrams
* @return a vector of word+count pairs sorted from high to low
*/
  def ngramHisto(str: String, n: Int, threshhold: Int, dropPunctuation: Boolean ): StringHistogram = {
    val searchCorpus = Corpus(this.nodes.filter(_.text.contains(str)))
    val hist  = searchCorpus.ngramHisto(n,threshhold,dropPunctuation)
    hist.stringMatch(str)
  }


  def cex(delimiter: String = "\t"): String = {
    nodes.map(_.cex(delimiter)).mkString("\n") + "\n"
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

  /** Create range URN for a vector of [[CitableNode]]s.
  *
  * @param Vector of citable nodes to identify with a range expression.
  */
  def passageUrn(v: Vector[CitableNode]) : Option[CtsUrn] = {
    v.size match {
      case 0 => None
      case 1 => Some(v(0).urn)
      case _ => {
        val u1 = v(0).urn
        val u2 = v.last.urn
        Some(CtsUrn(u1.toString + "-" + u2.passageComponent))
      }
    }
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
