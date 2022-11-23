package io.github.elizabethlfransen.lizlang

import io.github.elizabethlfransen.lizlang.util.TextLocation
import org.antlr.v4.runtime.ParserRuleContext

sealed interface ASTNode {
    val text: String
    val start: TextLocation
    val stop: TextLocation
}

sealed interface ASTLiteral<T> : ASTNode {
    val value: T
}

sealed interface ASTExpression : ASTNode {
}

sealed interface InfixExpression: ASTExpression {
    val left: ASTExpression
    val right: ASTExpression
}

data class LiteralExpression(
    val child: ASTLiteral<*>,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : ASTExpression

data class MultiplicationExpression(
    override val left: ASTExpression,
    override val right: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : InfixExpression

data class IntLiteral(
    override val value: Int,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : ASTLiteral<Int>

data class FloatLiteral(
    override val value: Float,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : ASTLiteral<Float>

data class DoubleLiteral(
    override val value: Double,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : ASTLiteral<Double>

data class TrueLiteral(
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : ASTLiteral<Boolean> {
    override val value: Boolean = true
}

data class FalseLiteral(
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : ASTLiteral<Boolean> {
    override val value: Boolean = false
}

// Eventually strings will support interpolation and thus the text will not be known at parse time. We will want to have
// both a string literal known at parse time and a string expression literal that will be compiled at runtime
data class StringLiteral(
    override val value: String,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : ASTLiteral<String>

data class CharacterLiteral(
    override val value: Char,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : ASTLiteral<Char>

@Suppress("SpellCheckingInspection")
fun <TAST,TValue> buildSingleChildNodeFromExpression(
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