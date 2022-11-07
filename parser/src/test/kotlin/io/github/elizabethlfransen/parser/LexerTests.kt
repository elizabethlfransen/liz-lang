package io.github.elizabethlfransen.parser

import assertk.assertThat
import assertk.assertions.isFailure
import io.github.elizabethlfransen.parser.util.hasTokensExactly
import io.github.elizabethlfransen.parser.util.tokens
import org.junit.jupiter.api.Test

class LexerTests {
    @Test
    fun `lexer should be able to identify an identifier`() {
        assertThat("test\$_").hasTokensExactly {
            identifier("test\$_")
        }
    }

    @Test
    fun `identifier should not allow numbers at the beginning`() {
        assertThat {
            "123test\$_".tokens()
        }.isFailure()
    }
}