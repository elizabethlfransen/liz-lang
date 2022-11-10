package io.github.elizabethlfransen.parser.util

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import assertk.assertions.support.expected
import io.github.elizabethlfransen.lizlang.parser.LizLangLexer
import org.antlr.v4.runtime.Token


private fun Assert<Int>.tokenTypeEquals(expectedType: Int) = given {actual ->
    if(expectedType == actual) return
    val expectedDisplayName = LizLangLexer.VOCABULARY.getDisplayName(expectedType)
    val actualDisplayName = LizLangLexer.VOCABULARY.getDisplayName(actual)
    expected(":<$expectedDisplayName> but was:<$actualDisplayName>")
}


fun Assert<Token>.extractType() = this.prop("type", Token::getType)
fun Assert<Token>.extractText() = this.prop("text", Token::getText)

fun Assert<Token>.expectType(expectedType: Int) = extractType()
    .tokenTypeEquals(expectedType)

fun Assert<Token>.expectText(expectedText: String) = extractText()
    .isEqualTo(expectedText)