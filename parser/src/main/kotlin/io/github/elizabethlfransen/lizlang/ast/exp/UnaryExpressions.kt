package io.github.elizabethlfransen.lizlang.ast.exp

import io.github.elizabethlfransen.lizlang.ast.ASTExpression
import io.github.elizabethlfransen.lizlang.ast.ASTLiteral
import io.github.elizabethlfransen.lizlang.ast.ASTNode
import io.github.elizabethlfransen.lizlang.util.TextLocation

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