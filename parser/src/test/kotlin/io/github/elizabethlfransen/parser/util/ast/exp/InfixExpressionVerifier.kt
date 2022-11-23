package io.github.elizabethlfransen.parser.util.ast.exp

import assertk.Assert
import assertk.assertions.prop
import io.github.elizabethlfransen.lizlang.ast.ASTExpression
import io.github.elizabethlfransen.lizlang.ast.InfixExpression

class InfixExpressionVerifier(private val name: String, private val actual: Assert<InfixExpression>) :
    ExpressionVerifier() {
    private enum class State {
        LEFT,
        RIGHT,
        FINISHED,
    }

    private val nextState = mapOf(
        State.LEFT to State.RIGHT,
        State.RIGHT to State.FINISHED
    )
    private var state = State.LEFT

    override fun runAssertion(assertion: (actual: Assert<ASTExpression>) -> Unit) {
        val target = when (state) {
            State.LEFT -> actual.prop(InfixExpression::left)
            State.RIGHT -> actual.prop(InfixExpression::right)
            State.FINISHED -> throw IllegalStateException("Attempted to execute assertion but all nodes were consumed")
        }
        state = nextState[state]!!
        assertion(target)
    }


    override fun verifyFinished() {
        if (state == State.LEFT) throw IllegalStateException("$name expression expects two child expression but had 0")
        if (state == State.RIGHT) throw IllegalStateException("$name expression expects two child expression but had 1")
    }
}