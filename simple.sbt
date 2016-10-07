
name := "Simple Project"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "1.4.0"

libraryDependencies ++= Seq(
  "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" artifacts (
    Artifact("javax.servlet", "jar", "jar")
  )
)

