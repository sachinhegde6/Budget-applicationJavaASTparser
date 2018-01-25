
// Project name (artifact name in Maven)
name := "budgetapp-webapp-try1"


// orgnization name (e.g., the package name of the project)
organization := "io.budgetapp"

version := ""
//version := "1.3.0-SNAPSHOT"



// project description
description := "budget app webapplication"

// Enables publishing to maven repo
publishMavenStyle := true

// Do not append Scala versions to the generated artifacts
crossPaths := false

// This forbids including Scala related libraries into the dependency
autoScalaLibrary := false

logBuffered in Test := false

conflictManager := ConflictManager.strict

publishArtifact in Test := true

mainClass in(Compile, packageBin) := Some("io.budgetapp.BudgetApplication")


// library dependencies. (orginization name) % (project name) % (version)
libraryDependencies ++= Seq(
  "io.dropwizard" % "dropwizard-core" % "1.1.2",
  "io.dropwizard" % "dropwizard-migrations" % "1.1.2",
  "io.dropwizard" % "dropwizard-hibernate" % "1.1.2" excludeAll(ExclusionRule("com.fasterxml", "classmate")),
  "io.dropwizard" % "dropwizard-auth" % "1.1.2",
  "com.bazaarvoice.dropwizard" % "dropwizard-configurable-assets-bundle" % "0.2.2" excludeAll(ExclusionRule("com.google.guava", "guava"),ExclusionRule("io.dropwizard", "dropwizard-core"),ExclusionRule("io.dropwizard", "dropwizard-servlets")),
  "io.dropwizard" % "dropwizard-testing" % "1.1.2",
  "org.tuckey" % "urlrewritefilter" % "4.0.4",
  "org.postgresql" % "postgresql" % "9.4.1209",
  "org.hsqldb" % "hsqldb" % "2.3.4"
)