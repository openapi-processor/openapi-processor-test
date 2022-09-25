plugins {
    groovy
    id("java-library")
    id("maven-publish")
    id("signing")
    alias(libs.plugins.nexus)
    alias(libs.plugins.versions)
}

val projectGroupId: String by project
val projectVersion: String by project

group = projectGroupId
version = projectVersion

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }

    withJavadocJar ()
    withSourcesJar ()
}

repositories {
    mavenCentral()
    maven {
        setUrl("https://oss.sonatype.org/content/repositories/snapshots")
        mavenContent {
            snapshotsOnly()
        }
    }
}

dependencies {
    compileOnly(libs.openapi.processor.api)

    implementation(platform(libs.jackson.bom))
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")

    implementation(platform(libs.groovy.bom))
    implementation("org.codehaus.groovy:groovy")
    implementation("org.codehaus.groovy:groovy-nio")

    implementation(libs.diff.utils)
    implementation(libs.jimfs) {
        exclude("com.google.guava")
    }
}

apply(from = "${rootProject.rootDir}/gradle/publishing.gradle")
