package io.github.elizabethlfransen.lizlang.ast.exp

import io.github.elizabethlfransen.lizlang.ast.ASTExpression
import io.github.elizabethlfransen.lizlang.ast.ASTIdentifier
import io.github.elizabethlfransen.lizlang.ast.ASTNode
import io.github.elizabethlfransen.lizlang.util.TextLocation

interface BinaryExpression<TLeft : ASTNode, TRight : ASTNode>: ASTExpression {
    val left: TLeft
    val right: TRight
}

data class MultiplicationExpression(
    override val left: ASTExpression,
    override val right: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : BinaryExpression<ASTExpression, ASTExpression>

data class AdditionExpression(
    override val left: ASTExpression,
    override val right: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : BinaryExpression<ASTExpression, ASTExpression>

data class SubtractionExpression(
    override val left: ASTExpression,
    override val right: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : BinaryExpression<ASTExpression, ASTExpression>

data class CastExpression(
    override val left: ASTExpression,
    override val right: ASTIdentifier,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
): BinaryExpression<ASTExpression, ASTIdentifier>

data class DivideExpression(
    override val left: ASTExpression,
    override val right: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
): BinaryExpression<ASTExpression, ASTExpression>


data class ModExpression(
    override val left: ASTExpression,
    override val right: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
): BinaryExpression<ASTExpression, ASTExpression>

data class ShiftLeftExpression(
    override val left: ASTExpression,
    override val right: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
): BinaryExpression<ASTExpression, ASTExpression>

data class ShiftRightExpression(
    override val left: ASTExpression,
    override val right: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
): BinaryExpression<ASTExpression, ASTExpression>

data class UnsignedShiftRightExpression(
    override val left: ASTExpression,
    override val right: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
): BinaryExpression<ASTExpression, ASTExpression>
data class LessThanExpression(
    override val left: ASTExpression,
    override val right: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
): BinaryExpression<ASTExpression, ASTExpression>
data class LessThanOrEqualToExpression(
    override val left: ASTExpression,
    override val right: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
): BinaryExpression<ASTExpression, ASTExpression>
data class GreaterThanExpression(
    override val left: ASTExpression,
    override val right: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
): BinaryExpression<ASTExpression, ASTExpression>
data class GreaterThanOrEqualToExpression(
    override val left: ASTExpression,
    override val right: ASTExpression,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
): BinaryExpression<ASTExpression, ASTExpression>