plugins {
    id("com.android.application")
}

val repositoryRoot = rootProject.projectDir.parentFile
val generatedReviewerAssets = layout.buildDirectory.dir("generated/reviewerAssets")
val copyReviewerHtml = tasks.register<Copy>("copyReviewerHtml") {
    from(repositoryRoot.resolve("index.html"))
    into(generatedReviewerAssets)
}

android {
    namespace = "com.aizen807.midtermreviewer"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.aizen807.midtermreviewer"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"
    }

    sourceSets.getByName("main").assets.srcDir(generatedReviewerAssets)

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

tasks.named("preBuild").configure {
    dependsOn(copyReviewerHtml)
}
