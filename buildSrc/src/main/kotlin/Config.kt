object Versions {
    const val KOTLIN_GRADLE_PLUGIN = "1.3.71"

    const val ANDROID_GRADLE_PLUGIN = "3.5.3"

    const val COMPILE_SDK_VERSION = 29

    const val BUILD_TOOL_VERSION = "29.0.2"

    const val MIN_SDK_VERSION = 21

    const val TARGET_SDK_VERSION = 29

    const val APP_COMPAT = "1.1.0"

    const val CORE_KTX = "1.1.0"

    const val KTX = "2.3.0-alpha04"

    const val CONSTRAINT_LAYOUT = "2.0.0-beta1"

    const val MATERIAL = "1.2.0-alpha02"

    const val KTLINT = "0.36.0"

    const val DETEKT = "1.9.1"

    const val KOIN = "2.1.5"

    const val RXJAVA = "3.0.0"

    const val RXJAVA3_EXTENSION = "3.0.0"

    const val RETROFIT = "2.7.1"

    const val RETROFIT_RXJAVA_ADAPTER = "3.0.0-RC8"

    const val OKHTTP = "4.7.2"

    const val GLIDE = "4.8.0"

    const val COROUTINES = "1.3.2"

    const val TIMBER = "4.7.1"

    const val LEAKCANARY = "2.0"

    const val PUSHER = "2.0.2"

    const val LOTTIE = "3.3.1"

    const val KTEXT = "1.0.0"

    const val CONSCRYPT_ANDROID = "2.4.0"
}

object ClassPaths {
    const val ANDROID_GRADLE_PLUGIN = "com.android.tools.build:gradle:${Versions.ANDROID_GRADLE_PLUGIN}"
    const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN_GRADLE_PLUGIN}"
}

object Plugins {
    const val androidApp = "com.android.application"
    const val kotlinAndroid = "android"
    const val kotlinExt = "android.extensions"
    const val kotlinApt = "kapt"
}

object Dependencies {
    const val SUPPORT_APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"

    const val SUPPORT_DESIGN = "com.google.android.material:material:${Versions.MATERIAL}"

    const val SUPPORT_CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"

    const val SUPPORT_CORE = "androidx.core:core:${Versions.CORE_KTX}"

    const val VIEW_MODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.KTX}"

    const val LIVE_DATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.KTX}"

    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.KOTLIN_GRADLE_PLUGIN}"

    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"

    const val KTLINT = "com.pinterest:ktlint:${Versions.KTLINT}"

    const val KOIN_VIEW_MODEL = "org.koin:koin-androidx-viewmodel:${Versions.KOIN}"

    const val KOIN_EXT = "org.koin:koin-androidx-ext:${Versions.KOIN}"

    const val RXJAVA = "io.reactivex.rxjava3:rxjava:${Versions.RXJAVA}"

    const val RXANDROID = "io.reactivex.rxjava3:rxandroid:${Versions.RXJAVA}"

    const val RXJAVA3_EXTENSION = "com.github.akarnokd:rxjava3-retrofit-adapter:${Versions.RXJAVA3_EXTENSION}"

    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"

    const val RETROFIT_CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"

    const val RETROFIT_ADAPTER_RXJAVA = "com.github.akarnokd:rxjava3-retrofit-adapter:${Versions.RETROFIT_RXJAVA_ADAPTER}"

    const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"

    const val OKHTTP_LOGGING = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"

    const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE}"

    const val GLIDE_COMPILE = "com.github.bumptech.glide:compiler:${Versions.GLIDE}"

    const val COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}"

    const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"

    const val TIMBER = "com.jakewharton.timber:timber:${Versions.TIMBER}"

    const val LEAK_CANARY = "com.squareup.leakcanary:leakcanary-android:${Versions.LEAKCANARY}"

    const val LOTTIE = "com.airbnb.android:lottie:${Versions.LOTTIE}"

    const val CONSCRYPT_ANDROID = "org.conscrypt:conscrypt-android:${Versions.CONSCRYPT_ANDROID}"
}