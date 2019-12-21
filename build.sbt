//name := "OHCO2 text library"

// XML libraries moved in 2.11, so can't support 2.10.
// Airframe logging only for >= 2.12
lazy val supportedScalaVersions = List("2.12.4") //List("2.11.8", "2.12.4")


lazy val root = project.in(file(".")).
    aggregate(crossedJVM, crossedJS).
    settings(
      crossScalaVersions := Nil,
      publish / skip := true
    )

lazy val crossed = crossProject.in(file(".")).
    settings(
      name := "ohco2",
      organization := "edu.holycross.shot",

      version := "10.18.0",

      licenses += ("GPL-3.0",url("https://opensource.org/licenses/gpl-3.0.html")),
      resolvers += Resolver.jcenterRepo,
      resolvers += Resolver.bintrayRepo("neelsmith", "maven"),
      libraryDependencies ++= Seq(
        "org.scala-js" %% "scalajs-stubs" % scalaJSVersion % "provided",
        "org.scalatest" %%% "scalatest" % "3.0.1" % "test",
        "org.scala-lang.modules" %% "scala-xml" % "1.0.6",


        "org.wvlet.airframe" %%% "airframe-log" % "19.8.10",

        "edu.holycross.shot.cite" %%% "xcite" % "4.1.1",
        "edu.holycross.shot" %%% "cex" % "6.3.3",
        "edu.holycross.shot" %%% "seqcomp" % "1.0.0"
      )
    ).
    jvmSettings(
      tutTargetDirectory := file("docs"),
      tutSourceDirectory := file("tut"),
      crossScalaVersions := supportedScalaVersions

    ).
    jsSettings(
      skip in packageJSDependencies := false,
      scalaJSUseMainModuleInitializer in Compile := true,
      crossScalaVersions := supportedScalaVersions


    )


lazy val crossedJVM = crossed.jvm//.enablePlugins(TutPlugin)
lazy val crossedJS = crossed.js
