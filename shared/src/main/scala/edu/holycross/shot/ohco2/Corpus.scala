package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._
import scala.annotation.tailrec

import scala.collection.mutable.Map

import scala.scalajs.js
import scala.scalajs.js.annotation._


import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter


trait Corpus extends LogSupport {


  def nodes: Vector[CitableNode]

  /** Map each concrete text's URN to a Vector of [CitableNode]s.
  */
  def concreteMap : Map[CtsUrn, Corpus] /*= {
    var mappedByText = scala.collection.mutable.Map[CtsUrn, Corpus]()
    for (c <- citedWorks) {
      mappedByText += (c -> containedNodes(c))
    }
    mappedByText
  }*/

  /** Create a new corpus comprising nodes contained by a given URN.
  *
  * @param u A CtsUrn at either version or exemplar level.
  */
  def containedNodes(u: CtsUrn): Corpus

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
      rangeToNodeRelation(u1, u2)
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

  /** Compute the topological relation of a range and a node.
  *
  * @param range URN for a series of nodes (a range  or containing expression).
  * @param point URN for a single node.
  */
  private def rangeToNodeRelation(range: CtsUrn, point: CtsUrn): TextPassageTopology.Value = {
    val rIdx = rangeIndex(range)
    val ptIdx = pointIndex(point)

    if (rIdx.a <= ptIdx && rIdx.b >= ptIdx) {
      TextPassageTopology.PassageContains
    } else if (rIdx.b < ptIdx) {
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
      //debug("Two ranges: " + r1idx + ", " + r2idx)
      //debug("from " + u1 + ", " + u2)
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

  /** Given an Iterable[CtsUrn] return a Vector[CtsUrn] sorted by document
  *   order according to the order in the Corpus. If any URNs in the
  *   parameter Iterable are range-URNs, this expands them to leaf-nodes
  *   before sorting.
  *   @param passageSet Set[CtsUrn]
  **/
  def sortPassages(passages:Iterable[CtsUrn]):Vector[CtsUrn] = {
    // For some reason, this can't ge done in one line with 2.11?
    val psgSet:Set[CtsUrn] = passages.toSet
    val psgVec:Vector[CtsUrn] = psgSet.toVector

    val pv:Vector[CtsUrn] = psgVec.map(
      u => {
        val vr = this.validReff(u)
        vr
      }
    ).flatten

    //debug("VALID REF MAP\n" + pv.map(_.toString).mkString("\n"))
    val pm:Vector[(CtsUrn,Vector[CtsUrn])] = pv.groupBy(_.dropPassage).toVector
    val workVec:Vector[CtsUrn] = pm.map(work => {
      val thisWorkUrns:Vector[(CtsUrn, Int)] = this.urns.filter(_.dropPassage == work._1).zipWithIndex
      val theseUrns:Vector[(CtsUrn, Int)] = work._2.map( wu => {
        var thisIndex:Int = thisWorkUrns.find(_._1 == wu).get._2
        (wu, thisIndex)
      })
      theseUrns.sortBy(_._2).map(_._1)
    }).flatten
    workVec
  }

  /** Given a Vector[CtsUrn] compress it so that any sequences of URNs that
  *   can be expressed as ranges are expressed as ranges.
  * @param urns Vector[CtsUrn]
  **/
  def compressReff(urns: Vector[CtsUrn]) : Vector[CtsUrn] = {
      // expand any ranges or containing elements
      val pv:Vector[CtsUrn] = urns.toVector.map(u => this.validReff(u)).flatten
      // group by text
      val pm:Vector[(CtsUrn,Vector[CtsUrn])] = pv.groupBy(_.dropPassage).toVector
      /* workVec returns…
        A Vector of…
          A vector of…
            A pair of CtsUrn, and that passage's index
              (its place in its sequence)
      */
      val workVec:Vector[Vector[(CtsUrn,Int)]] = pm.map(work => {
        val thisWorkUrns:Vector[(CtsUrn, Int)] = this.urns.filter(_.dropPassage == work._1).zipWithIndex
        val theseUrns:Vector[(CtsUrn, Int)] = work._2.map( wu => {
          var thisIndex:Int = thisWorkUrns.find(_._1 == wu).get._2
          (wu, thisIndex)
        })
        theseUrns.sortBy(_._2)
      })

      val returnVec:Vector[CtsUrn] = workVec.map(wv => {
        val indices:Vector[Int] = wv.map(_._2)
        val indicesGrouped:Vector[Vector[Int]] = groupSequences(indices)
        val returnDeepVec:Vector[CtsUrn] = indicesGrouped.map( i => {
          if (i.size == 1 ) {
            val u:CtsUrn = wv.find(_._2 == i.head ).get._1
            u
          } else {
            val startUrn:CtsUrn = wv.find(_._2 == i.head ).get._1
            val endUrn:CtsUrn = wv.find(_._2 == i.last ).get._1
            val endPsg:String = endUrn.passageComponent
            val u:CtsUrn = CtsUrn(s"${startUrn}-${endPsg}")
            u
          }
        })
        returnDeepVec
      }).flatten

      returnVec
  }

  /** Utility function: Given a Vector[Int] group all continuous runs
  * @param indices Vector[Int]
  **/
  private def groupSequences(indices:Vector[Int]):Vector[Vector[Int]] = {
    val iAsLists:List[Int] = indices.toList
    val answer:List[List[Int]] = groupSequences(iAsLists)
    answer.map(_.toVector).toVector
  }

  /** Utility function: Given a List[Int] group all continuous runs
  * @param indices List[Int]
  **/
  private def groupSequences(indices: List[Int]): List[List[Int]] = {
        val (acc, last) = indices
            .foldLeft ((List[List[Int]](), List[Int]())) ((a,b) =>
                if ( a._2.size == 0  )  {
                    (a._1 :+ a._2, List(b))
                }
                else if ( (a._2.last + 1) != b  ) {
                    (a._1 :+ a._2, List(b))
                }
                else {
                    (a._1, a._2 :+ b)
                }
            )
        val answerAsLists:List[List[Int]] = (acc :+ last).tail
        answerAsLists
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
    debug("Exemplars? matching " + urn)
    debug("got " + matches)
    val allMatches = matches.map(_.urn.dropPassage)
    debug("Drop passage matches: " + allMatches)
    //.filter(_.isExemplar).distinct
    val exemplarList = allMatches.filter(_.isExemplar).distinct
    debug("exemplars only: " + exemplarList)
    exemplarList.toSet
    */
  }



  /** Create a new corpus from a single URN idetnifying a range.
  * The given URN must refer to a concrete text.
  *
  * @param urn Range URN identifying corpus to extract.
  */
  def rangeExtract(urn: CtsUrn) : Corpus  /*= {
    require(urn.concrete, "Can only extract ranges for a concrete text:  " + urn)
    val rIdx = rangeIndex(urn)
    val sliver = nodes.slice(rIdx.a, rIdx.b + 1)
    Corpus(sliver)
  } */

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
      //debug("RANGE INDEX " + urn)

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
      //debug("A,B: \n" + urnA + " \n" + urnB)

      val aMatches = noPsg.filter(_.urn ~~ urnA)
      val bMatches = noPsg.filter(_.urn ~~ urnB)
      //debug("\nrangeIndexing " + urn + "\n")
      //debug("amatches: " + aMatches.map(_.urn).mkString("\n"))
      //debug("\n bmatches: " + bMatches.map(_.urn).mkString("\n"))


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
  def ++(corpus2: Corpus) : Corpus  /* ={
    val newNodes = nodes ++ corpus2.nodes
    Corpus(newNodes.distinct)
  }*/

  /** Create a new corpus by subtracting a second corpus from this one.
  *
  * @ corpus2 second corpus with contents to be removed from this one.
  */
  def --(corpus2: Corpus) : Corpus /*= {
    Corpus( nodes diff corpus2.nodes)
  }*/


  /** Create a single [[Corpus]] by summing up the contents of
  * a vector of corpora.
  *
  * @param corpora [[Corpus]] instances to concatenate.
  */
  //@tailrec final def sumCorpora(corpora: Vector[Corpus], sumCorpus:
  def sumCorpora(corpora: Vector[Corpus], sumCorpus: Corpus): Corpus /*= {
    if (corpora.isEmpty) {
      sumCorpus
    } else {
      sumCorpora(corpora.drop(1), sumCorpus ++ corpora(0))
    }
  }*/

  /** Create a new corpus of nodes that are URN-similar to a given CtsUrn,
  * limited to a given Version or Exemplar.
  * Collect all texts where this URN is cited, then
  * collect citable nodes for the cited version.
  * Note that chaining these filters therefore successively
  * filters the corpus and can be thought of as filtering by
  * logically ANDing the URNs.
  *
  * @param filterUrn URN identifying a set of nodes to select from this corpus.
  */
  def ~= (filterUrn:CtsUrn) : Corpus  /*= {
    val vUrn:CtsUrn = filterUrn.dropPassage
    val versionCorpus:Corpus = {
      Corpus(nodes.filter(_.urn.dropPassage == vUrn))
    }
    versionCorpus ~~ filterUrn
  }*/

  /** Create a new corpus of nodes that are URN-similar to a given CtsUrn.
  * Collect all texts where this URN is cited, then
  * collect citable nodes for the cited version.
  * Note that chaining these filters therefore successively
  * filters the corpus and can be thought of as filtering by
  * logically ANDing the URNs.
  *
  * @param filterUrn URN identifying a set of nodes to select from this corpus.
  */
  def ~~ (filterUrn: CtsUrn) : Corpus /*= {
    val psgRef = filterUrn.passageComponent

    if (filterUrn.isPoint) {
      Corpus(nodes.filter(_.urn ~~ filterUrn))

    } else if (filterUrn.isRange) {
      val corpora = for (cw <- concrete(filterUrn)) yield {
        //debug("\n\nFILTER FOR " + cw)
        val concreteFilter = CtsUrn(cw.toString + psgRef)
        //debug(s"\n\nconcreteFilter = ${concreteFilter}")
        val srcCorpus = Corpus(nodes.filter(_.urn.dropPassage == cw))
        //debug("Result of filtering is\n" + srcCorpus.nodes.map(_.urn).mkString("\n"))
        try {
          val rangeCorpus:Corpus = srcCorpus.rangeExtract(concreteFilter)
          //debug(s"""\nrangeCorpus: ${rangeCorpus.nodes.map(_.urn.toString).mkString("\n")}""")
          rangeCorpus
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

*/

  /** Create a new corpus of nodes that are URN-similar to any
  * CtsUrn in a given vector of CtsUrns.
  * Note that this can be thought of as filtering by
  * logically ORing the CtsUrns in the Vector.
  *
  * @param urnV vector of URNs to use in filtering the corpus.
  */
  def  ~~(urnV: Vector[CtsUrn]): Corpus /* = {
    val expandedUrnV:Vector[CtsUrn] = urnV.map( u => {
      this.validReff(u)
    }).flatten.distinct
    val rslts = Vector.empty
    this.~~(expandedUrnV, Corpus(rslts))
  }*/


  //@tailrec final  def ~~(urnV : Vector[CtsUrn], resultCorpus: Corpus):
  /** Recursively add to a given corpus all nodes in the present corpus
  * that are URN-similar  to the first URN in a given vector of URNs.
  * When all nodes in the vector have been applied, the
  * result is the final accumulation of all added nodes.
  *
  * @param urnV vector of URNs to use in filtering the corpus.
  * @param resultCorpus
  */
  def ~~(urnV : Vector[CtsUrn], resultCorpus: Corpus): Corpus /*= {
    if (urnV.isEmpty ) {
      resultCorpus

    } else {
      val subVect =   Corpus(nodes.filter(_.urn ~~ urnV.head))
      val newTotal = resultCorpus ++ subVect
      this ~~(urnV.tail, newTotal)
    }
  }*/

  /** Split a Corpus in to a Vector[Corpus] by distinct text
  * (versions & exemplars)
  */
  def chunkByText: Vector[Corpus] /* = {
    import scala.collection.mutable.LinkedHashMap
    val vcn:Vector[(CtsUrn, CitableNode)] = nodes.map(cn => {
      (cn.urn.dropPassage, cn)
    })
    //val v1:Vector[(Int, Int, String)] = Vector((1,1,"one-one"),(1,2,"one-two"),(2,1,"two-one"),(2,2,"two-two"))
    val v2 = vcn.zipWithIndex.groupBy( n => n._1._1)
    val v3 = LinkedHashMap(v2.toSeq sortBy (_._2.head._2): _*)
    val v4 = v3 mapValues (_ map (_._1))
    val v5:Vector[(CtsUrn, scala.collection.immutable.Vector[(CtsUrn, CitableNode)])] = v4.toVector
    val v6:Vector[Corpus] = v5.map( t => {
      val nodes:Vector[CitableNode] = t._2.map(_._2)
      Corpus(nodes)
    })
    v6
  }*/

  /** Split a Corpus in to a Vector[Corpus] by citation
  * (Will first chunk by Text).
  * @param drop How many levels of the passage-hierarchy, from the right, to drop when grouping
  */
  def chunkByCitation(drop:Int = 1): Vector[Corpus] /*= {
    val textChunks:Vector[Corpus] = this.chunkByText
    val sectionChunks:Vector[Corpus] = textChunks.map( tc => {
      val deepestLevel:Int = tc.urns.map(_.citationDepth.head).min
      if (deepestLevel <= drop) {
        val vc:Vector[Corpus] = Vector(tc)
        vc
      }
      else {
         val urnMap:Vector[(CtsUrn, Int)] = tc.urns.zipWithIndex
         // Get vector of node-indices where breaks will happen
         val breakPoints:Vector[Int] = {
          val groupedVec: Vector[ (CtsUrn, Vector[(CtsUrn, Int)])] = {
            urnMap.groupBy( um => um._1.collapsePassageBy(drop)).toVector
          }
          val justHeads: Vector[ Int ] = {
            groupedVec.map( _._2.head._2).sortBy( i => i )
          }
          justHeads
         }
         if (breakPoints.size > 1) {
           // Turn that into a map of from-Index, to-Index
           val chunkMap:Vector[Vector[Int]] = {
              val slid:Vector[Vector[Int]] = breakPoints.sliding(2,1).toVector
              // make a pair for the last chunk
              val lastPair:Vector[Vector[Int]] = {
                val v:Vector[Int] = Vector( slid.last.last, (urnMap.last._2 + 1) )
                Vector(v)
              }
              val adjustedSlid:Vector[Vector[Int]] = slid.map( s => {
                Vector(s(0), s(1))
              }) ++ lastPair
              //debug(s"${adjustedSlid}")
              adjustedSlid
           }
           val corpVec:Vector[Corpus] = chunkMap.map( cm => {
              val fromIndex:Int = cm(0)
              val untilIndex:Int = cm(1)
              val nodeVec:Vector[CitableNode] = {
                tc.nodes.slice(fromIndex, untilIndex)
              }
              val newCorpus:Corpus = Corpus(nodeVec)
              newCorpus
           })
           corpVec
         } else {
          // There's only one chunk
          Vector(this)
         }
      }
    }).flatten
    sectionChunks
  }
*/



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
def >= (urn: CtsUrn) : Corpus /*= {
  if (urn.concrete) {
     containedNodes(urn)

  } else {
    val psg = urn.passageComponent
    //debug("NOTIONAL: USE " + concrete(urn))
    // THIS IS BROKEN:
    val concreteTexts = concrete(urn)
    //debug("Concrete texts:\n")
    //debug(concreteTexts.mkString("\n"))
    val nVect = for (conc <-  concreteTexts) yield {
      val u = CtsUrn(conc.toString + psg)
      //debug("\n\nGET CONCRETE " + u)
      val contained = containedNodes(u)
      //debug("yielded " + contained)
      contained
    }
    //debug(s"NVECT:${nVect.size} nodes " + nVect.toSeq.toVector)
    sumCorpora(nVect.toSeq.toVector,Corpus(Vector.empty))
  }
}
*/

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

  /** Find all versions of a given CtsUrn in this corpus.
  *
  * @param urn URN to find versions for
  */
  def passageVersions(urn: CtsUrn) : Vector[CtsUrn] = {
    val matchingWorks = citedWorks.filter(wk => urn.dropPassage >= wk)
    matchingWorks.map(u => CtsUrn(s"${u}${urn.passageComponent}"))
  }

  /** Find index in this corpus of a URN's first node.
  * If urn is a leaf node, it's simply the index of the node,
  * but for a containing node, it's the first contained
  * leaf node.
  *
  * @param urn First node of a range.
  */
  def firstNodeIndex(urn: CtsUrn) : Option[Int] = {
      val tempIndex = this.urns.indexOf(urn)
      if (tempIndex >= 0) {
        Some(tempIndex)
      } else {
        val d:Int = urn.citationDepth.head
        val filteredNodes:Vector[CtsUrn] = this.urns.filter(_.collapsePassageTo(d) == urn)
        if (filteredNodes.size == 0) None
        else {
          val firstNode = filteredNodes.head
          Some(this.urns.indexOf(firstNode))
        }
      }
  }

  /** Find index in this corpus of a URN's last node.
  * If urn is a leaf node, it's simply the index of the node,
  * but for a containing node, it's the last contained leaf node.
  *
  * @param urn Last node of a range.
  */
  def lastNodeIndex(urn: CtsUrn) : Option[Int] = {
    val tempIndex = this.urns.indexOf(urn)
    if (tempIndex >= 0) { Some(tempIndex) }
    else {
      val d = urn.citationDepth.head
      val filteredNodes:Vector[CtsUrn] = this.urns.filter(_.collapsePassageTo(d) == urn)
      if (filteredNodes.size == 0) None
      else {
        val lastNode = filteredNodes.last
        Some(this.urns.indexOf(lastNode))
      }
    }
  }

  /** Extract all URNs for all citable nodes identified
  * by a given URN.
  * Note that it is not an error if the resulting Vector is empty.
  *
  * @param urn URN identifying passage for which to find node URNs.
  */
  def validReff(urn: CtsUrn): Vector[CtsUrn] = {
    // collect all versions of this URN:
    val allVersions :Vector[CtsUrn] = passageVersions(urn)
    //info(s"All versions: ${allVersions.size}")

    val vrr:Vector[CtsUrn] = allVersions.map( versionUrn =>
      {
        if ( versionUrn.passageComponent.isEmpty ) {
          //info("Valid reff: no passage component in " + urn)
          this.urns.filter(_.dropPassage == versionUrn)

        } else if (this.urns.indexOf(versionUrn) >= 0) {
          // URN is a leaf-node
          //info("Valid reff:  leaf node " + versionUrn)
          Vector(versionUrn)

        } else { // not a leaf-node

          if (versionUrn.isRange) {
            //info("Valid reff: range URN " + versionUrn)
            val beginIndex:Option[Int] = firstNodeIndex(versionUrn.rangeToUrnVector(0))
            val endIndex:Option[Int] = lastNodeIndex(versionUrn.rangeToUrnVector(1))
            if ( (beginIndex == None) | (endIndex == None) ) {
              Vector.empty[CtsUrn]
            } else {
              if (beginIndex.get > endIndex.get) {
                Vector.empty[CtsUrn]
              } else {
                this.urns.slice(beginIndex.get, (endIndex.get + 1) )
              }
            }

          } else {
            // It is a container
            //info(s"versionUrn = ${versionUrn}")
            //info(s"versionUrn.citationDepth = ${versionUrn.citationDepth}")
            val dpth = versionUrn.citationDepth.head

            //info(s"${ this.urns.filter(_.citationDepth.head >= dpth).size }")
            //info(s"${ this.urns.filter(_.citationDepth.head >= dpth).filter(_.collapsePassageTo(dpth) == versionUrn) }")
            //info(s"Done!")
            // Careful here.   Can't collapse URNs
            // to desired depth unless they're already
            // at passage depth >= to target depth.
            val containerResult = this.urns.filter(_.citationDepth.head >= dpth).filter(_.collapsePassageTo(dpth) == versionUrn)
            containerResult
          }
        }
      }).flatten
    vrr
  }

  def validReff2(filterUrn: CtsUrn): Vector[CtsUrn] = {
     val filtered = this ~~ filterUrn
     val concrete:CtsUrn = filterUrn.dropPassage
     val vrr:Vector[CtsUrn] =  filtered.nodes.filter(concrete >= _.urn).map(_.urn)
     vrr
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
    val matching = this >= filterUrn
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
    val matching = this >= filterUrn
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
    if (filterUrn.isVersion || filterUrn.isExemplar) {
      Corpus.passageUrn(prev(filterUrn))
    } else {
      throw Ohco2Exception("'prevUrn' is a valid request only for version- or exemplar-level URNs: " + filterUrn.toString)
    }
}


  /** Find URN for nodes following a passage.
  *
  * @param filterUrn Passage to find nodes after.
  */
  def nextUrn(filterUrn: CtsUrn): Option[CtsUrn] = {
    if (filterUrn.isVersion || filterUrn.isExemplar) {
      Corpus.passageUrn(next(filterUrn))
    } else {
      throw Ohco2Exception("'nextUrn' is a valid request only for version- or exemplar-level URNs: " + filterUrn.toString)
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
  * nodes in the passage unless fewer than that number of nodes preceding
  * the passage.  In that case, all preceding nodes will be
  * returned.  If no nodes precede the passage, an empty
  * vector is returned.
  *
  * @param filterUrn passage to find nodes before
  */
  def prev(filterUrn: CtsUrn): Vector[CitableNode] = {
    val subselection = this >= filterUrn

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
  def urnsForNGram(gram: String, threshhold: Int = 2, dropPunctuation: Boolean = true): Vector[CtsUrn] = {
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
  * @param threshhold only include ngrams that occur more than threshhold
  * times.  (Default value of 0 therefore collects all ngrams of the given sie.)
  * @param dropPunctuation true if punctuation should be omitted from ngrams
  * @return a vector of word+count pairs sorted from high to low
  */
  def ngramHisto(n: Int, threshhold: Int = 0, dropPunctuation: Boolean = true): StringHistogram = {
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
  def find(str: String): Corpus /* = {
    Corpus(this.nodes.filter(_.text.contains(str)))
  }*/


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
  def find(v: Vector[String]): Corpus /*= {
    if (v.isEmpty) {
      Corpus(Vector.empty)
    } else {
      find(v.drop(1), this.find(v(0)))
    }
  }*/

  /** Create a new corpus containing citable nodes
  * with content matching a white-space delimited token.
  * Optionally, ignore punctuation characters.
  *
  * @param v Strings to search for.
  * @param omitPunctuation True if punctuation should be ignored.
  */
  def findToken(t: String, omitPunctuation: Boolean = true): Corpus /*= {
    if (omitPunctuation) {
      // guardNodes removes any CitableNodes that would have empty text-content after stripping punctuation.
      val guardNodes:Vector[CitableNode] = nodes.filter(_.stripPunctuation.size > 0)
      val stripped:Vector[CitableNode] = guardNodes.map(CitableNode.stripPunctuation(_))
      Corpus(stripped.filter(_.tokenMatches(t)))
    } else {
      Corpus(nodes.filter(_.tokenMatches(t)))
    }
  } */

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
  def findTokensWithin(v: Vector[String], distance: Int, omitPunctuation: Boolean = true): Corpus /*= {
    if (omitPunctuation) {


      val matches = findWordTokens(v)
      val closeBy = Corpus(matches.nodes.filter(_.tokensWithin(v,distance)))
      closeBy
    } else {
      val matches = findWhiteSpaceTokens(v)
      val closeBy = Corpus(matches.nodes.filter(_.tokensWithin(v,distance)))
      closeBy
    }
  }*/

/** Create a histogram of ngrams of size n,
* occurring more than threshold times, and including
* a specified string.
*
* @param str String that must be part of indexed ngram.
* @param n size of ngram desired
* @param threshhold only include ngrams that occur more than threshhold
* times.  (Default value of 0 therefore collects all ngrams of the given sie.)
* @param dropPunctuation true if punctuation should be omitted from ngrams
* @return a vector of word+count pairs sorted from high to low

  def ngramHisto(str: String, n: Int, threshhold: Int, dropPunctuation: Boolean ): StringHistogram = {
    val searchCorpus = Corpus(this.nodes.filter(_.text.contains(str)))
    val hist  = searchCorpus.ngramHisto(n,threshhold,dropPunctuation)
    hist.stringMatch(str)
  }
*/

  /** Two-column serialization of this Corpus as formated for
  * CEX serialization.
  *
  * @param delimiter String value to separate two columns.
  */
  def cex(delimiter: String = "#"): String = {
    nodes.map(_.cex(delimiter)).mkString("\n") + "\n"
  }




    /** Pairs a CitableNode with a sequential index number for that
    * node.
    *
    * @param v Vector of triples, comprised of passage identifier
    * (a String value), a citable node, and a sequence number within
    * the passage node.
    * @param newVersion Version identifier for the new node.
    */
    def flattenTriple(v: Vector[(String, edu.holycross.shot.ohco2.CitableNode, Int)], newVersion : String) = {
      val psg = v(0)._1
      val seq = v(0)._3

      val urn = CtsUrn(s"${v(0)._2.urn.dropPassage.addVersion(newVersion)}${psg}")
      val cnodes = v.map(_._2)
      (seq, CitableNode(urn,   cnodes.map(_.text).mkString(" ")))
    }

    /** Creates a new corpus by reducing exemplar-level URNs
    * to version-level URNs.  Order of exemplar-level nodes is
    * maintained in the flattened, version-level corpus.
    *
    * @param newVersionId Value for version identifier of newly
    * generated version.
    */
    def exemplarToVersion(newVersionId: String): Corpus /*= {
      val zipped = nodes.zipWithIndex
      val triple = zipped.map {
        case (cn,i) => (cn.urn.passageComponent,cn,i)
      }
      val reduced = triple.map {
        case (s,cn,i) => (s.split("[.]").dropRight(1).mkString("."),cn,i)
      }
      val grouped = reduced.groupBy(_._1).values.toVector
      Corpus(grouped.map(
        flattenTriple(_, newVersionId)).sortBy(_._1).map( _._2)
        )
    }*/
}

/** Factory for [[edu.holycross.shot.ohco2.Corpus]] instances.
*/
object Corpus {

  /** Create a Corpus from a two-column data source.
  *
  * @param data string serialization of a corpus as delimited text, with one citable node per line.
  * @param separator delimiting value separating URN from text contents of citable node.
  */
  /*
  def apply(data: String, separator: String = "#"): Corpus = {
    val stringPairs = data.split("\n").toVector.filter(_.nonEmpty).map(_.split(separator).toVector)
    // should be exclusively 2-column data
    val checkFormat = stringPairs.filter(_.size != 2)
    if (checkFormat.size > 0) {
      throw Ohco2Exception("Badly formatted input.  Did not find 2 columns in the following source: " + checkFormat.map(_.mkString(" ")).mkString("\n"))
    }
    val citableNodes = stringPairs.map( arr => CitableNode(CtsUrn(arr(0)), arr(1))  )

    // no range urns!!
    val checkForWrongRanges:Vector[CtsUrn] = {
        citableNodes.filter(_.urn.isRange).map(_.urn)
    }
    if (checkForWrongRanges.size > 0) {
      throw Ohco2Exception(s"Invald URN in input. ${checkForWrongRanges.map(_.toString).mkString("\n")}. Range-URNs are not allowed to identify leaf nodes.")
    }


    Corpus(citableNodes)
  }*/

  /** Create a single composite [[Corpus]] from a Vector
  * of [[Corpus]] objects.  The order of nodes within the composite
  * corpus follows their order in the Vector of corpora.
  *
  * @param v Vector of corpora to merge.
  
  def composite(v: Vector[Corpus]) : Corpus = {

    v.foldLeft(Corpus(Vector.empty[CitableNode]))((r,c) => r ++ c)
  }
*/


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
