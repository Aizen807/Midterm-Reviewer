# Midterm Reviewer

A study reviewer for **Understanding the Self (UTS)**, **PATHFIT / Physical Education**, **Introduction to Computer**, **Purposive Communication (PURCOM)**, and **Reading in Philippine History**. The original browser reviewer remains at [`index.html`](index.html); the Android app packages that same page in a lightweight native WebView shell.

## What’s included

- Chapter and lesson notes for all five subjects
- Introduction to Computer: four parts covering computer basics, hardware, I/O and storage, and computer networking
- Understanding the Self: a lesson-by-lesson summary of all three chapters (including activities) modeled on the supplied review PDF, followed by all 80 pages of the original module as offline-readable text and the original PDF in a compatible browser
- PURCOM: five lesson groups, two shuffled quizzes with multiple-choice/identification/mixed modes, lesson filters, explanations, flashcards, a generated cheat sheet, and offline-readable text transcripts of the five unique supplied slide decks
- Reading in Philippine History: notes, source pathways, two quizzes, flashcards, and a printable cheat sheet based on the supplied American Colonial Period presentation
- Expanded UTS quiz and flashcard coverage; the cheat sheet is generated from the flashcards
- Multiple-choice, identification, and mixed quiz modes, with explanations and missed-question review
- Flashcards, shuffle, and subject cheat sheets
- Weak-spot statistics saved on the device
- Android system Back navigation from a subject to the study hub
- Android print dialog for the cheat sheet (including **Save as PDF**)

The app content, full UTS and PURCOM module text, and study interactions are local and work without a network connection after installation. Full-module text is extracted from the supplied source files and may not preserve diagrams or layout. The five unique PURCOM PDFs are kept in `purcom-materials/` for browser access; the UTS PDF remains in the repository root. The original networking presentation is kept in `computer-materials/` and linked from Part 4 in the browser version. The Reading in Philippine History source presentation is kept in `rph-materials/` and linked from its source-module section in the browser version. These source files are not bundled into the Android package. Duplicate PURCOM Lesson 2 and Lesson 3 uploads are included once. Google Fonts are optional and load only when the device is online. Weak-spot statistics use WebView local storage on the device.

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

Open [`index.html`](index.html) in a modern browser. The study reviewer works offline; internet access is only needed for optional web fonts. The original networking presentation, PURCOM slide PDFs, and Reading in Philippine History source PDF are linked from their browser-version sections.
