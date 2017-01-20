package edu.holycross.shot.ohco2
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._



class RepositorySpec extends FlatSpec {

  "A repository of citable texts"  should "have a corpus of citable nodes" in {
    val corpusSource = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(corpusSource)
    val catalogSource = "src/test/resources/scholiacatalog.txt"
    val catalog = Catalog(catalogSource)
    val repo = Repository(corpus, catalog)
    repo.corpus match {
      case c: Corpus => assert (true)
      case _ => fail("Failed to create corpus object")
    }
  }
  it should "have a catalog of citable texts" in {
    val corpusSource = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(corpusSource)
    val catalogSource = "src/test/resources/scholiacatalog.txt"
    val catalog = Catalog(catalogSource)
    val repo = Repository(corpus, catalog)
    repo.catalog match {
      case c: Catalog => assert (true)
      case _ => fail("Failed to create catalog object")
    }
  }
  it should "validate there is a 1-1 relation between texts cataloged as online and texts cited in the corpus" in  {
    val corpusSource = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(corpusSource)
    val catalogSource = "src/test/resources/scholiacatalog.txt"
    val catalog = Catalog(catalogSource)
    val repo = Repository(corpus, catalog)

    val catalogTexts = repo.catalog.texts.map(_.urn)
    assert(repo.corpus.citedWorks.toSet == catalogTexts.toSet)
  }
  it should "throw an Ohco2 exception if works cataloged as online do not appear in the corpus"  in {
    val corpusSource = "src/test/resources/shortscholia.tsv"
    val corpus = Corpus(corpusSource)
    val catalogSource = "src/test/resources/scholiacatalog.txt"
    val catalog = Catalog(catalogSource)
    try {
      Repository(corpus, catalog)
    } catch {
        case e : IllegalArgumentException => assert(e.getMessage() == "requirement failed: Online catalog (6 texts) did not match works appearing in corpus (1 texts)")
    }
  }
  it should "throw an Ohco2 exception if works in the corpus do not appear in the catalog as online"  in {
    val corpusSource = "src/test/resources/scholia-twocolumns.tsv"
    val corpus = Corpus(corpusSource)
    val catalogSource = "src/test/resources/shortcatalog.txt"
    val catalog = Catalog(catalogSource)
    try {
      Repository(corpus, catalog)
    } catch {
        case e : IllegalArgumentException => assert(e.getMessage() == "requirement failed: Online catalog (2 texts) did not match works appearing in corpus (6 texts)")
    }
  }
}
