package io.github.elizabethlfransen.parser.util.ast

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.prop
import io.github.elizabethlfransen.lizlang.ASTNode
import io.github.elizabethlfransen.lizlang.FloatLiteral
import io.github.elizabethlfransen.lizlang.IntLiteral

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
}