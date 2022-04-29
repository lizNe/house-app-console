import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
//    Plugin for Dokka - KDOC generating tool
    id("org.jetbrains.dokka") version "1.6.10"
    jacoco

    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    application
}

group = "me.lizzz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

//    Implementation means dependency available in all source sets, including test source sets
//    testImplementation means dependency only available in test source set

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.github.microutils:kotlin-logging:2.1.15")
    implementation("org.slf4j:slf4j-simple:1.7.32")
    implementation("com.thoughtworks.xstream:xstream:1.4.18")
    implementation("org.codehaus.jettison:jettison:1.4.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.2.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.13.2")
    implementation("org.yaml:snakeyaml:1.30")

    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.6.10")
}

tasks.test {
    useJUnitPlatform()
//    report is always generated after tests run
    finalizedBy(tasks.jacocoTestReport)
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}
