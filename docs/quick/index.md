---
layout: page
title:  Quick start
---


Import the library:


```scala
import edu.holycross.shot.ohco2._
```


## Create a TextRepositorySource

You can create a `TextRepository` from CEX-formatted text:


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

## Find out about the contents of a repository

A TextRepository has a Catalog (with metadata about each document in the repository) and a Corpus (with the text contents of each document).  Get a Vector of CatalogEntry objects for your repository:

```scala
val catalogEntries = textRepository.catalog.texts
```

See more extensive examples of [how to work with a text catalog](textcatalog)
