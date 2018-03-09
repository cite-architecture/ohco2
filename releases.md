# `ohco2`: release notes

**10.5.2**: Verified cross-building publication to bintray.

**10.5.1**: Clean binary publication on bintray.

**10.5.0**: Restores automated cross-building functionality (text, package, publish for all supported Scala versions in both JVM and JS environments).

**10.4.3**: Fixed bugs with token-searching, in cases where an analytical exemplar had CitableNodes consisting only of a single punctuation mark.

**10.4.2**: Fixed bugs with returning first- and last-node from a corpus where exemplars are present, and a bug with delivering a text's label from a catalog.

**10.4.1**:  Now depending on XCite library v. 3.2.2.

**10.4.0**:  API-breaking change to parameter sequence of `ngramHisto` function to support default values when compiled of ScalaJS.  Corrects bug in handling of `prev` and `next` on a Corpus.

**10.3.1**:  Correct default column separator in `TextRepositorySource`.

**10.3.0**:  Implement algebra of containment and similarity on a Corpus.

**10.2.0**: Implement topology of text passage relationships.

**10.1.2**: Incorporate bug fixes in updates to library dependencies.

**10.1.1**: Update `xcite` for faster build with scala 2.12.3.

**10.1.0**: In the JVM subproject, adds functions to the `TextRepositorySource` object to create a `TextRepository` from data in local files.

**10.0.0**:  Breaking changes:  dependency on `hocuspocus` library eliminated, replaced with `SimpleTabulator` class in JVM branch.


**9.3.1**:  Correctly publish cross-compiled versions.

**9.3.0**: Enhances layout of `textContents` function value.

**9.2.0**: Adds function to `Corpus` for formatted text results of string search.

**9.1.0**:  Adds function for adding and subtracting catalogs and text repositories.

**9.0.1**:  Improved exception messages.

**9.0.0**: Supports version 2.0 of the CEX specification, requiring breaking changes in construction of `CatalogEntry`s.


**8.0.1**:  Fixes a bug where generating a corpus from local files used a default value rather than a specified value for delimiting string in CEX output.


**8.0.0**:  Adds a variety of functions related to ignoring punctuation while working with `CitablenNode`s .Removes `WispDriver` class.  WISP limits builds to Scala < 2.12.  WispDriver can be moved into a separate project for 2.11 users who want its functionality. With version 8.0.0, `ohco2` is still limited to building for Scala 2.11 because its dependency on the `hocuspocus` library causes issues in 2.12.  Future versions should replace the `hocuspocus` dependency with 2.12-compatible options.

**7.4.0**: `Catalog` and `Corpus` objects check for unique URN values.

**7.3.0**:  Adds support for citable text sources in Markdown format.  Switches to using the `CexParser` class to work with source data in `.cex` format.


**7.2.1**: Same functionality as `7.2.0`, but correctly published for use in all cross-compiled versions.

**7.2.0**: Create catalog, corpus or text repository from local XML files with accompanying XML catalog, using `hocuspocus` library.

**7.1.0**:
 All objects support serialization to CITE Exchange format (`.cex` ).

**7.0.1**: Updated dependency on `xcite` library fixes bug in URN matching.


**7.0.0**: Allows for cataloging of notional works.  This breaks API of `Catalog` constructor, although this is opaque when a `Catalog` is loaded from, e.g., a File source rather than directly constructed from Scala objects.


**6.12.2**: Changes matching of punctuation to an explicit list since regular expressions are broken in JS environment.

**6.12.1**: Coordinates dependencies with other libraries in JS environment.

**6.12**: Exposes main classes of entire library to JS environment.

**6.11**: Adds functions for searching for tokens to `Corpus` class.

**6.10**: Adds `kwic` display to `CitableNode`.

**6.9.1**: Fixes bugs matching URNs at a notional work level.

**6.9**: In JVM subproject, add WispDriver class for plotting directly from CITE data structures using WISP

**6.8**:  Add size and labelling functions on Catalog.

**6.7**:  Add functions for searching corpora. Allow empty lines in input to `CorpusSource.fromFile`.

**6.6**: Reimplements `~~` on a `Corpus` in hopes of coaxing a little more performance out of JS version.

**6.5.3**: Minor bug fix.

**6.5.2**: Fixes a bug when next/prev requests extend beyond limits of work in a multiwork corpus.

**6.5.1**: Fixes a bug in handling blank lines in text input to Catalog builder.

**6.5.**: Add functions for finding URNs for passages preceding or following other passages.

**6.4**: Add pretty printing of catalog entries.

**6.3**: Permit blank lines in input to constructor for Corpus.

**6.2**: Adds functions for computing and manipulating n-grams.

**6.1**: Adds `isEmpty` function to `Corpus` and `~~` function to `CitableNode`.

**6.0**: API-breaking changes in names of functions, along with addition of functions specified for complete library support of projects building UIs for text browsing and reading.

**5.0**: Adds (API-breaking) support for `xcite` v. 2.

**4.3**:  Adds filtering by `OrcaCollection`

**4.2**: Adds `urns` and `texts` functions to `Corpus`.

**4.1**: To `Corpus`, adds `++` and `--` functions, and application of `~~` to Vectors of CTS URNs.

**4.0**: Breaking changes:  twiddling on a Corpus now returns a new (possibly empty) Corpus; in the JVM subproject, the `CorpusFileIO` class is replaced with `CorpusSource`.

**3.1**: Introduces the twiddle operator `~~` for URN matching.

**3.0**: Initial release implementing the code library from https://github.com/cite-architecture/ohco2 in a cross-compiling build.
