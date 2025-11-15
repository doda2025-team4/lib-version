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

To compile the project and run the tests, run the following inside the interactively run container **(Old! See [F2](#f2))**:

```bash
mvn clean package
```

I learned how to do this from: https://hub.docker.com/_/maven.

Built upon this project, I implemented what was discussed [here](https://stackoverflow.com/questions/3697449/retrieve-version-from-maven-pom-xml-in-code) to statically place the version specified in the pom.xml in a property.

To access the properties, I followed the instructions found [here](https://mkyong.com/java/java-read-a-file-from-resources-folder/).

Now, when running the tests, you should see the tests passing. The test verifies the string returned by getVersion() is equal to the version specified in `pom.xml`.

## F2

### Packaging

To create the GitHub action that creates a package in the repo, I used the template provided at: https://github.com/actions/setup-java/blob/main/docs/advanced-usage.md#apache-maven-with-a-settings-path. The only thing I changed is to have it trigger when a version is released.

I updated `pom.xml` to include a `distributionManagement` section as specified in https://docs.github.com/en/actions/tutorials/publish-packages/publish-java-packages-with-maven.

I replaced the hardcoded version in `pom.xml` by the CI-friendly `${revision}`, as specified in: https://maven.apache.org/guides/mini/guide-maven-ci-friendly.html.

However, this means the unit test no longer works. So, I removed it. This also means, you need to provide a version in the packaging command:

```bash
mvn -Drevision=1.0.0-SNAPSHOT clean package
```

The `-Drevision` flag also needs to be used in the CI packaging workflow. Its value is `${{ github.event.release.name }}`, because the packaging workflow is run every time a release is published.

### Releasing and Versioning

To automatically have a new releases be created with correct and automatically generated versions, I have used `cycjimmy/semantic-release-action@v5`. This is an GitHub Action from the GitHub Marketplace. For more information, visit: https://github.com/marketplace/actions/action-for-semantic-release#semantic_version.

This ensures a new release is published with correct versions according to [Semantic Release](https://github.com/semantic-release/semantic-release) when a commit is pushed to the main branch. This relies on the use of [conventional commits](https://www.conventionalcommits.org/en/v1.0.0/). It should work for merge request to main. However, fast forward merges will likely not work because it doesn't explicitly push to the branch.

This also required, the use of a release configuration. I followed the guideline [here](https://github.com/semantic-release/semantic-release/blob/master/docs/usage/configuration.md#configuration) to create `.releaerc.json`. 

## F11

I added the develop branch as another branch where releases will be generated. For this one, I set the pre-release flag to be true.

Additionally, I added a feature to the library. It now generates some sentences. This is used to test to see if the minor version increases as expected.

Furthermore, the version of the Maven package has been cleaned. It no longer has a prefix.

To test a major version change, I implemented some breaking changes. Renaming a class. Also added features to test minor verion changes and a fix to test patches.
