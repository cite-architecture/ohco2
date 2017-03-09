#ohco2: release notes

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
