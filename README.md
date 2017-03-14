# `ohco2`



##What it is

`ohco2` is a cross-platform library for working with corpora of citable texts.

##Current version: 6.10

Status:  **active development**. [Release notes](releases.md)

## License

[GPL 3.0](https://opensource.org/licenses/gpl-3.0.html)

## Using, building, testing

`ohco2` compiled for both the JVM and ScalaJS using scala versions 2.10 and 2.11.  Binaries for all platforms are available from jcenter.  If you are using sbt, include `Resolver.jcenterRepo`in your list of resolvers

    resolvers += Resolver.jcenterRepo

and  add this to your library dependencies:

    "edu.holycross.shot.cite" %% "ohco2" % VERSION

For maven, ivy or gradle equivalents, refer to <https://bintray.com/neelsmith/maven/ohco2>.



To build from source and test, use normal sbt commands (`compile`, `test` ...).
