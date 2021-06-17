# `ohco2`

## What it is

`ohco2` is a cross-platform library for working with corpora of citable texts.

## Current version: 10.20.4

Status:  **active development**. [Release notes](releases.md)

## License

[GPL 3.0](https://opensource.org/licenses/gpl-3.0.html)


## Documentation

See <http://cite-architecture.org/ohco2/>.


## Using, building, testing

`ohco2` can be built for both the JVM and ScalaJS using any version of Scala 2.11 or higher.  Binaries for all three versions are available from the Nexus repository on <terracotta.hpcc.uh.edu/nexus>.

    	resolvers += "Nexus" at "https://terracotta.hpcc.uh.edu/nexus/repository/maven-releases/"


and  add this to your library dependencies:

    "edu.holycross.shot.cite" %% "ohco2" % VERSION

For maven, ivy or gradle equivalents, refer to <https://bintray.com/neelsmith/maven/ohco2>.


To build from source and test for a given version, use normal sbt commands (`compile`, `test` ...).

You can also test or run tasks against all versions, using `+` before the task name.  E.g.,  `sbt "+ test"` runs the `test` task against all versions.

`ohco2` is used by the CITE library manager `scm`.  The `scm` documentaton at <https://cite-architecture.github.io/scm/> includes examples of how to create an ohco2 `TextRepository` from local files in various formats.
