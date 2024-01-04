@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "ru.itis.filmsandroidapp.core.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)

    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.koin)

    api(libs.retrofit.core)
    api(libs.gson)
    api(libs.retrofit.converter.gson)

    implementation(libs.kotlinx.coroutines.core)
}