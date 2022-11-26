package io.github.elizabethlfransen.lizlang.ast

import io.github.elizabethlfransen.lizlang.util.TextLocation

sealed interface ASTExpression : ASTNode {
}

sealed interface InfixExpression<TLeft : ASTNode, TRight : ASTNode>: ASTExpression {
    val left: TLeft
    val right: TRight
}

sealed interface UnaryExpression<TChild : ASTNode> : ASTExpression {
    val child : TChild
}

data class LiteralExpression(
    override val child: ASTLiteral<*>,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : UnaryExpression<ASTLiteral<*>>

data class ParenthesisExpression(
    override val child: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : UnaryExpression<ASTExpression>

data class MultiplicationExpression(
    override val left: ASTExpression,
    override val right: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : InfixExpression<ASTExpression, ASTExpression>

data class AdditionExpression(
    override val left: ASTExpression,
    override val right: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : InfixExpression<ASTExpression, ASTExpression>

data class UnaryMinusExpression(
    override val child: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : UnaryExpression<ASTExpression>

data class UnaryPlusExpression(
    override val child: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : UnaryExpression<ASTExpression>

data class PostIncrementExpression(
    override val child: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : UnaryExpression<ASTExpression>

data class PostDecrementExpression(
    override val child: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : UnaryExpression<ASTExpression>

data class PreIncrementExpression(
    override val child: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : UnaryExpression<ASTExpression>

data class PreDecrementExpression(
    override val child: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : UnaryExpression<ASTExpression>

data class NotExpression(
    override val child: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : UnaryExpression<ASTExpression>

data class BitwiseComplementExpression(
    override val child: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : UnaryExpression<ASTExpression>

data class CastExpression(
    override val left: ASTExpression,
    override val right: ASTIdentifier,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
): InfixExpression<ASTExpression, ASTIdentifier>