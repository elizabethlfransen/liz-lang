import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
}

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin="kotlin")
    group = "io.github.elizabethlfransen.liz-lang"
    version = "1.0-SNAPSHOT"


    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation(kotlin("test"))
        testImplementation(platform("org.mockito:mockito-bom:4.8.1"))
        testImplementation("org.mockito:mockito-core")
        testImplementation("org.mockito:mockito-junit-jupiter")
        testImplementation("org.mockito:mockito-junit-jupiter")
        testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
        testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.25")
    }

    tasks.test {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}