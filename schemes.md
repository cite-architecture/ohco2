Optional citation label catalog is a vector of citation label objects.

A citation label object has a scoping URN and a string label.  The ordering of these in a catalog is from most specific to most general.  Example:


| scope                                                      | label                  |
|:-----------------------------------------------------------|:-----------------------|
| `urn:cts:greekLit:tlg0012.tlg001.wackyItalian:2.494-2.759` | book/line/catalog unit |
| `urn:cts:greekLit:tlg0012:`                                | book/line              |

This means that within the specified range of the first specified version, you have a three-tiered citation scheme with the given labels.  In the rest of the "Homeric poetry" group, you have a two-tiered scheme labelled "book/line".


I would suggest the following serializations of this structure.

In our XML citation configuration file, an optional section at the beginning of each text group that could look like this:

    <citationLabel scope="urn:cts:greekLit:tlg0012.tlg001.wackyItalian:2.494-2.759" labels="book/line/catalog unit"/>
    <citationLabel scope="urn:cts:greekLit:tlg0012:" labels="book/line"/>

In `.cex` files, an optional section that could look like this:


    #!ctslabels
    urn:cts:greekLit:tlg0012.tlg001.wackyItalian:2.494-2.759#book/line/catalog unit
    urn:cts:greekLit:tlg0012:#book/line


In `ohco2`, the TextRepository object should get a new function `labelForPassage` that returns Option[String].  It returns the most specific labelling string it can find for a passage reference, or None if there is no label.  In the example above, the value for urn:cts:greekLit:tlg0012.tlg001.wackyItalian:2.500 would be the three-tiered string "book/line/catalog unit".  The value for urn:cts:greekLit:tlg0012.tlg001.wackyItalian:2.200-2.500 would be a two-tiered string.

URN twiddling should make this relatively easy to implement.
