plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("org.jetbrains.kotlin.kapt")
    alias(libs.plugins.safeArgs)
}

android {
    namespace = "ru.anura.emtesttask"
    compileSdk = 35

    defaultConfig {
        applicationId = "ru.anura.emtesttask"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.espresso.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    //recycler view
    implementation(libs.androidx.recyclerview)

    // retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    //coroutines
    implementation (libs.kotlinx.coroutines.android)
    implementation(libs.retrofit2.kotlin.coroutines.adapter)

    //mock
    implementation (libs.mockwebserver)

    //dagger
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    implementation(project(":data"))
    implementation(project(":feature-search"))
    implementation(project(":feature-tickets"))
    implementation(project(":common"))

}