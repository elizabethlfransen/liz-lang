package io.github.elizabethlfransen.lizlang.ast

import io.github.elizabethlfransen.lizlang.util.TextLocation

sealed interface ASTLiteral<T> : ASTNode {
    val value: T
}

data class CharacterLiteral(
    override val value: Char,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : ASTLiteral<Char>

data class DoubleLiteral(
    override val value: Double,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : ASTLiteral<Double>

data class FalseLiteral(
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : ASTLiteral<Boolean> {
    override val value: Boolean = false
}

data class FloatLiteral(
    override val value: Float,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : ASTLiteral<Float>

data class IntLiteral(
    override val value: Int,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : ASTLiteral<Int>

// Eventually strings will support interpolation and thus the text will not be known at parse time. We will want to have
// both a string literal known at parse time and a string expression literal that will be compiled at runtime
data class StringLiteral(
    override val value: String,
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : ASTLiteral<String>

data class TrueLiteral(
    override val text: String,
    override val start: TextLocation,
    override val stop: TextLocation
) : ASTLiteral<Boolean> {
    override val value: Boolean = true
}