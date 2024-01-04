plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "ru.itis.filmsandroidapp"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "ru.itis.filmsandroidapp"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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

    implementation(libs.core.ktx)
    implementation(libs.dagger.hilt)
    implementation(project(":feature:auth:api"))
    implementation(project(":feature:auth:impl"))

    implementation(project(":feature:home:api"))
    implementation(project(":feature:home:impl"))

    implementation(project(":feature:profile:api"))
    implementation(project(":feature:profile:impl"))

    implementation(project(":feature:film_details:api"))
    implementation(project(":feature:film_details:impl"))

    implementation(project(":core:designsystem"))
    implementation(project(":core:navigation"))


    // navigation
    implementation(libs.androidx.navigation)

    kapt(libs.dagger.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
}