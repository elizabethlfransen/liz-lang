package io.github.elizabethlfransen.lizlang

import io.github.elizabethlfransen.lizlang.ast.LizLangASTGenerator
import io.github.elizabethlfransen.lizlang.internal.LizLangLexer
import io.github.elizabethlfransen.lizlang.internal.LizLangParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.nio.file.Path

object LizLangInterpreter {
    fun interpretFile(file: Path) {
        file
            .let(CharStreams::fromPath) // convert to stream
            .let(::LizLangLexer) // create lexer
            .let(::CommonTokenStream) // create token stream
            .let(::LizLangParser) // create parser
            .let(LizLangParser::lizLangFile) // parse lizLangFile
            .let(LizLangASTGenerator()::visitLizLangFile) // generate ast
            .let(LizLangASTFormatter()::visitLizLangFile) // format ast
            .let(::println) // print
    }
}

fun main(vararg args: String) {
    LizLangInterpreter.interpretFile(Path.of(args[0]))
}