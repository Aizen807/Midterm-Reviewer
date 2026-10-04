# Midterm Reviewer

A study reviewer for **Understanding the Self (UTS)**, **PATHFIT / Physical Education**, and **Introduction to Computer**. The original browser reviewer remains at [`index.html`](index.html); the Android app packages that same page in a lightweight native WebView shell.

## What’s included

- Chapter and lesson notes for all three subjects
- Multiple-choice, identification, and mixed quiz modes, with explanations and missed-question review
- Flashcards, shuffle, and subject cheat sheets
- Weak-spot statistics saved on the device
- Android system Back navigation from a subject to the study hub
- Android print dialog for the cheat sheet (including **Save as PDF**)

The app content and study interactions are local and work without a network connection after installation. Google Fonts are optional and load only when the device is online. The weak-spot statistics use WebView local storage on the device.

## Build and run the Android app

1. Open the `android/` folder in Android Studio, or use the command line.
2. Install a JDK 17 or newer and Android SDK Platform 35 (Android Gradle Plugin 8.7.3).
3. From the repository root, run:

   ```bash
   cd android
   ./gradlew assembleDebug
   ```

4. The debug APK is written to `android/app/build/outputs/apk/debug/app-debug.apk`. Install it on a connected Android device with:

   ```bash
   adb install -r app/build/outputs/apk/debug/app-debug.apk
   ```

The Gradle build copies the repository-root `index.html` into the app’s generated assets, so the web reviewer remains the single source of study content and the Android app stays in sync on each build.

## Run the browser version

Open [`index.html`](index.html) in a modern browser. It is a self-contained HTML file; internet access is only needed for the optional web fonts.
