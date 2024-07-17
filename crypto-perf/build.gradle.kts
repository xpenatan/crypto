import java.util.*

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm")
    kotlin("kapt")
}

dependencies {
    implementation(project(":crypto-core"))
    implementation("org.openjdk.jmh:jmh-core:1.37")
    kapt("org.openjdk.jmh:jmh-generator-annprocess:1.37")
    implementation("org.bouncycastle:bcpkix-jdk18on:1.78.1")
    implementation("org.apache.commons:commons-crypto:1.2.0")
    implementation("buddy:buddy:2.0.0")
    implementation("org.clojure:clojure:1.11.3")
    implementation("at.favre.lib:hkdf:1.0.2")
    implementation("org.conscrypt:conscrypt-openjdk-uber:2.5.2")
}

val resultFile = File(projectDir, "build/jmh/")
resultFile.mkdirs()

tasks.register<JavaExec>("jmh") {
    dependsOn("classes")
    mainClass.set("org.openjdk.jmh.Main")
    classpath = sourceSets["main"].runtimeClasspath
    args = mutableListOf(
        "-rf",  "text",
        "-rff",  File(resultFile, "result.txt").path,
//        "-e", "AesBuddy|AesCommons|AesConscrypt|AesJce",
        "-i", "10"
    )
}