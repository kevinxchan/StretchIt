# :muscle: RoutineMe [![Build Status](https://travis-ci.com/kevinxchan/RoutineMe.svg?branch=master)](https://travis-ci.com/kevinxchan/RoutineMe)

A native Android app for logging and running routines

## Introduction 

Physical activity is an important part of our overall health, yet
there can be challenges in creating, recording, and running exercise
routines. RoutineMe is an Android app where users can create routines, add 
components to each routine, and run each component sequentially
to assist you in your workout, so you can say goodbye to the old
days of setting timers and repeating for each exercise.

## Getting started

### Overview

In RoutineMe, each exercise falls under one of three categories:
countdown, exercise, and rest. Each exercise also has a duration,
perfect for timing activities such as stretching, running, or
sustained exercises like planking. A routine is a set of exercises
to be ran in succession, automating the process transitioning 
between exercises and avoiding setting timers manually. 

When a routine is ran, RoutineMe will keep track of the number
of times you've ran that routine to completion. Other statistics
are planned to be tracked in the future as well.

### Installation

Currently, RoutineMe does not have a release APK, but a debug
build can be ran to run the app. Simply clone this repository 
and run the app on an emulator through your IDE or through the 
command line:

```bash
git clone https://github.com/kevinxchan/RoutineMe.git
cd /path/to/RoutineMe

# building the debug APK
./gradlew assembleDebug
# run on an emulator
emulator -avd avd_name
adb install app/outputs/debug/app-debug.apk
# or run on a device
adb -d install app/outputs/debug/app-debug.apk

# or, to install on a running emulator or connected USB device
./gradlew installDebug
```

If running on Windows, replace all `./gradlew` with `gradlew`.
See [here][cmd-build] for more information on app installation 
from the command line.

### Development

A list of features to be implemented are kept under the Projects
tab of this repository. The master branch will always be stable,
and will be updated with features periodically.

[cmd-build]: https://developer.android.com/studio/build/building-cmdline
