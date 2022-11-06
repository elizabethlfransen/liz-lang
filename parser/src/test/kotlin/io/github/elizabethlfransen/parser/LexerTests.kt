package io.github.elizabethlfransen.parser

import assertk.assertThat
import io.github.elizabethlfransen.parser.util.hasTokensExactly
import org.junit.jupiter.api.Test

class LexerTests {
    @Test
    fun `lexer should be able to identify an identifier`() {
        assertThat("test\$_").hasTokensExactly {
            identifier("test\$_")
        }
    }
}