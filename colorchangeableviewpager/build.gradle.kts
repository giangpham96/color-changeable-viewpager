plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(AndroidSettings.compileSdkVersion)

    defaultConfig {
        versionName = "0.0.1"
        versionCode = 1

        minSdkVersion(AndroidSettings.minSdkVersion)
        targetSdkVersion(AndroidSettings.targetSdkVersion)
    }
    compileOptions {
        sourceCompatibility = AndroidSettings.sourceCompatibility
        targetCompatibility = AndroidSettings.targetCompatibility
    }
}

dependencies {
    implementation(Dependencies.appCompat)
    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.ktx)
}
