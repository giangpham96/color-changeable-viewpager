plugins {
    id("com.android.application")
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
    buildTypes {
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-Debug"
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    packagingOptions {
        pickFirst("META-INF/services/javax.annotation.processing.Processor")
        exclude("META-INF/main.kotlin_module")
    }

    androidExtensions {
        isExperimental = true
    }
    testOptions {
        animationsDisabled = true
        unitTests(delegateClosureOf < Any ? > {
            unitTests.isReturnDefaultValues = true
            unitTests.isIncludeAndroidResources = true
        })
    }

    lintOptions {
        isWarningsAsErrors = true
        isCheckReleaseBuilds = false
        disable("ObsoleteLintCustomCheck")
    }
}

dependencies {
    implementation(Dependencies.appCompat)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.ktx)
    implementation(Dependencies.material)
    implementation(project(":colorchangeableviewpager"))
}
