import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

ThisBuild / crossScalaVersions := Seq("2.12.15", "2.13.7", "3.1.0")

ThisBuild / versionScheme := Some("early-semver")

ThisBuild / testFrameworks += new TestFramework("munit.Framework")

val catsV = "2.7.0"
val catsEffectV = "3.3.14"
val fs2V = "3.2.3"
val http4sV = "0.23.7"
val circeV = "0.14.1"
val doobieV = "1.0.0-RC1"
val munitCatsEffectV = "1.0.7"


// Projects
lazy val `clippette` = project.in(file("."))
  .disablePlugins(MimaPlugin)
  .enablePlugins(NoPublishPlugin)
  .aggregate(core.jvm, core.js)

lazy val core = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Full)
  .in(file("core"))
  .jsConfigure(
    project => project.enablePlugins(ScalaJSBundlerPlugin)
  )
  .settings(
    name := "clippette",

    libraryDependencies ++= Seq(
      "org.typelevel"               %%% "cats-core"                  % catsV,
      "org.typelevel"               %%% "cats-effect"                % catsEffectV,


      "org.typelevel"               %%% "munit-cats-effect-3"        % munitCatsEffectV         % Test,

    )
  ).jsSettings(
    Compile / npmDependencies += "clipboardy" -> "3.0.0",
    scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule)},
  )

lazy val site = project.in(file("site"))
  .disablePlugins(MimaPlugin)
  .enablePlugins(DavenverseMicrositePlugin)
  .dependsOn(core.jvm)
  .settings{
    import microsites._
    Seq(
      micrositeDescription := "Clipboard Manager",
    )
  }
