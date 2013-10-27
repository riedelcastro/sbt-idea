This is a clone of sbt-idea which replaces library dependencies within a project with module dependency
if artifact ids match. This is useful, for example, if you have a general purpose library A you depend
on in one of your more specific projects B, and you want to improve A as you develop B.
You can incorporate such a dependency in the B build file by using `dependsOn` between sbt projects,
but this then requires developers of B to get sources for the general purpose library A too, and place it in the file
system according to the build dependency.

<<<<<<< HEAD
To use it set up a "workspace" sbt project that aggregates a couple of sbt projects on your local harddisk.
For example:
=======
* [sbt](https://github.com/harrah/xsbt/wiki) 0.13
* For sbt 0.12.x version of the plugin, see [branch sbt-0.12](https://github.com/mpeltonen/sbt-idea/tree/sbt-0.12#requirements)
>>>>>>> remotes/upstream/sbt-0.13

```scala
import sbt._

object Workspace extends Build {
  lazy val root = Project("root", file(".")) aggregate(p1,p2)
  lazy val p1 = RootProject(file("../p1"))
  lazy val p2 = RootProject(file("../p2"))
}
```

<<<<<<< HEAD
Running `sbt gen-idea` in this project will create an intellij project file, as before. However, if project
`p1` has a library dependency to the artifact of `p2` (as determined by their name, org, scala version and version
settings), then the Intellij project will have a module dependency in module p1 to module p2, as opposed to
a library dependency (to the library jar in the local ivy repo).


=======
Add the following lines to ~/.sbt/0.13/plugins/build.sbt or PROJECT_DIR/project/plugins.sbt

    addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.5.2")

To use the **latest snapshot** version, add also Sonatype snapshots repository resolver into the same **plugins.sbt** file:

    resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

    addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0-SNAPSHOT")

Usage
-----

### Basic project

Use the `gen-idea` sbt task to create Idea project files.

### Project with dependencies

If you have two sbt projects A and B, and A depends on B, then use the `gen-idea` sbt task on Project A to create Idea project files for both projects.

The projects need to be set up in the following way:

*Project A:*

    import sbt._

    object A extends Build {
      lazy val A = Project("A", file(".")) aggregate(B) dependsOn(B)
      lazy val B = RootProject(file("../B"))
    }

*Project B:*

    import sbt._

    object B extends Build {
      lazy val B = Project("B", file("."))
    }

### Sources and javadocs

By default, classifiers (i.e. sources and javadocs) of dependencies are loaded if found and references added to Idea project files. If you don't want to download/reference them, use command 'gen-idea no-classifiers'.

Configuration settings
----------------------

### Exclude some folders

In your build.sbt:

    ideaExcludeFolders += ".idea"

    ideaExcludeFolders += ".idea_modules"

Or in your Build.scala:

    ...
    import org.sbtidea.SbtIdeaPlugin._
    ...
    lazy val myproject = Project(id = "XXXX" ....)
    .settings(ideaExcludeFolders := ".idea" :: ".idea_modules" :: Nil)



TODO...

License
-------

Licensed under the New BSD License. See the LICENSE file for details.
>>>>>>> remotes/upstream/sbt-0.13
