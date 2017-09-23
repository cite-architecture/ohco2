package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.annotation.tailrec

import scala.collection.mutable.Map

import scala.scalajs.js
import scala.scalajs.js.annotation._

/** A corpus of citable texts.
*
* @constructor Create a new corpus with a vector of CitableNode objects.
* @param nodes Contents of the citable corpus
*/
@JSExportAll case class Corpus (nodes: Vector[CitableNode]) {



  /** Map each concrete text's URN to a Vector of [CitableNode]s.
  */
  def concreteMap : Map[CtsUrn, Corpus] = {
    var mappedByText = scala.collection.mutable.Map[CtsUrn, Corpus]()
    for (c <- citedWorks) {
      mappedByText += (c -> containedNodes(c))
    }
    mappedByText
  }

  /** Create a new corpus comprising nodes contained by a given URN.
  *
  * @param u A CtsUrn at either version or exemplar level.
  */
  def containedNodes(u: CtsUrn): Corpus = {
    require(u.concrete, "Can only compute contained nodes for a concrete instance of a text: " + u)
    val trimmed = u.dropPassage
    //println("\n\nLimit containment to " + trimmed)
    //println("From corpus \n" + urns.mkString("\n") )
    val matchingWork = nodes.filter(_.urn.dropPassage == trimmed)
    //println("Found " + matchingWork.size + " nodes")

    if (u.isRange) {
      // Allow for possibility that range begin/end references are
      // either containers or nodes
      //println("CONTAINMENT ON RANGE: " + u)
      val urnA = CtsUrn(u.dropPassage.toString + u.rangeBeginRef)
      val urnB = CtsUrn(u.dropPassage.toString + u.rangeEndRef)

      val aContained = containedNodes(urnA)
      val bContained = containedNodes(urnB)
      if (aContained.size < 1 || bContained.size < 1) {
        Corpus(Vector.empty)
      } else {
        val firstRef = aContained.nodes.head.urn.passageNodeRef
        val lastRef = bContained.nodes.last.urn.passageNodeRef

        val extentUrn = CtsUrn(u.dropPassage.toString + firstRef + "-" + lastRef)
        rangeExtract(extentUrn)
      }

    } else {
      // single node or containing reference:
      val containedCorpus = Corpus(matchingWork.filter(u >= _.urn ))
      //println("Filtering using " + u + ": " + containedCorpus.size)
      containedCorpus

    }
  }

  /** Computes topological relation of passage
  * components of two CtsUrns.
  *
  * @param u1 First CtsUrn to compare.
  * @param u2 Second CtsUrn to compare.
  */
  def relation(u1: CtsUrn, u2: CtsUrn): TextPassageTopology.Value = {
    require(u1.dropPassage == u2.dropPassage, s"Corpus.relation: URNs must refer to same concrete version: ${u1} and ${u2}" )

    val u1size = if (u1.isRange) {
      rangeIndex(u1).size
    } else  {
      containedNodes(u1).size
    }
    val u2size = if (u2.isRange) {
      rangeIndex(u2).size
    } else  {
      containedNodes(u2).size
    }


    if (u1size == 1 && u2size == 1) {
      nodeToNodeRelation(u1,u2)
    } else if (u1size == 1 && u2size > 1) {
      nodeToRangeRelation(u1, u2)
    } else if (u2size == 1 && u1size > 1) {
      Corpus.invertTopology(nodeToRangeRelation(u2, u1))
    } else {
      rangeToRangeRelation(u1, u2)
    }
  }


  /** Compute the topological relation of a node and a range.
  *
  * @param point URN for a single node.
  * @param range URN for a series of nodes (a range  or containing expression).
  */
  private def nodeToRangeRelation(point: CtsUrn, range: CtsUrn): TextPassageTopology.Value = {
    val rIdx = rangeIndex(range)
    val ptIdx = pointIndex(point)

    if (rIdx.a <= ptIdx &&  rIdx.b >= ptIdx) {
      TextPassageTopology.PassageContainedBy
    } else if (ptIdx < rIdx.a) {
      TextPassageTopology.PassagePrecedes
    } else {
      TextPassageTopology.PassageFollows
    }
  }


  /** Compute the topological relation of two ranges.
  *
  * @param u1 URN for a series of nodes (range- or container-expression).
  * @param u2 URN for a series of nodes (range-  or container-expression).
  */
  private def rangeToRangeRelation(u1: CtsUrn, u2: CtsUrn): TextPassageTopology.Value = {
    val r1idx = rangeIndex(u1)
    val r2idx = rangeIndex(u2)

    val rangeRelation = if (r1idx == r2idx) {
      TextPassageTopology.PassageEquals

    } else if (r1idx.b < r2idx.a) {
      TextPassageTopology.PassagePrecedes
    } else if (r1idx.a  > r2idx.b) {
      TextPassageTopology.PassageFollows

    } else if (r1idx.a <= r2idx.a && r1idx.b >= r2idx.b) {
      TextPassageTopology.PassageContains
    } else if (r1idx.a >= r2idx.a && r1idx.b <= r2idx.b) {
      TextPassageTopology.PassageContainedBy

    } else if (r1idx.a < r2idx.a && r1idx.b < r2idx.b && r1idx.b >= r2idx.a) {
      TextPassageTopology.PassagePrecedesAndOverlaps
    } else if (r1idx.a > r2idx.a && r1idx.b > r2idx.b  ) {
      //println("Two ranges: " + r1idx + ", " + r2idx)
      //println("from " + u1 + ", " + u2)
      TextPassageTopology.PassageOverlapsAndFollows

    } else {
      throw Ohco2Exception("Unrecognized relation of range URNs " + u1 + " and " + u2 )
    }
    rangeRelation

  }



  /** Compute topological relation of two nodes URNs.
  *
  * @param u1 First node to compare.
  * @param u2 Second node to compare.
  */
  private def nodeToNodeRelation(u1: CtsUrn, u2: CtsUrn): TextPassageTopology.Value = {
    if (u1 == u2) {
      TextPassageTopology.PassageEquals
    } else {
      if (pointIndex(u1) < pointIndex(u2)) {
        TextPassageTopology.PassagePrecedes
      } else {
        TextPassageTopology.PassageFollows
      }
    }
  }

  /** Find the set of versions in the present corpus
  * matching a given URN.
  *
  * @param urn URN to find versions for.
  */
  def versions(urn: CtsUrn): Set[CtsUrn] = {
    val versionUrns = nodes.map(_.urn).filter(_.isVersion)

    if (urn.isRange) {
      val firstRef = CtsUrn(urn.dropPassage + urn.rangeBeginRef)
      val similar = versionUrns.filter (_ ~~ firstRef)
      similar.map(_.dropPassage).distinct.toSet

    } else {
      /*
      val matches = nodes.filter(_.urn ~~ urn)
      val versionList = matches.map(_.urn.dropPassage).filter(_.isVersion).distinct
      versionList.toSet*/
      val similar = versionUrns.filter (_ ~~ urn)
      similar.map(_.dropPassage).distinct.toSet
    }
  }

  /** Find the set of exemplars in the present corpus
  * matching a given URN.
  *
  * @param urn URN to find exemplars for.
  */
  def exemplars(urn: CtsUrn): Set[CtsUrn] = {
    val exemplarUrns = nodes.map(_.urn).filter(_.isExemplar)



    if (urn.isRange) {
      val firstRef = CtsUrn(urn.dropPassage + urn.rangeBeginRef)
      val similar = exemplarUrns.filter (_ ~~ firstRef)
      similar.map(_.dropPassage).distinct.toSet

    } else {
      val similar = exemplarUrns.filter (_ ~~ urn)
      similar.map(_.dropPassage).distinct.toSet
    }
    /*
    val matches = nodes.filter(_.urn ~~ urn)
    println("Exemplars? matching " + urn)
    println("got " + matches)
    val allMatches = matches.map(_.urn.dropPassage)
    println("Drop passage matches: " + allMatches)
    //.filter(_.isExemplar).distinct
    val exemplarList = allMatches.filter(_.isExemplar).distinct
    println("exemplars only: " + exemplarList)
    exemplarList.toSet
    */
  }



  /** Create a new corpus from a single URN idetnifying a range.
  * The given URN must refer to a concrete text.
  *
  * @param urn Range URN identifying corpus to extract.
  */
  def rangeExtract(urn: CtsUrn) : Corpus = {
    require(urn.concrete, "Can only extract ranges for a concrete text:  " + urn)
    val rIdx = rangeIndex(urn)
    val sliver = nodes.slice(rIdx.a, rIdx.b + 1)
    Corpus(sliver)
  }

  /** Find beginning and end index in this corpus of a given range URN.
  * Beginning and end references of ranges may either be node references or
  * containing references.
  *
  * @param u CtsUrn to index.  It is an exception if u does not
  * identify a range of nodes present in the Corpus.
  */
  def rangeIndex(urn: CtsUrn): RangeIndex = {
    require (urn.concrete, "Can only index references to concrete texts: " + urn)
    if (urn.isRange) {
      val noPsg = nodes.filter(_.urn.dropPassage == urn.dropPassage)
      //println("RANGE INDEX " + urn)

      val beginRef ={
        urn.rangeBeginRefOption match {
            case opt: Some[String] => opt.get
            case None => ""
        }
      }
      val endRef = {
        urn.rangeEndRefOption match {
            case opt: Some[String] => opt.get
            case None => ""
        }
      }

      val urnA = CtsUrn(urn.dropPassage.toString + beginRef)
      val urnB = CtsUrn(urn.dropPassage.toString + endRef)
      //println("A,B: \n" + urnA + " \n" + urnB)

      val aMatches = noPsg.filter(_.urn ~~ urnA)
      val bMatches = noPsg.filter(_.urn ~~ urnB)
      //println("\nrangeIndexing " + urn + "\n")
      //println("amatches: " + aMatches.map(_.urn).mkString("\n"))
      //println("\n bmatches: " + bMatches.map(_.urn).mkString("\n"))


      if (aMatches.isEmpty || bMatches.isEmpty) {
        throw Ohco2Exception("Failed to match both ends of range reference: " + urn)
      } else {
        val a = pointIndex(aMatches.head.urn)
        val b = pointIndex(bMatches.last.urn)
        RangeIndex(a,b)
      }

    } else {
      val contained = this >= urn
      if (contained.size > 1) {
        val a = pointIndex(contained.urns.head)
        val b = pointIndex(contained.urns.last)
        RangeIndex(a,b)
      } else {
        throw Ohco2Exception(s"Not a containing or range expression: ${urn}")
      }
    }
  }


  /** Find index in `nodes` of a given CtsUrn.
  *
  * @param u CtsUrn to index.  It is an exception if u does not
  * identify a single text node that is present in the Corpus.
  */
  def pointIndex(urn: CtsUrn): Int = {
    val matches = nodes.filter(_.urn == urn)
    matches.size match {
      case 1 =>  nodes.indexOf(matches(0))
      case _ => throw Ohco2Exception("Corpus.pointIndex: " + urn + " does not refer to a node.")
    }
  }

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


  /** Create a single [[Corpus]] by summing up the contents of
  * a vector of corpora.
  *
  * @param corpora [[Corpus]] instances to concatenate.
  */
  //@tailrec final def sumCorpora(corpora: Vector[Corpus], sumCorpus:
  def sumCorpora(corpora: Vector[Corpus], sumCorpus: Corpus): Corpus = {
    if (corpora.isEmpty) {
      sumCorpus
    } else {
      sumCorpora(corpora.drop(1), sumCorpus ++ corpora(0))
    }
  }

  /** Create a new corpus of nodes that are URN-similar to a given CtsUrn.
  * Collect all texts where this URN is cited, then
  * collect citable nodes for the cited version.
  * Note that chaining these filters therefore successively
  * filters the corpus and can be thought of as filtering by
  * logically ANDing the URNs.
  *
  * @param filterUrn URN identifying a set of nodes to select from this corpus.
  */
  def ~~ (filterUrn: CtsUrn) : Corpus = {
    val psgRef = filterUrn.passageComponentOption match {
      case None => ""
      case s: Option[String] => s.get
    }

    if (filterUrn.isPoint) {
      Corpus(nodes.filter(_.urn ~~ filterUrn))

    } else if (filterUrn.isRange) {
      val corpora = for (cw <- concrete(filterUrn)) yield {
        //println("\n\nFILTER FOR " + cw)
        val concreteFilter = CtsUrn(cw.toString + psgRef)
        val srcCorpus = Corpus(nodes.filter(_.urn.dropPassage == cw))
        //println("Result of filtering is\n" + srcCorpus.nodes.map(_.urn).mkString("\n"))
        try {
          srcCorpus.rangeExtract(concreteFilter)
        } catch {
          case oe: Ohco2Exception => Corpus(Vector.empty)
        }

      }
      sumCorpora(corpora.toSeq.toVector,Corpus(Vector.empty))


    } else {
      // containing node:
      Corpus(nodes.filter(_.urn ~~ filterUrn))
    }
  }



  /** Create a new corpus of nodes that are URN-similar to any
  * CtsUrn in a given vector of CtsUrns.
  * Note that this can be thought of as filtering by
  * logically ORing the CtsUrns in the Vector.
  *
  * @param urnV vector of URNs to use in filtering the corpus.
  */
  def  ~~(urnV: Vector[CtsUrn]): Corpus = {
    val rslts = Vector.empty
    this.~~(urnV, Corpus(rslts))
  }


  //@tailrec final  def ~~(urnV : Vector[CtsUrn], resultCorpus: Corpus):
  /** Recursively add to a given corpus all nodes in the present corpus
  * that are URN-similar  to the first URN in a given vector of URNs.
  * When all nodes in the vector have been applied, the
  * result is the final accumulation of all added nodes.
  *
  * @param urnV vector of URNs to use in filtering the corpus.
  * @param resultCorpus
  */
  def ~~(urnV : Vector[CtsUrn], resultCorpus: Corpus): Corpus = {
    if (urnV.isEmpty ) {
      resultCorpus

    } else {
      val subVect =   Corpus(nodes.filter(_.urn ~~ urnV.head))
      val newTotal = resultCorpus ++ subVect
      this ~~(urnV.tail, newTotal)
    }
  }


/*
  def >< (urn: CtsUrn) = {
    //handle the simple case where urn is a nodes
    Corpus( nodes.filter(_ >< urn))
  }


  def > (urn: CtsUrn) = {
    //handle the simple case where urn is a nodes
    Corpus( nodes.filter(_ > urn))
  }
*/


/** Find list of all concrete texts for a given URN.
*
* @param urn URN to find concrete texts for.
*/
def concrete(urn: CtsUrn) : Set[CtsUrn] = {
  versions(urn) ++ exemplars(urn)
}

/** Create a new corpus of nodes that are contained by a
* given URN.
*
* @param urn CtsUrn to use in filtering the corpus.
*/
def >= (urn: CtsUrn) : Corpus = {
  if (urn.concrete) {
     containedNodes(urn)

  } else {
    val psg = urn.passageComponent
    //println("NOTIONAL: USE " + concrete(urn))
    // THIS IS BROKEN:
    val concreteTexts = concrete(urn)
    //println("Concrete texts:\n")
    //println(concreteTexts.mkString("\n"))
    val nVect = for (conc <-  concreteTexts) yield {
      val u = CtsUrn(conc.toString + psg)
      //println("\n\nGET CONCRETE " + u)
      val contained = containedNodes(u)
      //println("yielded " + contained)
      contained
    }
    //println(s"NVECT:${nVect.size} nodes " + nVect.toSeq.toVector)
    sumCorpora(nVect.toSeq.toVector,Corpus(Vector.empty))
  }
}


/*
  def < (urn: CtsUrn) = {
    //handle the simple case where urn is a nodes
    Corpus( nodes.filter(_ < urn))
  }

  def <= (urn: CtsUrn) = {
    //handle the simple case where urn is a nodes
    Corpus( nodes.filter(_ <= urn))
  }
*/

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

  /*
  def textContents(filterUrn: CtsUrn, connector: String ): String = {
    val matching = this ~~ filterUrn
    matching.nodes.map(_.text).mkString(connector)
  }
  */


  /** Format text contents of passages matching a given string
  * as a single string.
  *
  * @param filterUrn URN identifying passage to select
  * @param connector String value separating citable nodes in the resulting string.
  */
  def textContents(filter: String, connector: String = "\n"): String = {
    val matching = this.find(filter)
    matching.nodes.map(n => n.urn.toString + " " + n.text).mkString(connector)
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
  def first: CitableNode = {
    nodes(0)
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

  /** Find the last citable node in the corpus.
  * It is an exception if the passage does not include
  * at least one citable node.
  */
  def last: CitableNode = {
    nodes(nodes.size - 1)
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
    val subselection = this >= filterUrn

    if (subselection.nodes.isEmpty) {
     Vector.empty

    } else {
      val workCorpus = this >= filterUrn.dropPassage
      val idx = workCorpus.nodes.indexOf(subselection.nodes.last) + 1
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
    val subselection = this >= filterUrn
    println(s"PREV: from ${filterUrn} subselected " + subselection)

    if (subselection.nodes.isEmpty) {
     Vector.empty

    } else {
      val workCorpus = this >= filterUrn.dropPassage
      val idx = workCorpus.nodes.indexOf(subselection.nodes(0))
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

  /** Create a new corpus containing citable nodes
  * with content matching a given string.
  *
  * @param str String to search for.
  * @return A Corpus object.
  */
  def find(str: String): Corpus = {
    Corpus(this.nodes.filter(_.text.contains(str)))
  }


  /** Create a new corpus containing citable nodes with content
  * matching all strings in a given list by recursively finding
  * matches for the first string in the list.
  *
  * @param v Strings to search for.
  * @param currentCorpus Corpus to search in.
  */
//  @tailrec final  def find(v: Vector[String], currentCorpus: Corpus): Corpus
    def find(v: Vector[String], currentCorpus: Corpus): Corpus = {
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

  /** Create a new corpus with nodes containing all tokens in a
  * given list by recursively finding matches for the first token in the list.
  * Optionally omit or include punctuation in token definition.
  *
  * @param v Tokens to search for.
  * @param currentCorpus Corpus to search in.
  * @param omitPunctuation True if punctuation should be omitted from tokens.
  */
  //@tailrec final  def findTokens(v: Vector[String], currentCorpus: Corpus,
  def findTokens(v: Vector[String], currentCorpus: Corpus,
       omitPunctuation: Boolean = true): Corpus = {
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
  def findWhiteSpaceTokens(v: Vector[String]): Corpus = {
    findTokens(v.drop(1),this.findToken(v(0)),false )
  }


  /** Create a new corpus containing citable nodes
  * with content matching all of a list of
  * whitespace-delimited tokens, ignoring punctuation
  * ("word" tokens).
  * This is equivalent to successively filtering
  * from a given corpus for nodes matching each token.
  * E.g., corpus.findTokens (Vector[s1,s2]) is equivalent to
  * corpus.findTokens(s1).findTokens(s2).
  *
  * @param v Strings to search for.
  */
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
      val matches = findWhiteSpaceTokens(v)
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

  /** Two-column serialization of this Corpus as formated for
  * CEX serialization.
  *
  * @param delimiter String value to separate two columns.
  */
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
  def apply(data: String, separator: String = "#"): Corpus = {
    val stringPairs = data.split("\n").toVector.filter(_.nonEmpty).map(_.split(separator).toVector)
    // should be exclusively 2-column data
    val checkFormat = stringPairs.filter(_.size != 2)
    if (checkFormat.size > 0) {
      throw Ohco2Exception("Badly formatted input.  Did not find 2 columns in the following source: " + checkFormat.map(_.mkString(" ")).mkString("\n"))
    }

    val citableNodes = stringPairs.map( arr => CitableNode(CtsUrn(arr(0)), arr(1))  )

    Corpus(citableNodes)
  }


  /**
  */
  def invertTopology(topo: TextPassageTopology.Value): TextPassageTopology.Value = {
    topo match {
      case TextPassageTopology.PassageEquals => TextPassageTopology.PassageEquals

      case TextPassageTopology.PassagePrecedes => TextPassageTopology.PassageFollows
      case TextPassageTopology.PassageFollows => TextPassageTopology.PassagePrecedes

      case TextPassageTopology.PassageContains => TextPassageTopology.PassageContainedBy
      case TextPassageTopology.PassageContainedBy =>  TextPassageTopology.PassageContains

      case TextPassageTopology.PassagePrecedesAndOverlaps =>  TextPassageTopology.PassageOverlapsAndFollows
      case TextPassageTopology.PassageOverlapsAndFollows => TextPassageTopology.PassagePrecedesAndOverlaps
    }
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
