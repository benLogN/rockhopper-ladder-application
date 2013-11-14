import sbt._
import Keys._
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import com.mojolly.scalate.ScalatePlugin._
import ScalateKeys._

object RockhopperLadderApplicationBuild extends Build {
  val Organization = "es.chickade"
  val Name = "Rockhopper Ladder Application"
  val Version = "0.1.0"
  val ScalaVersion = "2.10.2"
  val ScalatraVersion = "2.2.1"

  lazy val project = Project (
    "rockhopper-ladder-application",
    file("."),
    settings = Defaults.defaultSettings ++ ScalatraPlugin.scalatraWithJRebel ++ scalateSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += "Typesafe Maven" at "http://repo.typesafe.com/typesafe/releases",
      resolvers += "Sonatype Maven" at "https://oss.sonatype.org/content/repositories/releases",
      libraryDependencies ++= Seq(
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
        "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
        "ch.qos.logback" % "logback-classic" % "1.0.6" % "runtime",
        "org.eclipse.jetty" % "jetty-webapp" % "8.1.8.v20121106" % "container",
        "play" %% "anorm" % "2.1.5",
        "postgresql" % "postgresql" % "9.1-901.jdbc4",
        "com.lambdaworks" % "scrypt" % "1.4.0",
        "com.sandinh" % "play-jdbc-standalone_2.10" % "2.0.1_2.2",
        "org.scalatra" %% "scalatra-json" % "2.2.1",
        "org.json4s"   %% "json4s-jackson" % "3.2.4",
        "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container;provided;test" artifacts (Artifact("javax.servlet", "jar", "jar"))
      ),
      scalateTemplateConfig in Compile <<= (sourceDirectory in Compile){ base =>
        Seq(
          TemplateConfig(
            base / "webapp" / "WEB-INF" / "templates",
            Seq.empty,  /* default imports should be added here */
            Seq(
              Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
            ),  /* add extra bindings here */
            Some("templates")
          )
        )
      }
    )
  )
}
