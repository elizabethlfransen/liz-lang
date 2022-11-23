package io.github.elizabethlfransen.parser.util.ast

import assertk.Assert
import assertk.assertions.isInstanceOf
import assertk.assertions.prop
import io.github.elizabethlfransen.lizlang.*
import kotlin.reflect.KClass

fun Assert<ASTNode>.isExpression(verifier: InitialExpressionVerifier.() -> Unit) {
    this.isInstanceOf(ASTExpression::class)
        .let(::InitialExpressionVerifier)
        .apply(verifier)
        .verifyFinished()
}

abstract class ExpressionVerifier {

    private fun infix(expressionType: KClass<out InfixExpression>, verifier: InfixExpressionVerifier.() -> Unit) {
        runAssertion { actual ->
            actual.isInstanceOf(expressionType)
                .let(::InfixExpressionVerifier)
                .apply(verifier)
                .verifyFinished()
        }
    }

    fun mult(verifier: InfixExpressionVerifier.() -> Unit) = infix(MultiplicationExpression::class, verifier)
    fun add(verifier: InfixExpressionVerifier.() -> Unit) = infix(AdditionExpression::class, verifier)

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

class InfixExpressionVerifier(private val actual: Assert<InfixExpression>) : ExpressionVerifier() {
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
        if (state != State.FINISHED) throw IllegalStateException("Attempted to verify without exhausting all nodes")
    }
}

class InitialExpressionVerifier(private val actual: Assert<ASTExpression>) : ExpressionVerifier() {
    private var finished = false

    override fun runAssertion(assertion: (actual: Assert<ASTExpression>) -> Unit) {
        if (finished) throw IllegalStateException("Attempted to have multiple verifications at root level")
        assertion(actual)
        finished = true
    }

    override fun verifyFinished() {
        if (!finished)
            throw IllegalStateException("Attempted to verify without a root expression")
    }
}