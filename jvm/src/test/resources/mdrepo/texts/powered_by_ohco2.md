# Powered by OHCO2

The [OHCO2 model of citable text](http://cite-architecture.github.io/ohco2/) can be implemented in many ways.  The `hocuspocus` library can create a directed graph in RDF for repositories of texts available in local files in any of the following formats:

- XML
- "bland" markdown
- 82XF
- 2-column delimited


## Hierarchical formats

### XML format

XML documents must be well formed.  Optionally, they may validate against a schema or DTD.  The repository manager must be able to map each text's canonical citation scheme onto its XML markup scheme.  (This mapping is expressed in the repository's text configuration document.)


### Markdown




For markdown files [bland markdown](http://mcneill.io/bland-markdown/)" subset of Common Markdown, a citation scheme is automatically generated according to the following logic:

- header elements define hierarchical levels, identified as `h1` .. `h`*n*
- block elements define citable nodes within the hierarchy, as `n1` .. `n`*n*

Note that hierarchical levels *must* begin with level 1, and follow in successive order.


## Flat formats

### OHCO2 eXchange Format (82XF)

The OHCO2 eXchange Format (82XF) was designed to fully express the OHCO2 model.  Files in 82XF need no further information to be expressed as RDF graphs.


### 2-column delimited format


It is also possible to express the OCHO2 model with simple pairings of URNs and text contents, *provided that the pairings are ordered*.    
