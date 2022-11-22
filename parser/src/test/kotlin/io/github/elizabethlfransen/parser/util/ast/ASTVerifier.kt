package io.github.elizabethlfransen.parser.util.ast

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.prop
import io.github.elizabethlfransen.lizlang.AST
import io.github.elizabethlfransen.lizlang.IntLiteral

class ASTVerifier(private val actual: Assert<AST>) {

    fun intLiteral(expectedValue: Int) {
        actual.isInstanceOf(IntLiteral::class)
            .prop(IntLiteral::value)
            .isEqualTo(expectedValue)
    }
}