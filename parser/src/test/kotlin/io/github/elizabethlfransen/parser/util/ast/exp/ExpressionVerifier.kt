package io.github.elizabethlfransen.parser.util.ast.exp

import assertk.Assert
import assertk.assertions.isInstanceOf
import assertk.assertions.prop
import io.github.elizabethlfransen.lizlang.ast.*
import io.github.elizabethlfransen.parser.util.ast.isIntLiteral
import kotlin.reflect.KClass

@Suppress("SimpleRedundantLet")
abstract class ExpressionVerifier {

    private fun infix(
        name: String,
        expressionType: KClass<out InfixExpression>,
        verifier: InfixExpressionVerifier.() -> Unit
    ) {
        runAssertion { actual ->
            actual.isInstanceOf(expressionType)
                .let { child -> InfixExpressionVerifier(name, child) }
                .apply(verifier)
                .verifyFinished()
        }
    }

    private fun unary(
        name: String,
        expressionType: KClass<out UnaryExpression>,
        verifier: UnaryExpressionVerifier.() -> Unit
    ) {
        runAssertion { actual ->
            actual.isInstanceOf(expressionType)
                .let { exp -> exp.prop(UnaryExpression::child) }
                .let { child -> UnaryExpressionVerifier(name, child) }
                .apply(verifier)
                .verifyFinished()
        }
    }

    fun mult(verifier: InfixExpressionVerifier.() -> Unit) =
        infix("Multiplication", MultiplicationExpression::class, verifier)

    fun add(verifier: InfixExpressionVerifier.() -> Unit) = infix("Addition", AdditionExpression::class, verifier)

    fun paren(verifier: UnaryExpressionVerifier.() -> Unit) =
        unary("Parenthesis", ParenthesisExpression::class, verifier)

    fun unaryMinus(verifier: UnaryExpressionVerifier.() -> Unit) =
        unary("Unary Minus", UnaryMinusExpression::class, verifier)

    fun unaryPlus(verifier: UnaryExpressionVerifier.() -> Unit) =
        unary("Unary Plus", UnaryPlusExpression::class, verifier)

    fun postIncrement(verifier: UnaryExpressionVerifier.() -> Unit) =
        unary("Post Increment", PostIncrementExpression::class, verifier)

    fun postDecrement(verifier: UnaryExpressionVerifier.() -> Unit) =
        unary("Post Decrement", PostDecrementExpression::class, verifier)

    fun preIncrement(verifier: UnaryExpressionVerifier.() -> Unit) =
        unary("Pre Increment", PreIncrementExpression::class, verifier)

    fun preDecrement(verifier: UnaryExpressionVerifier.() -> Unit) =
        unary("Pre Decrement", PreDecrementExpression::class, verifier)
    fun not(verifier: UnaryExpressionVerifier.() -> Unit) =
        unary("Not", NotExpression::class, verifier)

    fun bnot(verifier: UnaryExpressionVerifier.() -> Unit) =
        unary("Bitwise Complement", BitwiseComplementExpression::class, verifier)

    fun cast(verifier: UnaryExpressionVerifier.() -> Unit) =

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