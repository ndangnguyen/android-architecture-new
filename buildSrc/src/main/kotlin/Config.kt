object Versions {
    const val kotlin = "1.3.71"
    const val androidGradlePlugin = "3.5.3"

    const val compileSdkVersion = 29
    const val buildToolsVersion = "29.0.2"
    const val minSdkVersion = 21
    const val targetSdkVersion = 29

    const val appcompat = "1.1.0"
    const val coreKtx = "1.1.0"
    const val constraintLayout = "2.0.0-beta1"
    const val material = "1.2.0-alpha02"
}

object ClassPaths {
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

object Plugins {
    const val androidApp = "com.android.application"
    const val kotlinAndroid = "android"
    const val kotlinExt = "android.extensions"
    const val kotlinApt = "kapt"
}

object Dependencies {
    const val supportAppCompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val supportDesign = "com.google.android.material:material:${Versions.material}"
    const val supportCoreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val supportCore = "androidx.core:core:${Versions.coreKtx}"

    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
}