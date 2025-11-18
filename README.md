# Lib Version

This repository contains the solutions to the following functionalities:

- *F1: Create a Version-aware Library*: Add a version-aware Maven library `lib-version` to your organization. The library should contain a class `VersionUtil` that can be asked for its version. The implementation should parse meta-data that is included in the package or explicitly store the version in a separate resource ﬁle. The `app` should depend on the library and use the `VersionUtil` somewhere. This feature request has two purposes: a) it allows you to illustrate that you understand how to release and reuse a library and b) the awareness about the version can prove useful later, for example, to add meaningful system information during the monitoring.
	- **Info:** Do not rely on parsing data from the version-control system, like tags, when determining the version during runtime, as it might not always be available when the library is reused.
- *F2: Library Release:* A workﬂow is used to automatically package and version `lib-version` and to release it in the GitHub package registry for Maven.
	- **Caution:** Do *NOT* release your projects to *Maven Central*! Do not pollute release repositories with test releases!
- *F11: `lib-version` pre-releases:* All stable releases of all your artifacts must be properly and automatically versioned. However, we want to further advance the versioning concept for the `lib-version` repository. The goal is to automate both the creation of stable releases, while also automating the release of pre-release versions from branches.

Look at the release tab on the right on the GitHub repository to see the releases. They are all automatically generated. The packages are automatically generated as well.

Look at the workflows in `.github/workflows/` to see how the workflows are designed. An explanation is found below.

Look at the `app` repository to see the library in action.

## Functionality

This library contains two classes: `VersionUtil`, as requested, and `GoodSentenceGenerator`. The `VersionUtil` class has two methods: `getVersion()` and `getName()`. These return the library version and name as a `String` respectively. These are read from a properties file in a filtered resource directory. This approach to getting the library version and name are based on [this StackOverflow thread](https://stackoverflow.com/questions/3697449/retrieve-version-from-maven-pom-xml-in-code). The properties file is loaded in the constructor of `VersionUtil`.

The `GoodSentenceGenerator` class contains the logic that the `app` repository uses the `lib-version` library for. It is a class with one method: `generateSentence()`. This method returns a random string from a predetermined list of strings. This class is also unit tested.

## Workflows

There are four workflow files in this repository: `semantic-versioning.yml`, `get-semantic-version.yml`, `create-release.yml` and `maven-publish.yml`. `semantic-versioning.yml` is triggerd on pushes to the `main` and `develop` branches. This workflow uses `get-semantic-version.yml` to get the newest semantic version of the branch that is pushed to. If this is a newer version than the latest, it will use `create-release.yml` to create a release of that version. `maven-publish.yml` is triggered when a new release is made. It packages and deploys the package, giving it the version that is the tag of the release. Because it has a separate trigger, it is also triggered when releases are manualy published. This is a conscious decision.

Now follows a more detailed explanantion of `get-semantic-version.yml`, because that workflow is most complex.

### `get-semantic-version.yml`

This workflow parses the commits and tags of the provided branch to determine the new version of the latest commit of that branch and whether that version is new compared to the previous version. It assumes that the release branch is `main` and that any other branch is a `pre-release` branch. For the release branch, the version is in the format `vX.Y.Z` and for a pre-release branch `branch`, the version is in the format `vX.Y.Z-branch.N`.

The versions are determined based on the commit messages of the commits since the latest release or pre-release tag and those tags themselves. A version is bumped according to the semantic versioning with (major, minor and patch). The commit messages are assumed to be in the form of [conventional commits](https://www.conventionalcommits.org/en/v1.0.0/). If there is a commit message since the last corresponding version tag that contains either the string `BREAKING CHANGE` or is marked with an exclamation mark (`!`), then a breaking change is assumed to have been made, so the major version is bumped. If this is not the case, but there is a commit message that describes either a feature or a change in performance, then the minor version is bumped. If this is also not the case, but a commit message that indicates a fix is found, then the patch version is bumped. If none of these cases were found, then the version is not bumped.

The pre-release branch has a special case. There, it may happen that new breaking changes, features or patches are introduced while it already has a pre-release version that reflects that. In this case, no version should be bumped, because relative to the release branch, nothing extra new happened (only relative to the pre-release branch). So, the pre-release number is increased. That is the `N` in the example `vX.Y.Z-branch.N` for some pre-release branch `branch`.

The way this workflow is used in `semantic-versioning.yml` makes it so versions of only the branches `main` and `develop` are tracked.
