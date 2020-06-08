# RealDrip Android
The Android client for the [RealDrip device](https://treplabs.co/realdrip). 
## \[ 🚧 Work in progress 🛠 👷🔧👷‍♀️️🔧️ 🚧 \]
[![Build Status](https://travis-ci.com/r4sh33d/SlidingUpMenu.svg?token=8TPyvGS2YqpBT3ypdxNc&branch=master)](https://travis-ci.com/r4sh33d/SlidingUpMenu)
## Code Formatting
Code Formatting is done with the gradle spotless plugin, using [ktlint](https://github.com/pinterest/ktlint) as the linter. Other settings for the plugin can be configured in [spotless.gradle](spotless.gradle). To format code, run

```gradle
./gradlew spotlessApply
```
To automate the formatting process every time you commit, you can activate the pre-configured [pre-commit-checks.sh](https://github.com/TREP-LABS/realdrip-android/blob/master/config/pre-commit-checks.sh) hook. To activate, run the following commands:

```gradle
chmod u+x config/pre-commit-checks.sh
ln -s $PWD/config/pre-commit-checks.sh .git/hooks/pre-commit
```
