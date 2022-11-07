package io.github.elizabethlfransen.parser.util

import io.github.elizabethlfransen.lizlang.parser.LizLangLexer
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.Token

fun String.tokens(allowErrors: Boolean = false): List<Token> {
    val errorListener = CollectingErrorListener()
    val lexer = this
        .let(CharStreams::fromString)
        .let(::LizLangLexer)

    lexer.addErrorListener(errorListener)

    val tokens = lexer.run {
        allTokens + emitEOF()
    }

    if(!allowErrors && errorListener.errors.isNotEmpty())
        assertk.fail("expected tokenization to not have any errors but received errors", emptyList<CollectingErrorListener.SyntaxErrorEntry>(), errorListener.errors)
    return tokens
}