plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "kbs.apps.mobiledevelopment.week11database"
    compileSdk = 35

    defaultConfig {
        applicationId = "kbs.apps.mobiledevelopment.week11database"
        minSdk = 27
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

//    implementation("androidx.room:room-ktx:2.4.3")
//    kapt("androidx.room:room-compiler:2.4.3")
//    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
//    implementation("androidx.activity:activity-ktx:1.6.0")
//    implementation("androidx.fragment:fragment-ktx:1.5.3")
//    annotationProcessor("android.arch.persistence.room:compiler:1.1.1")
//    implementation("androidx.room:room-runtime:2.4.3")


    // ViewModel
    implementation("androidx. lifecycle: lifecycle-viewmodel-ktx: \"2.2.0\"")
    // LiveData
    implementation("android, lifecycle: lifecycle-livedata-ktx: \"2.2.0\"")

}