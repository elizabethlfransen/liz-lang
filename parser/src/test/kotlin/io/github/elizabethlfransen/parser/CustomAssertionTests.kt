package io.github.elizabethlfransen.parser

import assertk.assertThat
import assertk.assertions.hasMessage
import assertk.assertions.isFailure
import assertk.assertions.isInstanceOf
import assertk.assertions.isSuccess
import io.github.elizabethlfransen.lizlang.parser.LizLangLexer
import io.github.elizabethlfransen.parser.util.hasTokensExactly
import org.junit.jupiter.api.Test

/**
 * Tests to make sure custom assertions work
 */
class CustomAssertionTests {
    @Test
    fun `given an incorrect number of tokens then assertion should fail with message`() {
        val result = assertThat {
            assertThat("test").hasTokensExactly {
                identifier("test")
                eof
            }
        }.isFailure()
        result.isInstanceOf(AssertionError::class)
        result.hasMessage("expected to have 3 tokens but had 2 tokens")
    }

    @Test
    fun `given a token with incorrect type then assertion should fail with message`() {
        val result = assertThat {
            assertThat("test").hasTokensExactly {
                eof
            }
        }.isFailure()
        result.isInstanceOf(AssertionError::class)
        result.hasMessage("expected tokens[0] to have type 'EOF' but was 'IDENTIFIER'")
    }

    @Test
    fun `given a token with incorrect text then assertion should fail with message`() {
        val result = assertThat {
            assertThat("test").hasTokensExactly {
                identifier("incorrect")
            }
        }.isFailure()
        result.isInstanceOf(AssertionError::class)
        result.hasMessage("expected tokens[0] to have text 'incorrect' but was 'test'")
    }

    @Test
    fun `given an expected token with no text and valid type then the assertion should succeed`() {
        assertThat {
            assertThat("test").hasTokensExactly {
                token(LizLangLexer.IDENTIFIER)
            }
        }.isSuccess()
    }

    @Test
    fun `given an expected token matching the actual token then the assertion should succeed`() {
        assertThat {
            assertThat("test").hasTokensExactly {
                identifier("test")
            }
        }.isSuccess()
    }

    @Test
    fun `given an expected token matching the actual token type and text when using the not dsl then the assertion should fail`() {
        val result = assertThat {
            assertThat("test").hasTokensExactly {
                not {
                    identifier("test")
                }
            }
        }.isFailure()
        result.isInstanceOf(AssertionError::class)
        result.hasMessage("expected tokens[0] to not be IDENTIFIER('test') but was IDENTIFIER('test')")
    }

    @Test
    fun `given an expected token matching the actual token type and null text when using the not dsl then the assertion should fail`() {
        val result = assertThat {
            assertThat("test").hasTokensExactly {
                not {
                    identifier
                }
            }
        }.isFailure()
        result.isInstanceOf(AssertionError::class)
        result.hasMessage("expected tokens[0] to not be IDENTIFIER but was IDENTIFIER")
    }

    @Test
    fun `given an expected token not matching the actual token type when using the not dsl then assertion should succeed`() {
        assertThat {
            assertThat("test").hasTokensExactly {
                not { eof }
            }
        }.isSuccess()
    }

    @Test
    fun `given an expected token matching the actual token type but not the same text when using the not dsl then assertion should succeed`() {
        assertThat {
            assertThat("test").hasTokensExactly {
                not {
                    identifier("incorrect")
                }
            }
        }.isSuccess()
    }
}