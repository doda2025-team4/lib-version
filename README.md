# Lib Version

This repository will contain the solutions to the following functionalities:

- *F1: Create a Version-aware Library*: Add a version-aware Maven library `lib-version` to your organization. The library should contain a class `VersionUtil` that can be asked for its version. The implementation should parse meta-data that is included in the package or explicitly store the version in a separate resource ﬁle. The `app` should depend on the library and use the `VersionUtil` somewhere. This feature request has two purposes: a) it allows you to illustrate that you understand how to release and reuse a library and b) the awareness about the version can prove useful later, for example, to add meaningful system information during the monitoring.
	- **Info:** Do not rely on parsing data from the version-control system, like tags, when determining the version during runtime, as it might not always be available when the library is reused.
- *F2: Library Release:* A workﬂow is used to automatically package and version `lib-version` and to release it in the GitHub package registry for Maven.
	- **Caution:** Do *NOT* release your projects to *Maven Central*! Do not pollute release repositories with test releases!
- *F11: `lib-version` pre-releases:* All stable releases of all your artifacts must be properly and automatically versioned. However, we want to further advance the versioning concept for the `lib-version` repository. The goal is to automate both the creation of stable releases, while also automating the release of pre-release versions from branches.

## F1

A boilerplate maven project has been created with the following command:

```bash
$ mvn archetype:generate -DgroupId=com.github.doda2025-team4.lib -DartifactId=lib-version -DarchetypeArtifactId=maven-archetype-quickstart
```

I learned how to do this from: https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html.

I used the following docker command to enter a container where everything required is installed:

```bash
docker run --rm -it -v /path/to/lib-version/:/usr/src/lib-version:Z -w /usr/src/lib-version maven:3.9.11-eclipse-temurin-25-noble /bin/bash
```

To compile the project, run the following inside the interactively run container:

```bash
mvn package
```

To run the executable generated, run the following inside the interactively run container:

```bash
java -cp target/lib-version-1.0-SNAPSHOT.jar com.github.doda2025_team4.lib.App
```

I learned how to do this from: https://hub.docker.com/_/maven.

Built upon this project, I implemented what was discussed [here](https://stackoverflow.com/questions/3697449/retrieve-version-from-maven-pom-xml-in-code) to statically place the version specified in the pom.xml in a property.

To access the properties, I followed the instructions found [here](https://mkyong.com/java/java-read-a-file-from-resources-folder/).

Now, when compiling and running the project, the following should be printed:

```
I am version: 1.0-SNAPSHOT
```
