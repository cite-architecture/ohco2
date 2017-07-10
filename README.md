# `ohco2`

## What it is

`ohco2` is a cross-platform library for working with corpora of citable texts.

## Current version: 9.3.0


Status:  **active development**. [Release notes](releases.md)

## License

[GPL 3.0](https://opensource.org/licenses/gpl-3.0.html)

## Using, building, testing

`ohco2` is compiled for both the JVM and ScalaJS using scala 2.11.  Binaries for all platforms are available from jcenter.  If you are using sbt, include `Resolver.jcenterRepo`in your list of resolvers

    resolvers += Resolver.jcenter

and  add this to your library dependencies:

    "edu.holycross.shot.cite" %% "ohco2" % VERSION

For maven, ivy or gradle equivalents, refer to <https://bintray.com/neelsmith/maven/ohco2>.



To build from source and test for a given version, use normal sbt commands (`compile`, `test` ...).

You can also test or run tasks against all versions, using `+` before the task name.  E.g.,  `sbt "+ test"` runs the `test` task against all versions.

`ohco2` is used by the CITE library manager `scm`.  The `scm` wiki at <https://github.com/cite-architecture/scm/wiki> includes examples of how to create an ohco2 `TextRepository` from local files in various formats.
