plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.travelgo.tripzyy"
    compileSdk = 35
buildFeatures {
    viewBinding= true

}
    defaultConfig {
        applicationId = "com.travelgo.tripzyy"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }


}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.support.annotations)
    implementation(libs.annotation)
    implementation(libs.legacy.support.v4)
    implementation(libs.support.v4)
    implementation(libs.support.v13)
    implementation(libs.preference)
    // implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.google.firebase:firebase-auth:23.2.0");
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation(platform("com.google.firebase:firebase-bom:33.10.0"))
    implementation ("com.loopj.android:android-async-http:1.4.11"); //client-server communication
    implementation ("com.google.android.gms:play-services-auth:21.3.0")

}