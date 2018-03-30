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


You can also collect Sets of `LabelledCtsUrn`s.
