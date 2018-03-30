---
layout: page
title:  Working with a text catalog
---


As always, import the library.  We'll want to work with CTS URNs, so we'll also  import the `cite` library.

```tut:silent
import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
```



```tut:invisible
val cex = """urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online#lang
urn:cts:greekLit:tlg5026.msA.hmt:#book/scholion/part#Scholia to the Iliad#Main scholia of the Venetus A#HMT project edition##true#grc
urn:cts:greekLit:tlg5026.msAim.hmt:#book/scholion/part#Scholia to the Iliad#Intermarginal scholia of the Venetus A#HMT project edition##true#grc
urn:cts:greekLit:tlg5026.msAint.hmt:#book/scholion/part#Scholia to the Iliad#Interior scholia of the Venetus A#HMT project edition##true#grc
urn:cts:greekLit:tlg5026.msAext.hmt:#book/scholion/part#Scholia to the Iliad#Exterior scholia of the Venetus A#HMT project edition##true#grc
urn:cts:greekLit:tlg5026.msAil.hmt:#book/scholion/part#Scholia to the Iliad#Interlinear scholia of the Venetus A#HMT project edition##true#grc
urn:cts:greekLit:tlg5026.msAimlater.hmt:#book/scholion/part#Scholia to the Iliad#Later intermarginal scholia of the Venetus A#HMT project edition##true#grc
urn:cts:greekLit:tlg5026.msAimlater.hmt.tokens:#book/scholion/part/token#Scholia to the Iliad#Later intermarginal scholia of the Venetus A#HMT project edition#tokenized exemplar#true#grc
"""
```

And of course you'll need a `Catalog` to work with.  For these examples, we have silently loaded the CEX data from [this file](../sample-cex-catalog.txt) into a String called `cex`.

```tut:silent
val catalog = Catalog(cex)
```


## What's in the catalog?

You can easily find all text groups, works, versions or exemplars present in the catalog.  If you simply want Sets of CTS URNs, use the functions with these names:

```tut

println(catalog.groups)
println(catalog.works)
println(catalog.versions)
println(catalog.exemplars)
```

## Labelling contents

The catalog can provide labels for any level of a CTS URN:

```tut

val passage = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt:1.1")

println(catalog.groupName(passage))
println(catalog.workTitle(passage))
println(catalog.versionLabel(passage))
```


## Working with `LabelledCtsUrn`s.

A text catalog can also package groups of references `LabelledCtsUrn`s that associate a labelling String with a URN.  For example, you collect all text groups, works, versions and exemplars in a catalog as LabelledCtsUrn`s.

```tut
println(catalog.labelledGroups)
println(catalog.labelledWorks)
println(catalog.labelledVersions)
println(catalog.labelledExemplars)
```

## Table of contents

The `toc` function constructs a table of contents for the whole catalog.  The contents is organized as a map keyed  by text groups (as  `LabelledCtsUrn`s).  These keys point to a further map keyed by works as `LabelledCtsUrn`s, which in turn point to a map of version `LabelledCtsUrn`s, which (finally!) point to a simple (possibly empty) Vector of `LabelledCtsUrn`s for exemplars.

Let's alphabetize top-level text groups by their label by converting the set of known groups to a sequence, and sorting on their labelling string. We can cycle through the top level of the table of contents alphabetically with that list.

Similarly, if we further want to alphabetize works for each text group by the work's title, we can convert the keyset to map from group to works to a sequence, and sort it by the label property of each `LabelledCtsUrn`.

This example uses plain

```tut

val alpha = catalog.labelledGroups.toSeq.sortBy(_.label)

for (group <- alpha) {
  val worksFromGroupToVersions = catalog.toc(group)
  println("\n" + group.label + " has " + worksFromGroupToVersions.size + " works, sorted alphabetically as:\n")
  for (wk <- worksFromGroupToVersions.keySet.toSeq.sortBy(_.label)) {
    val versionsFromWorkToExemplar = worksFromGroupToVersions(wk)
    println(s"\t-  ${wk.label} has " + versionsFromWorkToExemplar.size + " version(s).")

    for (vers <- versionsFromWorkToExemplar.keySet.toSeq.sortBy(_.label) ) {
      val exemplars = versionsFromWorkToExemplar(vers)
      println(s"\t\t-  ${vers.label} has " + exemplars.size + " exemplar(s).")
    }

  }

}
```
