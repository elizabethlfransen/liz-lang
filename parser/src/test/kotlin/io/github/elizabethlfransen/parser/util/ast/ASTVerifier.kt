package io.github.elizabethlfransen.parser.util.ast

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.prop
import io.github.elizabethlfransen.lizlang.*

class ASTVerifier(private val actual: Assert<ASTNode>) {

    fun intLiteral(expectedValue: Int) {
        actual.isInstanceOf(IntLiteral::class)
            .prop(IntLiteral::value)
            .isEqualTo(expectedValue)
    }

    fun floatLiteral(expectedValue: Float) {
        actual.isInstanceOf(FloatLiteral::class)
            .prop(FloatLiteral::value)
            .isEqualTo(expectedValue)
    }

    fun doubleLiteral(expectedValue: Double) {
        actual.isInstanceOf(DoubleLiteral::class)
            .prop(DoubleLiteral::value)
            .isEqualTo(expectedValue)
    }

    fun trueLiteral() {
        actual.isInstanceOf(TrueLiteral::class)
    }

    fun falseLiteral() {
        actual.isInstanceOf(FalseLiteral::class)
    }
}