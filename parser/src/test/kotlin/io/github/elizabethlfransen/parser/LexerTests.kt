package io.github.elizabethlfransen.parser

import assertk.assertThat
import assertk.assertions.isFailure
import io.github.elizabethlfransen.parser.util.hasTokensExactly
import io.github.elizabethlfransen.parser.util.tokens
import org.junit.jupiter.api.Test

class LexerTests {
    @Test
    fun `lexer should be able to identify an identifier`() {
        assertThat("test123\$_").hasTokensExactly {
            identifier("test123\$_")
        }
    }

    @Test
    fun `identifier should not allow numbers at the beginning`() {
        assertThat {
            "123test\$_".tokens()
        }.isFailure()
    }

    @Test
    fun `given an integer literal with only digits an integer token should be emitted`() {
        assertThat("123").hasTokensExactly {
            integer(123)
        }
    }

    @Test
    fun `given an integer literal with underscores an integer token should be emitted`() {
        assertThat("1_2_3").hasTokensExactly {
            integer("1_2_3")
        }
    }

    @Test
    fun `given an integer with an underscore at the beginning, an integer token should not be emitted`() {
        // should tokenize as an identifier not a number
        assertThat("_123").hasTokensExactly {
            not {
                integer("_123")
            }
        }
    }

    @Test
    fun `given an integer with an underscore at the end, lexing should fail`() {
        assertThat {
            assertThat("123_").hasTokensExactly {
                integer("123_")
            }
        }.isFailure()
    }
}