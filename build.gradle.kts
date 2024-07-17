plugins {
    id("java")
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.21")
    }
}

allprojects {
    apply {
        plugin("java")
        plugin("maven-publish")
    }

    repositories {
        mavenLocal()
        mavenCentral()
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/releases/") }
        google()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://repo.clojars.org/") }
    }
}