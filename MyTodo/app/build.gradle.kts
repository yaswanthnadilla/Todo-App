plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.mytodo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mytodo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        var versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        dataBinding=true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    val navigationversion = "2.7.7"
    val roomversion = "2.6.1"
    val lifecycleversion = "2.7.0"


    // Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationversion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationversion")

// Room components
    implementation("androidx.room:room-runtime:$roomversion")
    annotationProcessor("androidx.room:room-compiler:$roomversion")
    implementation("androidx.room:room-ktx:$roomversion")
    kapt("androidx.room:room-compiler:$roomversion")
    testImplementation("androidx.room:room-testing:$roomversion")

// Lifecycle components
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleversion" )
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleversion" )
    kapt("androidx.lifecycle:lifecycle-compiler:$lifecycleversion")

// Kotlin components
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.22")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

}