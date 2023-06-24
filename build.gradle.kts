buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
    dependencies {
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.6")
        classpath("com.google.gms:google-services:4.3.15")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.46.1")
        classpath("io.github.c0nnor263:plugin:11.09")
        classpath("gradle.plugin.com.onesignal:onesignal-gradle-plugin:0.14.0")
    }
}

plugins {
    id("com.android.application") version "8.0.2" apply false
    id("com.android.library") version "8.0.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.21" apply false
}