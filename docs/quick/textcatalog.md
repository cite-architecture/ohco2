---
layout: page
title:  Working with a text catalog
---


As always, import the library.  We'll want to work with CTS URNs, so we'll also  import the `cite` library.

```scala
import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
```






And of course you'll need a `Catalog` to work with.  For these examples, we have silently loaded the CEX data from [this file](../sample-cex-catalog.txt) into a String called `cex`.

```scala
val catalog = Catalog(cex)
```


## What's in the catalog?

You can easily find all text groups, works, versions or exemplars present in the catalog.  If you simply want Sets of CTS URNs, use the functions with these names:

```scala
scala> println(catalog.groups)
Set(urn:cts:greekLit:tlg5026:)

scala> println(catalog.works)
Set(urn:cts:greekLit:tlg5026.msAext:, urn:cts:greekLit:tlg5026.msAil:, urn:cts:greekLit:tlg5026.msAim:, urn:cts:greekLit:tlg5026.msAimlater:, urn:cts:greekLit:tlg5026.msA:, urn:cts:greekLit:tlg5026.msAint:)

scala> println(catalog.versions)
Set(urn:cts:greekLit:tlg5026.msAint.hmt:, urn:cts:greekLit:tlg5026.msAim.hmt:, urn:cts:greekLit:tlg5026.msA.hmt:, urn:cts:greekLit:tlg5026.msAext.hmt:, urn:cts:greekLit:tlg5026.msAimlater.hmt:, urn:cts:greekLit:tlg5026.msAil.hmt:)

scala> println(catalog.exemplars)
Set(urn:cts:greekLit:tlg5026.msAimlater.hmt.tokens:)
```

## Labelling contents

The catalog can provide labels for any level of a CTS URN:

```scala
scala> val passage = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1")
passage: edu.holycross.shot.cite.CtsUrn = urn:cts:greekLit:tlg5026.msA.hmt:1.1

scala> println(catalog.groupName(passage))
Scholia to the Iliad

scala> println(catalog.workTitle(passage))
Main scholia of the Venetus A

scala> println(catalog.versionLabel(passage))
HMT project edition
```


## Working with `LabelledCtsUrn`s.

A text catalog can also package groups of references `LabelledCtsUrn`s that associate a labelling String with a URN.  For example, you collect all text groups, works, versions and exemplars in a catalog as LabelledCtsUrn`s.

```scala
scala> println(catalog.labelledGroups)
Set(LabelledCtsUrn(urn:cts:greekLit:tlg5026:,Scholia to the Iliad))

scala> println(catalog.labelledWorks)
Set(LabelledCtsUrn(urn:cts:greekLit:tlg5026.msAil:,Interlinear scholia of the Venetus A), LabelledCtsUrn(urn:cts:greekLit:tlg5026.msAimlater:,Later intermarginal scholia of the Venetus A), LabelledCtsUrn(urn:cts:greekLit:tlg5026.msAint:,Interior scholia of the Venetus A), LabelledCtsUrn(urn:cts:greekLit:tlg5026.msAext:,Exterior scholia of the Venetus A), LabelledCtsUrn(urn:cts:greekLit:tlg5026.msAim:,Intermarginal scholia of the Venetus A), LabelledCtsUrn(urn:cts:greekLit:tlg5026.msA:,Main scholia of the Venetus A))

scala> println(catalog.labelledVersions)
Set(LabelledCtsUrn(urn:cts:greekLit:tlg5026.msAint.hmt:,HMT project edition), LabelledCtsUrn(urn:cts:greekLit:tlg5026.msAimlater.hmt:,HMT project edition), LabelledCtsUrn(urn:cts:greekLit:tlg5026.msAil.hmt:,HMT project edition), LabelledCtsUrn(urn:cts:greekLit:tlg5026.msAext.hmt:,HMT project edition), LabelledCtsUrn(urn:cts:greekLit:tlg5026.msA.hmt:,HMT project edition), LabelledCtsUrn(urn:cts:greekLit:tlg5026.msAim.hmt:,HMT project edition))

scala> println(catalog.labelledExemplars)
Set(LabelledCtsUrn(urn:cts:greekLit:tlg5026.msAimlater.hmt.tokens:,tokenized exemplar))
```

## Table of contents

The `toc` function constructs a table of contents for the whole catalog.  The contents is organized as a map keyed  by text groups (as  `LabelledCtsUrn`s).  These keys point to a further map keyed by works as `LabelledCtsUrn`s, which in turn point to a map of version `LabelledCtsUrn`s, which (finally!) point to a simple (possibly empty) Vector of `LabelledCtsUrn`s for exemplars.

Let's alphabetize top-level text groups by their label by converting the set of known groups to a sequence, and sorting on their labelling string. We can cycle through the top level of the table of contents alphabetically with that list.

Similarly, if we further want to alphabetize works for each text group by the work's title, we can convert the keyset to map from group to works to a sequence, and sort it by the label property of each `LabelledCtsUrn`.

This example uses plain

```scala
scala> val alpha = catalog.labelledGroups.toSeq.sortBy(_.label)
alpha: Seq[edu.holycross.shot.ohco2.LabelledCtsUrn] = Vector(LabelledCtsUrn(urn:cts:greekLit:tlg5026:,Scholia to the Iliad))

scala> for (group <- alpha) {
     |   val worksFromGroupToVersions = catalog.toc(group)
     |   println("\n" + group.label + " has " + worksFromGroupToVersions.size + " works, sorted alphabetically as:")
     |   for (wk <- worksFromGroupToVersions.keySet.toSeq.sortBy(_.label)) {
     |     val versionsFromWorkToExemplar = worksFromGroupToVersions(wk)
     |     println(s"\t-  ${wk.label} has " + versionsFromWorkToExemplar.size + " version(s).")
     | 
     |     for (vers <- versionsFromWorkToExemplar.keySet.toSeq.sortBy(_.label) ) {
     |       val exemplars = versionsFromWorkToExemplar(vers)
     |       println(s"\t\t-  ${vers.label} has " + exemplars.size + " exemplar(s).")
     |     }
     | 
     |   }
     | 
     | }

Scholia to the Iliad has 6 works, sorted alphabetically as:
	-  Exterior scholia of the Venetus A has 1 version(s).
		-  HMT project edition has 0 exemplar(s).
	-  Interior scholia of the Venetus A has 1 version(s).
		-  HMT project edition has 0 exemplar(s).
	-  Interlinear scholia of the Venetus A has 1 version(s).
		-  HMT project edition has 0 exemplar(s).
	-  Intermarginal scholia of the Venetus A has 1 version(s).
		-  HMT project edition has 0 exemplar(s).
	-  Later intermarginal scholia of the Venetus A has 1 version(s).
		-  HMT project edition has 1 exemplar(s).
	-  Main scholia of the Venetus A has 1 version(s).
		-  HMT project edition has 0 exemplar(s).
```
