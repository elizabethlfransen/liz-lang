package io.github.elizabethlfransen.parser

import assertk.assertThat
import io.github.elizabethlfransen.lizlang.parser.LizLangParser
import io.github.elizabethlfransen.parser.util.ast.asAST
import io.github.elizabethlfransen.parser.util.ast.verify
import io.github.elizabethlfransen.parser.util.mapToDynamicTest
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class GrammarTests {
    @TestFactory
    fun `given a int literal when parsing a literal ast then an int literal should be parsed`(): Collection<DynamicTest> {
        return listOf(
            "123" to 123,
            "0x123" to 0x123,
            "123_456_789" to 123_456_789
        ).mapToDynamicTest({ (literal, _) -> literal}) { (literal, expected) ->
            assertThat(literal)
                .asAST(LizLangParser::literal)
                .verify {
                    intLiteral(expected)
                }
        }
    }
}