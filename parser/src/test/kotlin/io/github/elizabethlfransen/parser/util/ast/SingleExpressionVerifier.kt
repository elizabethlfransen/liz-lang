package io.github.elizabethlfransen.parser.util.ast

import assertk.Assert
import assertk.assertions.isInstanceOf
import assertk.assertions.prop
import io.github.elizabethlfransen.lizlang.*
import kotlin.reflect.KClass

fun Assert<ASTNode>.isExpression(verifier: SingleExpressionVerifier.() -> Unit) {
    this.isInstanceOf(ASTExpression::class)
        .let { root -> SingleExpressionVerifier("Root", root) }
        .apply(verifier)
        .verifyFinished()
}

abstract class ExpressionVerifier {

    private fun infix(name: String, expressionType: KClass<out InfixExpression>, verifier: InfixExpressionVerifier.() -> Unit) {
        runAssertion { actual ->
            actual.isInstanceOf(expressionType)
                .let { child -> InfixExpressionVerifier(name, child) }
                .apply(verifier)
                .verifyFinished()
        }
    }

    fun mult(verifier: InfixExpressionVerifier.() -> Unit) = infix("Multiplication", MultiplicationExpression::class, verifier)
    fun add(verifier: InfixExpressionVerifier.() -> Unit) = infix("Addition", AdditionExpression::class, verifier)

    fun paren(verifier: ExpressionVerifier.() -> Unit) {
        runAssertion {actual ->
            actual.isInstanceOf(ParenthesisExpression::class)
                .prop(ParenthesisExpression::child)
                .let { child -> SingleExpressionVerifier("Parenthesis", child) }
                .apply(verifier)
                .verifyFinished()
        }
    }

    fun int(value: Int) {
        runAssertion { actual ->
            actual.isInstanceOf(LiteralExpression::class)
                .prop(LiteralExpression::child)
                .isIntLiteral(value)
        }
    }

    protected abstract fun runAssertion(assertion: (actual: Assert<ASTExpression>) -> Unit)
    abstract fun verifyFinished()
}

class InfixExpressionVerifier(private val name: String, private val actual: Assert<InfixExpression>) : ExpressionVerifier() {
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

class SingleExpressionVerifier(val name: String, private val actual: Assert<ASTExpression>) : ExpressionVerifier() {
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