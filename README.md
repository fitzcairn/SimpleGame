# SimpleGame

This is an example game implemented using [SimpleEngine](https://github.com/fitzcairn/SimpleEngine), a 2D Java game engine.  Please feel free to copy shamelessly to make your own games.

This example game runs as a JavaFX Application and an Android app.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

I suggest you do the following before cloning this project:

* Download the [IntelliJ IDEA](https://www.jetbrains.com/idea/download).
* Ensure you have [Java JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or later installed.
* I also suggest you install the latest version of [Gradle](https://gradle.org/install/).
* Create a [GitHub](https://github.com/) account.
* Set up [GitHub over SSH](https://help.github.com/articles/connecting-to-github-with-ssh/).

### Setting Up IntelliJ

* Clone this repository to your local machine (directions [here](https://help.github.com/articles/cloning-a-repository/)).
* I strongly suggest [creating a local branch](https://git-scm.com/book/en/v2/Git-Branching-Basic-Branching-and-Merging) before making any changes.
* Open IntelliJ, and create your project by [importing from build.gradle](https://www.jetbrains.com/help/idea/gradle.html#gradle_import). (Note: I suggest quickly reading up on [how IntelliJ works with Gradle](https://www.jetbrains.com/help/idea/getting-started-with-gradle.html), but this is optional--you shouldn't need to mess with the build config.)

### Building and Running: JavaFX

* [Open the Gradle tool window in IntelliJ](https://www.jetbrains.com/help/idea/jetgradle-tool-window.html).
* Build and run the app.  To do this:
    * Expand ":modules:javafx"
    * Expand "Tasks"
    * Expand "application"
    * Double-click "run".

### Building and Running: Android

First, a few more setup steps:

* Get the Android SDK.  There is [a bug in IntelliJ](https://youtrack.jetbrains.com/issue/IDEA-180999) that makes this very hard to do without using Android Studio.  I suggest [downloading and installing Android Studio](https://developer.android.com/studio/install?pkg=tools), and using that to install the Android SDKs.
* If you'd like to continue to develop in IntelliJ, add the Android SDKs to your project through [this workaround](https://stackoverflow.com/questions/45268254/how-do-i-install-the-standalone-android-sdk-and-then-add-it-to-intellij-idea-on/45268592#45268592).

Now you're ready to build for Android:

* Add an [Android run/debug configuration](https://www.jetbrains.com/help/idea/running-and-debugging-android-applications.html) to IntelliJ.
* Build an unsigned APK.  To do this:
    * Expand ":modules:android"
    * Expand "Tasks"
    * Expand "build"
    * Double-click "build".
* Run --> "Run 'android'", and follow instructions to create an AVD and launch the APK.

## Running Tests

There aren't any tests for SimpleGame, but please feel free to add some. :)

## Contributing

I rather like this [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426); let's follow that for any pull requests.

## Versioning

I use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/fitzcairn/SimpleGame/tags).

## Authors

* **Steve Martin** - *Initial work* - [Fitzcairn](https://github.com/fitzcairn)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

