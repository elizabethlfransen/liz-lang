package io.github.elizabethlfransen.parser

import assertk.assertThat
import io.github.elizabethlfransen.lizlang.parser.LizLangParser
import io.github.elizabethlfransen.parser.util.ast.*
import io.github.elizabethlfransen.parser.util.mapToDynamicTest
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class GrammarTests {
    @TestFactory
    fun `given an int literal when parsing a literal ast then an int literal should be parsed`(): Collection<DynamicTest> {
        return listOf(
            "123" to 123,
            "0x123" to 0x123,
            "123_456_789" to 123_456_789
        ).mapToDynamicTest({ (literal, _) -> literal}) { (literal, expected) ->
            assertThat(literal)
                .asAST(LizLangParser::literal)
                .isIntLiteral(expected)
        }
    }

    @TestFactory
    fun `given a float literal when parsing a literal ast then a float literal should be parsed`(): Collection<DynamicTest> {
        return listOf(
            "123f" to 123f,
            "123.4f" to 123.4f,
            "123.4_5f" to 123.4_5f,
            "123.4e5f" to 123.4e5f
        ).mapToDynamicTest({ (literal, _) -> literal}) { (literal, expected) ->
            assertThat(literal)
                .asAST(LizLangParser::literal)
                .isFloatLiteral(expected)
        }
    }

    @TestFactory
    fun `given a double literal when parsing a literal ast then a double literal should be parsed`(): Collection<DynamicTest> {
        return listOf(
            "123.0" to 123.0,
            "123.4" to 123.4,
            "123.4_5" to 123.4_5,
            "123.4e5" to 123.4e5
        ).mapToDynamicTest({ (literal, _) -> literal}) { (literal, expected) ->
            assertThat(literal)
                .asAST(LizLangParser::literal)
                .isDoubleLiteral(expected)
        }
    }

    @Test
    fun `given a true literal when parsing a literal ast then a true literal should be parsed`() {
        assertThat("true")
            .asAST(LizLangParser::literal)
            .isTrueLiteral()
    }

    @Test
    fun `given a false literal when parsing a literal ast then a false literal should be parsed`() {
        assertThat("false")
            .asAST(LizLangParser::literal)
            .isFalseLiteral()
    }

    @Test
    fun `given a string literal when parsing a literal ast then a string literal should be parsed`() {
        assertThat("\"test\"")
            .asAST(LizLangParser::literal)
            .isStringLiteral("test")
    }

    @Test
    fun `given a char literal when parsing a literal ast then a char literal should be parsed`() {
        assertThat("'a'")
            .asAST(LizLangParser::literal)
            .isCharLiteral('a')
    }

    @Test
    fun `given a literal expression when parsing then the expression should contain a single non-null literal`() {
        assertThat("123")
            .asAST(LizLangParser::expression)
            .isExpression {
                int(123)
            }
    }

    @Test
    fun `given a simple multiplication then the parser should be able to parse it`() {
        assertThat("123 * 12")
            .asAST(LizLangParser::expression)
            .isExpression {
                mult {
                    int(123)
                    int(12)
                }
            }
    }

    @Test
    fun `given a multiple multiplication then the parser should be able to parse it`() {
        assertThat("123 * 12 * 3 * 45")
            .asAST(LizLangParser::expression)
            .isExpression {
                mult {
                    mult {
                        mult {
                            int(123)
                            int(12)
                        }
                        int(3)
                    }
                    int(45)
                }
            }
    }

    @Test
    fun `parser should be able to parse an addition expression`() {
        assertThat("123 + 456 + 789")
            .asAST(LizLangParser::expression)
            .isExpression {
                add {
                    add {
                        int(123)
                        int(456)
                    }
                    int(789)
                }
            }
    }

    @Test
    fun `parser should priorities multiplication over addition`() {
        assertThat("123 + 456 * 789 + 123")
            .asAST(LizLangParser::expression)
            .isExpression {
                add {
                    add {
                        int(123)
                        mult {
                            int(456)
                            int(789)
                        }
                    }
                    int(123)
                }
            }
    }
}