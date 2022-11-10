package io.github.elizabethlfransen.parser.util

import assertk.Assert
import assertk.assertions.support.expected
import io.github.elizabethlfransen.lizlang.parser.LizLangLexer
import io.github.elizabethlfransen.parser.util.tokendsl.TokenDsl
import kotlin.test.fail

/**
 * Asserts that when a string is tokenized the tokens emit exactly what is described by [init].
 * If [emitEOF] is true EOF is automatically added to expected tokens.
 */
fun Assert<String>.hasTokensExactly(emitEOF: Boolean = true, init: TokenDsl.() -> Unit) = given { actual ->
    val actualTokens = actual.tokens()
    val expectedTokens = TokenDsl(emitEOF)
        .apply(init)
        .tokenMatchers

    if(actualTokens.size != expectedTokens.size)
        expected("to have ${expectedTokens.size} tokens but had ${actualTokens.size} tokens")

    actualTokens.forEachIndexed { index, actualToken ->
        expectedTokens[index].assertMatches(index,actualToken)
    }
}