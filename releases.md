# ohco2: release notes

**6.12.2**: Changes matching of punctuation to an explicit list since regular expressions are broken in JS environment.

**6.12.1**: Coordinates dependencies with over libraries in JS environment.

**6.12**: Exposes main classes of entire library to JS environment.

**6.11**: adds functions for searching for tokens to `Corpus` class.

**6.10**: Adds `kwic` display to `CitableNode`.

**6.9.1**: Fixes bugs matching URNs at a notional work level.

**6.9**: In JVM subproject, add WispDriver class for plotting directly from CITE data structures using WIPS.

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

**5.0**: adds (API-breaking) support for `xcite` v. 2.

**4.3**:  adds filtering by `OrcaCollection`


**4.2**: adds `urns` and `texts` functions to `Corpus`.

**4.1**: to `Corpus`, adds `++` and `--` functions, and application of `~~` to Vectors of CTS URNs.

**4.0**: breaking changes:  twiddling on a Corpus now returns a new (possibly empty) Corpus; in the JVM subproject, the `CorpusFileIO` class is replaced with `CorpusSource`.

**3.1**: introduces the twiddle operator `~~` for URN matching.

**3.0**: initial release implementing the code library from https://github.com/cite-architecture/orca in a cross-compiling build.
