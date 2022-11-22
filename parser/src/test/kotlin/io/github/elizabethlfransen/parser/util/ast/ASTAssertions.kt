package io.github.elizabethlfransen.parser.util.ast

import assertk.Assert
import assertk.assertions.isNotNull
import assertk.assertions.support.appendName
import io.github.elizabethlfransen.lizlang.AST
import io.github.elizabethlfransen.lizlang.LizLangASTParser
import io.github.elizabethlfransen.lizlang.parser.LizLangLexer
import io.github.elizabethlfransen.lizlang.parser.LizLangParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.ParserRuleContext

fun Assert<String>.asAST(parseFunction: (LizLangParser) -> ParserRuleContext) : Assert<AST> {
    val result = transform(appendName("AST")) { actual ->
        val inputStream = CharStreams.fromString(actual)
        val tokens = CommonTokenStream(LizLangLexer(inputStream))
        val parser = LizLangParser(tokens)
        val astParser = LizLangASTParser()
        parseFunction(parser).accept(astParser)
    }
    result.isNotNull()
    return result
}

fun Assert<AST>.verify(verify: ASTVerifier.() -> Unit) {
    ASTVerifier(this).verify()
}
