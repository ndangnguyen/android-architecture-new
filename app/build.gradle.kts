plugins {
    id(Plugins.androidApp)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinExt)
    kotlin(Plugins.kotlinApt)
}

android {
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion(Versions.buildToolsVersion)

    defaultConfig {
        applicationId = "com.ndn.androidarchitecture"
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        versionCode = 1
        versionName = "1.0"
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions("default")

    productFlavors {
        create("Dev") {
            applicationIdSuffix = ".dev"
            versionCode = 1
            versionName = "1.0.0"
            resValue("string", "app_name", "Android Architecture DEV")
            buildConfigField("String", "END_POINT", "\"https://jsonplaceholder.typicode.com\"")
        }
        create("Prod") {
            versionCode = 1
            versionName = "1.0.0"
            resValue("string", "app_name", "Android Architecture")
            buildConfigField("String", "END_POINT", "\"https://jsonplaceholder.typicode.com\"")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    dexOptions {
        javaMaxHeapSize = "4096m"
        preDexLibraries = true
        threadCount = 6
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    androidExtensions {
        isExperimental = true
    }

    kapt {
        useBuildCache = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.kotlinStdlib)
    implementation(Dependencies.supportAppCompat)
    implementation(Dependencies.supportCoreKtx)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.supportDesign)
    implementation(Dependencies.supportCore)
}
