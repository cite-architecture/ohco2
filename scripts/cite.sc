
import scala.io.Source
import java.io._
import scala.collection.mutable.LinkedHashMap
import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import java.util.Calendar


val fileName1:String = "scripts/iliad_2_col.txt"
val fileName2:String = "scripts/iliad_2_col_noTok.txt"

def loadFile(fp:String = fileName1):Vector[String] = {
	Source.fromFile(fp).getLines.toVector
}


val corp:Corpus = Corpus(loadFile().mkString("\n"),"#")

println(s"Working in a corpus of ${corp.size} nodes.")

val urnPoints:Vector[CtsUrn] = Vector(
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.10"),
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:2.10"),
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:3.10"),
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:4.10"),
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:5.10"),
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:6.10"),
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:7.10"),
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:8.10"),
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:9.10"),
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:10.10"),
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:11.10"),
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:12.10"),
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:13.10"),
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:14.10"),
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:15.10"),
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:16.10"),
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:17.10"),
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:18.10"),
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:19.10"),
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:20.10"),
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:21.10"),
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:22.10"),
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:23.10"),
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:24.10")
)

val urnBooks:Vector[CtsUrn] = urnPoints.map(_.collapsePassageTo(1))

val urnRanges:Vector[CtsUrn] = urnPoints.map( u => {
	val psg = u.passageComponent
	val rangePsg = s"${u}-${psg}0"
	CtsUrn(rangePsg)
})

val urnWorks:Vector[CtsUrn] = Vector(
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:"),
	CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2.tokens:")
)

val numUrns = urnPoints.size + urnBooks.size + urnRanges.size

val u1 = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.5")
val u2 = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1")
val u3 = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.5-1.10")


val timeStart:Long = Calendar.getInstance().getTimeInMillis

	val pts1:Vector[CtsUrn] = urnPoints.map(u => corp.validReff(u)).flatten
	println(s"${pts1.size} urns.")
	val books1:Vector[CtsUrn] = urnBooks.map(u => corp.validReff(u)).flatten
	println(s"${books1.size} urns.")
	val ranges1:Vector[CtsUrn] = urnRanges.map(u => corp.validReff(u)).flatten
	println(s"${ranges1.size} urns.")
	val allWorks1:Vector[CtsUrn] = urnWorks.map(u => corp.validReff(u)).flatten
	println(s"${allWorks1.size} urns.")

val timeEnd:Long = Calendar.getInstance().getTimeInMillis


val timeStart2:Long = Calendar.getInstance().getTimeInMillis

	val pts2:Vector[CtsUrn] = urnPoints.map(u => corp.validReff2(u)).flatten
	println(s"${pts2.size} urns.")
	val books2:Vector[CtsUrn] = urnBooks.map(u => corp.validReff2(u)).flatten
	println(s"${books2.size} urns.")
	val ranges2:Vector[CtsUrn] = urnRanges.map(u => corp.validReff2(u)).flatten
	println(s"${ranges2.size} urns.")
	val allWorks2:Vector[CtsUrn] = urnWorks.map(u => corp.validReff(u)).flatten
	println(s"${allWorks2.size} urns.")

val timeEnd2:Long = Calendar.getInstance().getTimeInMillis


val totalResults1 = pts1.size + books1.size + ranges1.size
val totalResults2 = pts2.size + books2.size + ranges2.size

println(s"points\tbooks\tranges")
println(s"----------------------------------------")
println(s"${pts1.size}\t${books1.size}\t${ranges1.size}\t${allWorks1.size}")
println(s"${pts2.size}\t${books2.size}\t${ranges2.size}\t${allWorks2.size}")
println(s"----------------------------------------")

println(s"Resolved ${totalResults1}:${totalResults2} passages.")

println(s"\n-------\nvalidReff in ${(timeEnd - timeStart) / 1000} seconds.")
println(s"\n-------\nvalidReff2 in ${(timeEnd2 - timeStart2) / 1000} seconds.")
