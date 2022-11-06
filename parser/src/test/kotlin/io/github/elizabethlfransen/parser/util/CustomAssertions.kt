package io.github.elizabethlfransen.parser.util

import assertk.Assert
import assertk.assertions.support.expected
import io.github.elizabethlfransen.lizlang.parser.LizLangLexer
import kotlin.test.fail

fun Assert<String>.hasTokensExactly(init: TokenDsl.() -> Unit) = given { actual ->
    val actualTokens = actual.tokens
    val expectedTokens = TokenDsl()
        .apply(init)
        .tokenMatchers

    if(actualTokens.size != expectedTokens.size)
        expected("to have ${expectedTokens.size} tokens but had ${actualTokens.size} tokens")

    actualTokens.forEachIndexed { index, actualToken ->
        val expectedToken = expectedTokens[index]
        if(actualToken.type != expectedToken.type) {
            val expectedTokenDisplayName =LizLangLexer.VOCABULARY.getDisplayName(expectedToken.type)
            val actualTokenDisplayName = LizLangLexer.VOCABULARY.getDisplayName(actualToken.type)
            fail("expected tokens[$index] to have type '$expectedTokenDisplayName' but was '$actualTokenDisplayName'")
        }

        expectedToken.text
            ?.takeIf { actualToken.text != expectedToken.text }
            ?.let { expectedText ->
                fail("expected tokens[$index] to have text '$expectedText' but was '${actualToken.text}'")
        }
    }
}