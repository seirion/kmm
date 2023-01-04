plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.dokka") version "0.9.17"
    id("maven-publish")
}

group = "com.seirion.kmm"
version = "0.0.2"

val GITHUB_USER: String by project
val GITHUB_TOKEN: String by project

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "0.0.2"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "shared"
        }
    }
    
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

publishing {
    repositories {
        maven {
            setUrl("https://maven.pkg.github.com/seirion/kmm")
            credentials {
                username = GITHUB_USER
                password = GITHUB_TOKEN
            }
        }
    }
    publications {
        create<MavenPublication>("gpr") {
            run {
                groupId = "com.seirion.kmm"
                artifactId = "shared"
                version = "0.0.2"
                artifact("$buildDir/outputs/aar/shared-release.aar")
            }
        }
    }
}

android {
    namespace = "com.neo.kmm"
    compileSdk = 32
    defaultConfig {
        minSdk = 24
        targetSdk = 32
    }
}
