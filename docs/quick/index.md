---
layout: page
title:  Quick start
---

Import the library:


```scala
import edu.holycross.shot.ohco2._
```


Create a `TextRepository` from CEX text:


```scala
val cex = """
#!cexversion
3.0

#!ctscatalog
urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online#lang
urn:cts:greekLit:tlg5026.msA.hmt:#book,comment,section#Scholia Vetera in Iliadem#Main scholia to Venetus A#Homer Multitext##true#grc

#!ctsdata
urn:cts:greekLit:tlg5026.msA.hmt:1.2.lemma#μῆνις
urn:cts:greekLit:tlg5026.msA.hmt:1.2.comment#παρὰ τὸ μένω μῆνις ὡς ἐνὸς ἦνις · οἱ δὲ περὶ Γλαύκωνα τὸν Ταρσέα ἠξίουν ὀξύνειν τὸ ὄνομα οὐκ ὀρθῶς .
"""

val textRepository = TextRepository(cex)
```

In the JVM environment, the `TextRepositorySource` object has numerous functions for creating a `TextRepository` directly from files in a variety of formats.
