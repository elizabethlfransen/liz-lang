package io.github.elizabethlfransen.lizlang.ast

import io.github.elizabethlfransen.lizlang.util.TextLocation
import org.antlr.v4.runtime.ParserRuleContext

sealed interface ASTNode {
    val text: String
    val start: TextLocation
    val stop: TextLocation
}

data class ASTIdentifier(
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation,
) : ASTNode


fun <TAST, TValue> buildASTFromContext(
    context: ParserRuleContext,
    value: TValue,
    builder: (value: TValue, text: String, start: TextLocation, stop: TextLocation) -> TAST
) = buildASTFromContext(context) { text, start, stop ->
    builder(value, text, start, stop)
}

fun <TAST, TLeft, TRight> buildASTFromContext(
    context: ParserRuleContext,
    left: TLeft,
    right: TRight,
    builder: (left: TLeft, right: TRight, text: String, start: TextLocation, stop: TextLocation) -> TAST
) = buildASTFromContext(context) { text, start, stop ->
    builder(left, right, text, start, stop)
}

fun <T> buildASTFromContext(
    context: ParserRuleContext,
    builder: (text: String, start: TextLocation, stop: TextLocation) -> T
) = builder(
    context.text,
    TextLocation.fromToken(context.start),
    TextLocation.fromToken(context.stop)
)