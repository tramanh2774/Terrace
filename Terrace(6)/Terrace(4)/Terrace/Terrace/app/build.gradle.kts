plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.terrace"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.terrace"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures(
        {
            viewBinding = true
        }
    )

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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(platform("com.google.firebase:firebase-bom:33.3.0"));
    implementation("com.google.firebase:firebase-analytics");
    implementation ("com.google.firebase:firebase-firestore:24.4.4");
    implementation ("com.github.bumptech.glide:glide:4.15.1");
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1");
    implementation ("com.squareup.picasso:picasso:2.71828")
}