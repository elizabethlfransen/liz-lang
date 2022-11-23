package io.github.elizabethlfransen.parser.util.ast.exp

import assertk.Assert
import io.github.elizabethlfransen.lizlang.ast.ASTExpression

class UnaryExpressionVerifier(val name: String, private val actual: Assert<ASTExpression>) : ExpressionVerifier() {
    private var finished = false

    override fun runAssertion(assertion: (actual: Assert<ASTExpression>) -> Unit) {
        if (finished) throw IllegalStateException("$name expression expects one child expression but had multiple")
        assertion(actual)
        finished = true
    }

    override fun verifyFinished() {
        if (!finished)
            throw IllegalStateException("$name expression expects one child expression but had multiple")
    }
}