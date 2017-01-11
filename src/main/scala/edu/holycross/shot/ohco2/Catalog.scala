package edu.holycross.shot.ohco2

import edu.holycross.shot.cite._

case class Catalog (texts: Vector[CatalogEntry])

case class CatalogEntry(urn: CtsUrn, citationScheme: String, groupName: String, workTitle: String, versionLabel: String, exemplarLabel: Option[String] = None, online: Boolean = true)
