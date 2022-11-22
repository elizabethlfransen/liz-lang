package io.github.elizabethlfransen.lizlang

import io.github.elizabethlfransen.lizlang.util.TextLocation
import org.antlr.v4.runtime.ParserRuleContext

sealed interface ASTNode {
    val text: String
    val start: TextLocation
    val stop: TextLocation
}

sealed interface Literal<T> {
    val value: T
}

data class IntLiteral(
    override val value: Int,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : ASTNode, Literal<Int>

data class FloatLiteral(
    override val value: Float,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : ASTNode, Literal<Float>

data class DoubleLiteral(
    override val value: Double,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : ASTNode, Literal<Double>

data class TrueLiteral(
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : ASTNode, Literal<Boolean> {
    override val value: Boolean = true
}

data class FalseLiteral(
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : ASTNode, Literal<Boolean> {
    override val value: Boolean = false
}

@Suppress("SpellCheckingInspection")
fun <TAST,TValue> buildLiteralFromContext(
    context: ParserRuleContext,
    value: TValue,
    builder: (value: TValue, text: String, start: TextLocation, stop: TextLocation) -> TAST
) = buildASTFromContext(context) { text, start, stop ->
    builder(value, text, start, stop)
}

fun <T> buildASTFromContext(
    context: ParserRuleContext,
    builder: (text: String, start: TextLocation, stop: TextLocation) -> T
) = builder(
    context.text,
    TextLocation.fromToken(context.start),
    TextLocation.fromToken(context.stop)
)