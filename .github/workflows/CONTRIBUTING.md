# Contributing to Coordinate

Thank you for taking time to contribute!

Since `Coordinate` is an open source project and is maintained by a small team, we encourage interested members of the research community to seek out issues and propose changes where needed. In this document, we detail outlines to how you may contribute to the `Coordinate` app.

## Bug reporting

To report a bug with the app, create an [issue](https://github.com/PhenoApps/Coordinate/issues) that describes the bug along with the steps necessary to recreate it.

## Formatting

* In general, we try to utilize the default Android Studio style for code formatting. To automatically format your code in the current source code window, use `Cmd+Option+L` (on Mac) or `Ctrl+Alt+L` (on Windows and Linux).
* Use tabs instead of spaces.

## Naming Conventions

* Start variable names with a lowercase letter, and use camelCase rather than under_scores.
* Constants (public/private static final) are ALL_CAPS_WITH_UNDERSCORES.

## Short Methods

When possible, keep methods small and focused. The public methods of a class should read like a book, and essentially just aggregate the actions of private methods within that class. The private methods of a class should, as much as possible, be written in "functional style" with actions are confined to their local scope and their return value without having any other side effects on instance variables of the class.

## Other

This style guide is adapted from the [Signal App Android Style Guidelines](https://github.com/signalapp/Signal-Android/wiki/Code-Style-Guidelines). For any style questions not addressed here please refer to Google's [ASOP Java Style Guide](https://source.android.com/docs/setup/contribute/code-style) and [Kotlin Style Guide](https://developer.android.com/kotlin/style-guide), and prioritize consistency with the existing codebase.

## Submitting a pull request

* Fork the `Coordinate` repository from GitHub.

* Make your changes, commit to git, and then create a PR. **Please provide a descriptive PR**. Your PR should have the following:
  + Descriptive title (briefly document the change)
  + What number does this PR fix? Please provide this in the body of the PR with the language `Fixes #<issue number>`.


## Code of Conduct

Please note that the Coordinate project is released with a [Contributor Code of Conduct](CODE_OF_CONDUCT.md). By contributing to this project you agree to abide by its terms.