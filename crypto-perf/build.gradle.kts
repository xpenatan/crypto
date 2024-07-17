repositories.mavenCentral()

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm")
    kotlin("kapt")
}

dependencies {
    implementation(project(":crypto-core"))
    implementation("org.openjdk.jmh:jmh-core:1.18")
    kapt("org.openjdk.jmh:jmh-generator-annprocess:1.18")
    implementation("org.bouncycastle:bcpkix-jdk15on:1.61")
    implementation("org.apache.commons:commons-crypto:1.0.0")
    implementation("buddy:buddy:2.0.0")
    implementation("org.clojure:clojure:1.10.1-RC1")
    implementation("at.favre.lib:hkdf:1.0.2")
    implementation("org.conscrypt:conscrypt-openjdk-uber:2.1.0")
}

tasks.register<JavaExec>("jmh") {
    dependsOn("classes")
    mainClass.set("org.openjdk.jmh.Main")
    classpath = sourceSets["main"].runtimeClasspath
}