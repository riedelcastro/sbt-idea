package org.sbtidea

import android.AndroidSupport
import java.io.File
import xml.NodeSeq

// cheating for now
import sbt.ScalaInstance

class IdeaProjectDomain

object IdeaLibrary {
  sealed abstract class Scope(val configName: String)
  case object CompileScope extends Scope("")
  case object RuntimeScope extends Scope("RUNTIME")
  case object TestScope extends Scope("TEST")
  case object ProvidedScope extends Scope("PROVIDED")
}

case class IdeaLibrary(id: String, name: String, evictionId: String, classes: Set[File], javaDocs: Set[File], sources: Set[File]) {
  def hasClasses = !classes.isEmpty
  def allFiles = classes ++ sources ++ javaDocs
}

case class IdeaModuleLibRef(config: IdeaLibrary.Scope, library: IdeaLibrary)

case class Directories(sources: Seq[File], resources: Seq[File], outDir: File) {
  def addSrc(moreSources: Seq[File]): Directories = copy(sources = sources ++ moreSources)
  def addRes(moreResources: Seq[File]): Directories = copy(resources = resources ++ moreResources)
}

case class ArtifactId(name:String, version:String, organization:String, scalaVersion:String) {
  def toFullName = "SBT: %s:%s_%s:%s".format(organization,name,scalaVersion,version)
}

case class SubProjectInfo(baseDir: File, name: String, dependencyProjects: List[String], classpathDeps: Seq[(File, Seq[File])], compileDirs: Directories,
                          testDirs: Directories, libraries: Seq[IdeaModuleLibRef], scalaInstance: ScalaInstance,
                          ideaGroup: Option[String], webAppPath: Option[File], basePackage: Option[String],
                          packagePrefix: Option[String], extraFacets: NodeSeq, scalacOptions: Seq[String],
                          includeScalaFacet: Boolean, androidSupport: AndroidSupport,artifactId:ArtifactId)

case class IdeaProjectInfo(baseDir: File, name: String, childProjects: List[SubProjectInfo], ideaLibs: List[IdeaLibrary])

case class IdeaUserEnvironment(webFacet: Boolean)

case class IdeaProjectEnvironment(projectJdkName :String, javaLanguageLevel: String,
                                  includeSbtProjectDefinitionModule: Boolean, projectOutputPath: Option[String],
                                  excludedFolders: Seq[String], compileWithIdea: Boolean, modulePath: String, useProjectFsc: Boolean,
                                  enableTypeHighlighting: Boolean)
