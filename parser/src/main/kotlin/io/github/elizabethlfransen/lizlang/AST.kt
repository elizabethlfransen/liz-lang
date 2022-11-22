package io.github.elizabethlfransen.lizlang

import io.github.elizabethlfransen.lizlang.util.TextLocation
import org.antlr.v4.runtime.ParserRuleContext

sealed interface AST {
    val text: String
    val start: TextLocation
    val stop: TextLocation
}

data class IntLiteral(
    val value: Int,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : AST



fun <T> buildASTFromContext(
    context: ParserRuleContext,
    builder: (text: String, start: TextLocation, stop: TextLocation) -> T
) = builder(
    context.text,
    TextLocation.fromToken(context.start),
    TextLocation.fromToken(context.stop)
)