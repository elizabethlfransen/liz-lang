plugins {
    antlr
}

dependencies {
    antlr("org.antlr:antlr4:4.11.1")
}

tasks.compileKotlin {
    dependsOn("generateGrammarSource")
}

tasks.compileTestKotlin {
    dependsOn("generateTestGrammarSource")
}

tasks.generateGrammarSource {
    arguments = arguments + listOf("-package", "io.github.elizabethlfransen.lizlang.parser")
    outputDirectory = project.buildDir.resolve("generated-src/antlr/main/io/github/elizabethlfransen/lizlang/parser")
}