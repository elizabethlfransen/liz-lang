package io.github.elizabethlfransen.parser

import assertk.assertThat
import assertk.assertions.*
import io.github.elizabethlfransen.lizlang.parser.LizLangLexer
import io.github.elizabethlfransen.parser.util.verifyTokens
import io.github.elizabethlfransen.parser.util.expectToken
import org.junit.jupiter.api.Test

class TokenVerifyTests {

    @Test
    fun `given a token that matches type asserting that the token types match should succeed`() {
        assertThat {
            verifyTokens("test")
                .expectToken(LizLangLexer.IDENTIFIER)
        }.isSuccess()
    }

    @Test
    fun `given a token that does not match an assertion then a pretty failure message should be emitted`() {
        val result = assertThat {
            verifyTokens("test")
                .expectToken(LizLangLexer.INTEGER)
        }.isFailure()
        result.hasMessage("expected [nextToken.type]:<INTEGER> but was:<IDENTIFIER> (\"test\")")
    }

    @Test
    fun `given a token that is not in vocabulary then a pretty failure message should be emitted`() {

        val result = assertThat {
            verifyTokens("test")
                .expectToken(-100)
        }.isFailure()
        result.hasMessage("expected [nextToken.type]:<-100> but was:<IDENTIFIER> (\"test\")")
    }

    @Test
    fun `given a token that matches text then the assertion should succeed`() {
        assertThat {
            verifyTokens("test")
                .expectToken("test")
        }.isSuccess()
    }

    @Test
    fun `given a token that does not match text then the assertion should fail with message`() {
        val result = assertThat {
            verifyTokens("test")
                .expectToken("bob")
        }.isFailure()
        result.hasMessage("expected [nextToken.text]:<\"[bob]\"> but was:<\"[test]\"> (\"test\")")
    }

    @Test
    fun `given an expected token type of null when asserting a token matching text then the assertion should succeed`() {
        assertThat {
            verifyTokens("test")
                .expectToken(null, "test")
        }.isSuccess()
    }

    @Test
    fun `given an expected token text of null when asserting a token matching type then the assertion should succeed`() {
        assertThat {
            verifyTokens("test")
                .expectToken(LizLangLexer.IDENTIFIER, null)
        }
    }
}