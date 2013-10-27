This is a clone of sbt-idea which replaces library dependencies within a project with module dependency
if artifact ids match. This is useful, for example, if you have a general purpose library A you depend
on in one of your more specific projects B, and you want to improve A as you develop B.
You can incorporate such a dependency in the B build file by using `dependsOn` between sbt projects,
but this then requires developers of B to get sources for the general purpose library A too, and place it in the file
system according to the build dependency.

To use it set up a "workspace" sbt project that aggregates a couple of sbt projects on your local harddisk.
For example:

```scala
import sbt._

object Workspace extends Build {
  lazy val root = Project("root", file(".")) aggregate(p1,p2)
  lazy val p1 = RootProject(file("../p1"))
  lazy val p2 = RootProject(file("../p2"))
}
```

