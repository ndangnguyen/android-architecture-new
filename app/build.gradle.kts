plugins {
    id(Plugins.androidApp)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinExt)
    kotlin(Plugins.kotlinApt)
}

android {
    compileSdkVersion(Versions.COMPILE_SDK_VERSION)
    buildToolsVersion(Versions.BUILD_TOOL_VERSION)

    defaultConfig {
        applicationId = "com.ndn.androidarchitecture"
        minSdkVersion(Versions.MIN_SDK_VERSION)
        targetSdkVersion(Versions.TARGET_SDK_VERSION)
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

    // Kotlin
    implementation(Dependencies.KOTLIN_STDLIB)

    // App compat & design
    implementation(Dependencies.SUPPORT_APP_COMPAT)
    implementation(Dependencies.SUPPORT_CORE)
    implementation(Dependencies.SUPPORT_CORE_KTX)
    implementation(Dependencies.CONSTRAINT_LAYOUT)
    implementation(Dependencies.SUPPORT_DESIGN)

    // App compat & design
    implementation(Dependencies.SUPPORT_APP_COMPAT)
    implementation(Dependencies.SUPPORT_CORE)
    implementation(Dependencies.SUPPORT_DESIGN)
    implementation(Dependencies.CONSTRAINT_LAYOUT)

    // Rxjava
    implementation(Dependencies.RXJAVA)
    implementation(Dependencies.RXANDROID)
    implementation(Dependencies.RXJAVA3_EXTENSION)

    // Coroutines
    implementation(Dependencies.COROUTINES_CORE)
    implementation(Dependencies.COROUTINES_ANDROID)

    // Retrofit
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_CONVERTER_GSON)

    // Okhttp
    implementation(Dependencies.OKHTTP)
    implementation(Dependencies.OKHTTP_LOGGING)

    // Koin
    implementation(Dependencies.KOIN_VIEW_MODEL)
    implementation(Dependencies.KOIN_EXT)

    // Glide
    implementation(Dependencies.GLIDE)
    annotationProcessor(Dependencies.GLIDE_COMPILE)
    kapt(Dependencies.GLIDE_COMPILE)

    // Leak canary
    debugImplementation(Dependencies.LEAK_CANARY)

    // Timber
    implementation(Dependencies.TIMBER)

    // KTX
    implementation(Dependencies.SUPPORT_CORE_KTX)
    implementation(Dependencies.VIEW_MODEL_KTX)
    implementation(Dependencies.LIVE_DATA_KTX)

    // Lottie
    implementation(Dependencies.LOTTIE)
}
