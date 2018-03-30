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


You can also collect Sets of `LabelledCtsUrn`s.
