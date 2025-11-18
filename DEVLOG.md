# Devlog

This file contains the process of the completion of assignments F1, F2 and F11 in this repository.

The solution to these assignments, the `README.md` and this devlog have all been created by Kasper van Maasdam.

## F1

To be able to run maven commands and work with Java, I used a Docker container. I used the following command:

```bash
docker run --rm -it -v /path/to/lib-version/:/usr/src/lib-version:Z -w /usr/src/lib-version maven:3.9.11-eclipse-temurin-25-noble /bin/bash
```

A boilerplate maven project has been created with the following command:

```bash
$ mvn archetype:generate -DgroupId=com.github.doda2025-team4.lib -DartifactId=lib-version -DarchetypeArtifactId=maven-archetype-quickstart
```

I learned how to do this from: https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html.

~~To compile the project and run the tests, run the following inside the interactively run container~~ **(Old! See [F2](#f2))**:

```bash
mvn clean package
```

I learned how to do this from: https://hub.docker.com/_/maven.

Built upon this project, I implemented what was discussed [here](https://stackoverflow.com/questions/3697449/retrieve-version-from-maven-pom-xml-in-code) to statically place the version specified in the pom.xml in a property.

To access the properties, I followed the instructions found [here](https://mkyong.com/java/java-read-a-file-from-resources-folder/).

I also created a test that verifies the string returned by getVersion() is equal to the version specified in `pom.xml` by parsing the pom.xml.

## F2

At first, I did not understand we are not allowed to use GitHub Actions Marketplace workflows for versioning and releasing. This is why I first use these. I later rewrote the application to work without those workflows. The devlog will describe my solution that uses the marketplace workflows and the rewriting is discussed in [Rewriting F2 and F11](#rewriting-f2-and-f11).

### Packaging

I updated `pom.xml` to include a `distributionManagement` section as specified in https://docs.github.com/en/actions/tutorials/publish-packages/publish-java-packages-with-maven.

I replaced the hardcoded version in `pom.xml` by the CI-friendly `${revision}`, as specified in: https://maven.apache.org/guides/mini/guide-maven-ci-friendly.html.

However, this means the unit test no longer works. So, I removed it. This also means, you need to provide a version in the packaging command:

```bash
mvn -Drevision=1.0.0 clean package
```

To create the GitHub Actions workflow that creates a package in the repo, I used the template provided at: https://github.com/actions/setup-java/blob/main/docs/advanced-usage.md#apache-maven-with-a-settings-path. The only thing I changed is to have it trigger when a version is released and I have added the `-Drevision` flag. Its value is `${{ github.event.release.name }}`, because the workflow is run every time a release is published (as specified [here](https://docs.github.com/en/rest/releases/releases?apiVersion=2022-11-28#get-a-release)).

### Releasing and Versioning

To automatically have a new releases be created with correct and automatically generated versions, I have used `cycjimmy/semantic-release-action@v5`. This is an GitHub Action from the GitHub Marketplace. For more information, visit: https://github.com/marketplace/actions/action-for-semantic-release#semantic_version.

This ensures a new release is published with correct versions according to [Semantic Release](https://github.com/semantic-release/semantic-release) when a commit is pushed to the main branch. This relies on the use of [conventional commits](https://www.conventionalcommits.org/en/v1.0.0/) (Though the breaking changes indicated by an exclamation mark are not considered). It should work for merge request to main. However, fast forward merges will likely not work because it doesn't explicitly push to the branch.

This also required, the use of a release configuration. I followed the guideline [here](https://github.com/semantic-release/semantic-release/blob/master/docs/usage/configuration.md#configuration) to create `.releaerc.json`. 

## F11

I added the develop branch as another branch where releases will be generated. For this one, I set the pre-release flag to be true. This was done both in the `.releaserc.json` and in the `semantic-versioning.yml`. In the future, more branches could be added like this, for example: rc, alpha, beta, etc.

Additionally, I added a feature to the library. It now generates some sentences. This is used to test to see if the minor version increases as expected.

Furthermore, the version of the Maven package has been cleaned. It no longer has a prefix.

To test a major version change, I implemented some breaking changes. Renaming a class. I also added features to test minor verion changes and a fix to test patches. Finally, I did some work on this documentation to see if indeed no new release would be made.

## Rewriting F2 and F11

Because I realized using GitHub Actions Marketplace workflows was not allowed, I had to remove those from the workflows I used them in. I wanted to keep the same functionality, so I rewrote the functionality I required. For this, I created two new workflows. Both to be used from within `semantic-versioning.yml`.

The first workflow I made is `get-semantic-verion.yml`. This looks at a branch and determines the new version of that branch based on tags that indicate previous (pre-release) versions and the commit messages since the most recent version. It again depends on commit conventions. In fact, my implementation actually adheres to the way [conventional commits](https://www.conventionalcommits.org/en/v1.0.0/) handles breaking changes with and exclamation mark. It assumes main is the release branch and assumes any other as pre-release branches.
