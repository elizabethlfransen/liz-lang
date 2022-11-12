package io.github.elizabethlfransen.parser.util

import assertk.Assert
import assertk.assertions.support.appendName
import io.github.elizabethlfransen.lizlang.parser.LizLangLexer
import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.TokenSource

fun Assert<TokenSource>.expectToken(assertion: (token: Assert<Token>) -> Unit) = apply {
    transform(appendName("nextToken", " "), TokenSource::nextToken)
        .let(assertion)
}

private fun <T> Assert<TokenSource>.expectTokenGiven(given: T, consumer: Assert<Token>.(actual: T) -> Unit) =
    expectToken { assertion ->
        assertion.consumer(given)
    }

fun Assert<TokenSource>.expectToken(tokenType: Int) = expectTokenGiven(tokenType, Assert<Token>::expectType)

fun Assert<TokenSource>.expectToken(tokenText: String) = expectTokenGiven(tokenText, Assert<Token>::expectText)

fun Assert<TokenSource>.expectToken(tokenType: Int?, tokenText: String?) = expectToken { tokenAssert ->
    tokenType?.let(tokenAssert::expectType)
    tokenText?.let(tokenAssert::expectText)
}


fun Assert<TokenSource>.expectIdentifier(text: String? = null) = expectToken(LizLangLexer.IDENTIFIER, text)
fun Assert<TokenSource>.expectIntegerLiteral(text: String? = null) = expectToken(LizLangLexer.INTEGER, text)
fun Assert<TokenSource>.expectDoubleLiteral(text: String? = null) = expectToken(LizLangLexer.DOUBLE, text)
fun Assert<TokenSource>.expectFloatLiteral(text: String? = null) = expectToken(LizLangLexer.FLOAT, text)

fun Assert<TokenSource>.expectTrueLiteral(text: String? = null) = expectToken(LizLangLexer.TRUE, text)
fun Assert<TokenSource>.expectFalseLiteral(text: String? = null) = expectToken(LizLangLexer.FALSE, text)

fun Assert<TokenSource>.expectEOF() = expectToken(LizLangLexer.EOF)