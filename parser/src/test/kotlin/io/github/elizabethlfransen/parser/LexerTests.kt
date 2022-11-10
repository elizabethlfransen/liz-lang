package io.github.elizabethlfransen.parser

import io.github.elizabethlfransen.parser.util.expectEOF
import io.github.elizabethlfransen.parser.util.expectIdentifier
import io.github.elizabethlfransen.parser.util.expectIntegerLiteral
import io.github.elizabethlfransen.parser.util.verifyTokens
import org.junit.jupiter.api.Test

class LexerTests {
    @Test
    fun `lexer should be able to identify an identifier`() {
        verifyTokens("test123\$_")
            .expectIdentifier("test123\$_")
            .expectEOF()
    }

    @Test
    fun `given an integer literal with only digits an integer token should be emitted`() {
       verifyTokens("123")
           .expectIntegerLiteral("123")
           .expectEOF()
    }

    @Test
    fun `given an integer literal with underscores an integer token should be emitted`() {
        verifyTokens("1_2_3")
            .expectIntegerLiteral("1_2_3")
            .expectEOF()
    }
}