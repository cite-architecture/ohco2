name := "OHCO2 text library"

// XML libraries moved in 2.11, so can't support 2.10.
// Scalajs only available through 2.12.1.
crossScalaVersions := Seq("2.11.8", "2.12.1")
scalaVersion := "2.11.8"

lazy val root = project.in(file(".")).
    aggregate(crossedJVM, crossedJS).
    settings(
      publish := {},
      publishLocal := {}

    )

lazy val crossed = crossProject.in(file(".")).
    settings(
      name := "ohco2",
      organization := "edu.holycross.shot",
      version := "10.0.0",
      licenses += ("GPL-3.0",url("https://opensource.org/licenses/gpl-3.0.html")),
      resolvers += Resolver.jcenterRepo,
      resolvers += Resolver.bintrayRepo("neelsmith", "maven"),
      libraryDependencies ++= Seq(
        "org.scala-js" %% "scalajs-stubs" % scalaJSVersion % "provided",
        "org.scalatest" %%% "scalatest" % "3.0.1" % "test",
        "org.scala-lang.modules" %% "scala-xml" % "1.0.6",

        "edu.holycross.shot.cite" %%% "xcite" % "2.6.0",
        "edu.holycross.shot" %%% "cex" % "6.0.0",
        "edu.holycross.shot" %%% "seqcomp" % "1.0.0"
      )
    ).
    jvmSettings(
    ).
    jsSettings(
      skip in packageJSDependencies := false,
      persistLauncher in Compile := true,
      persistLauncher in Test := false

    )

lazy val crossedJVM = crossed.jvm
lazy val crossedJS = crossed.js.enablePlugins(ScalaJSPlugin)
