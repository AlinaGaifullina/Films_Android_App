@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "ru.itis.filmsandroidapp.feature.auth.impl"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeKotlinCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":feature:auth:api"))
    implementation(project(":feature:home:api"))

    implementation(project(":core:designsystem"))
    implementation(project(":core:widget"))
    implementation(project(":core:db"))
    implementation(project(":core:navigation"))
    implementation(project(":core:designsystem"))

    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.dagger.hilt.navigation.compose)

    implementation(libs.room)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.room.ktx)
    kapt(libs.room.kapt)

    // viewModel
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // navigation
    implementation(libs.androidx.navigation)
    implementation(libs.accompanist.navigation.animation)

    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material3)

    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}