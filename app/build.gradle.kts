@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    //     TODO Google services
//    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.onesignal.androidsdk.onesignal-gradle-plugin")
    id("io.github.c0nnor263.obfustring-plugin")
}

android {
    namespace = "com.miniclip.footb"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.miniclip.footb"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")



    // AppsFlyer
    implementation("com.appsflyer:af-android-sdk:6.11.1")

    // Facebook
    implementation("com.facebook.android:facebook-android-sdk:16.0.1")

    // OneSignal
    implementation("com.onesignal:OneSignal:4.8.6")

    // GAID
    implementation("com.google.android.gms:play-services-ads-identifier:18.0.1")

    // Install Referrer
    implementation("com.android.installreferrer:installreferrer:2.2")



    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.1.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-config-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.46.1")
    implementation("com.google.dagger:hilt-android-compiler:2.46.1")

    // Room
    val roomVersion = "2.5.1"
    implementation("androidx.room:room-compiler:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.room:room-runtime:$roomVersion")


    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("io.github.c0nnor263:obfustring-core:11.09")
}