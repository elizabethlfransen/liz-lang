import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.21"
    antlr
}

group = "io.github.elizabethlfransen.lizlang"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    antlr("org.antlr:antlr4:4.13.0")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

tasks.generateGrammarSource {
    outputDirectory = outputDirectory.resolve("io/github/elizabethlfransen/lizlang/internal")
    arguments = arguments + listOf("-visitor","-package","io.github.elizabethlfransen.lizlang.internal")
}

tasks.withType<KotlinCompile> {
    dependsOn(tasks.generateGrammarSource)
}