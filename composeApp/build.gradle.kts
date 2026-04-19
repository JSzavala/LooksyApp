import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.9.2")
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(compose.materialIconsExtended)
                // Correct Multiplatform Navigation
            implementation(libs.navigation.compose)
                // Lifecycle (Multiplatform versions)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
                // Image Loading (Use Coil 3 for Multiplatform support)
                // Add this to libs.versions.toml: coil = "3.0.0-alpha06"
                // implementation("io.coil-kt.coil3:coil-compose:3.0.0-alpha06")
            /*implementation("androidx.compose.ui:ui:1.6.0")
            implementation("androidx.compose.material:material:1.6.0")
            implementation("androidx.compose.ui:ui-tooling-preview:1.6.0")
            implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
            implementation("androidx.navigation:navigation-compose:2.7.6")*/

            // Para cargar imágenes
            implementation("io.coil-kt:coil-compose:2.5.0")

            // Para layouts tipo grid (Staggered Grid)
            implementation("com.google.accompanist:accompanist-flowlayout:0.32.0")
        }
    }
}

android {
    namespace = "com.example.Looksy"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.coil.compose)
    debugImplementation(compose.uiTooling)
}
