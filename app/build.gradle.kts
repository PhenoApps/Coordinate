import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.aboutlibraries)
}

kotlin {
    jvmToolchain(21)
}

android {
    namespace = "org.wheatgenetics.coordinate"
    compileSdk = libs.versions.compileSdk.get().toInt()

    signingConfigs {
        create("playStoreConfig") {
            val keystorePropsFile = file("keystore.config")
            if (keystorePropsFile.exists()) {
                val keystoreProps = Properties().apply {
                    FileInputStream(keystorePropsFile).use { load(it) }
                }
                keyAlias = keystoreProps.getProperty("keyAlias")
                keyPassword = keystoreProps.getProperty("keyPassword")
                storePassword = keystoreProps.getProperty("storePassword")
                storeFile = file("coordinate.keystore.jks")
            }
        }
    }

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "org.wheatgenetics.coordinate"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        manifestPlaceholders["crashlyticsCollectionEnabled"] = "true"
    }

    buildTypes {
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            manifestPlaceholders["crashlyticsCollectionEnabled"] = "false"
        }

        release {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("playStoreConfig")
            isMinifyEnabled = false
            isDebuggable = false
            isJniDebuggable = false
            isPseudoLocalesEnabled = false
        }
    }

    packaging {
        resources {
            excludes += "META-INF/DEPENDENCIES"
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle)
    implementation(libs.preference)
    implementation(libs.core.ktx)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)

    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.navigation.dynamic)
    implementation(libs.navigation.runtime)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(libs.material)
    implementation(libs.changelog)
    implementation(libs.material.about)
    implementation(libs.aboutlibraries)
    implementation(libs.zxing.core)
    implementation(libs.tableview)
    implementation(libs.phenolib)
    implementation(libs.searchpreference)
    implementation(libs.taptargetview)
    implementation(libs.appintro)
    implementation(libs.easypermissions)
    implementation(libs.zxing.embedded) { isTransitive = false }

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso)
    androidTestImplementation(libs.test.core)
    androidTestImplementation(libs.navigation.testing)
    androidTestImplementation(libs.hilt.testing)
    kspAndroidTest(libs.hilt.compiler)

    coreLibraryDesugaring(libs.desugar)
}
