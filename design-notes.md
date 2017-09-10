
For `Corpus` objects:

## URN filtering

see notes on  `>`, `>=`, `<`, `<=`, `~~` and `><`

## Containment hierarchy

-  versions(u: CtsUrn): Set[CtsUrn]
-  exemplars(u: CtsUrn): Set[CtsUrn]

##  URN Topology of passages

-  define class RangeIndex (a: Int, b: Int)
-  indexPoint returns an Integer for a leaf node CtsUrn
-  indexRange returns a RangeIndex


Possible relations:

enum or the like with :

- equals
- precedes/follows
- contains/containedBy
- precedesAndOverlaps/overlapsAndPrecededBy
- overlapsAndFollows/overlapsAndFollowedBy

Urn-Urn pairings:

Point-Point can yield

- equals
- precedes/follows
- contains/containedBy


Point-Range can yield :

- equals
- precedes/follows
- contains/containedBy

Range-Range can yield:


- equals
- precedes/follows
- contains/containedBy
- precedesAndOverlaps/overlapsAndPrecededBy
- overlapsAndFollows/overlapsAndFollowedBy
