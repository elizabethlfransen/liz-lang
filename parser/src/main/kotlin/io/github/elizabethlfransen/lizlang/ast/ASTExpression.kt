package io.github.elizabethlfransen.lizlang.ast

import io.github.elizabethlfransen.lizlang.util.TextLocation

sealed interface ASTExpression : ASTNode {
}

sealed interface InfixExpression: ASTExpression {
    val left: ASTExpression
    val right: ASTExpression
}

sealed interface UnaryExpression : ASTExpression {
    val child : ASTExpression
}

data class LiteralExpression(
    val child: ASTLiteral<*>,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : ASTExpression

data class ParenthesisExpression(
    override val child: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : UnaryExpression

data class MultiplicationExpression(
    override val left: ASTExpression,
    override val right: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : InfixExpression

data class AdditionExpression(
    override val left: ASTExpression,
    override val right: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : InfixExpression

data class UnaryMinusExpression(
    override val child: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : UnaryExpression

data class UnaryPlusExpression(
    override val child: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : UnaryExpression