package io.github.elizabethlfransen.parser.util.ast

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.prop
import io.github.elizabethlfransen.lizlang.ast.*
import kotlin.reflect.KClass

abstract class ASTVerifier<TNode : ASTNode>(private val nodeType: KClass<TNode>) {
    abstract fun verify(node: Assert<TNode>)
    open fun tryVerify(node: Assert<ASTNode>) {
        node.isInstanceOf(nodeType)
            .let(this::verify)
    }
}

class UnaryExpressionVerifier<TNode: UnaryExpression<TChild>, TChild: ASTNode>(
    nodeType: KClass<TNode>,
    private val childVerifier: ASTVerifier<out TChild>
) : ASTVerifier<TNode>(nodeType) {
    override fun verify(node: Assert<TNode>) {
        node.prop(UnaryExpression<TChild>::child)
            .let(childVerifier::tryVerify)
    }
}

class BinaryExpressionVerifier<TNode: InfixExpression<TLeft, TRight>, TLeft: ASTNode, TRight : ASTNode>(
    nodeType: KClass<TNode>,
    private val leftVerifier: ASTVerifier<out TLeft>,
    private val rightVerifier: ASTVerifier<out TRight>
) : ASTVerifier<TNode>(nodeType) {
    override fun verify(node: Assert<TNode>) {
        node.prop(InfixExpression<TLeft, TRight>::left)
            .let(leftVerifier::tryVerify)
        node.prop(InfixExpression<TLeft, TRight>::right)
            .let(rightVerifier::tryVerify)
    }
}

class LiteralVerifier(
    private val verifier: ( Assert<*>) -> Unit
    ) : ASTVerifier<ASTLiteral<*>>(ASTLiteral::class) {
    override fun verify(node: Assert<ASTLiteral<*>>) {
        node.prop(ASTLiteral<*>::value)
            .let(verifier)
    }
}

fun literal(verifier: (Assert<*>) -> Unit) = LiteralVerifier(verifier)

fun literal(expectedValue: Any?) = literal { actual ->
    actual.isEqualTo(expectedValue)
}

fun literalExp(verifier: ASTVerifier<ASTLiteral<*>>) = UnaryExpressionVerifier(LiteralExpression::class,verifier)

fun literalExp(expectedValue: Any?) = literalExp(literal(expectedValue))

fun mult(
    leftVerifier: ASTVerifier<out ASTExpression>,
    rightVerifier: ASTVerifier<out ASTExpression>
) = BinaryExpressionVerifier(MultiplicationExpression::class,leftVerifier, rightVerifier)


fun add(
    leftVerifier: ASTVerifier<out ASTExpression>,
    rightVerifier: ASTVerifier<out ASTExpression>
) = BinaryExpressionVerifier(AdditionExpression::class,leftVerifier, rightVerifier)

fun paren(
    childVerifier: ASTVerifier<out ASTExpression>
) = UnaryExpressionVerifier(ParenthesisExpression::class,childVerifier)

fun unaryMinus(
    childVerifier: ASTVerifier<out ASTExpression>
) = UnaryExpressionVerifier(UnaryMinusExpression::class,childVerifier)

fun unaryPlus(
    childVerifier: ASTVerifier<out ASTExpression>
) = UnaryExpressionVerifier(UnaryPlusExpression::class,childVerifier)


fun postDecrement(
    childVerifier: ASTVerifier<out ASTExpression>
) = UnaryExpressionVerifier(PostDecrementExpression::class,childVerifier)

fun postIncrement(
    childVerifier: ASTVerifier<out ASTExpression>
) = UnaryExpressionVerifier(PostIncrementExpression::class,childVerifier)

fun preDecrement(
    childVerifier: ASTVerifier<out ASTExpression>
) = UnaryExpressionVerifier(PreDecrementExpression::class,childVerifier)

fun preIncrement(
    childVerifier: ASTVerifier<out ASTExpression>
) = UnaryExpressionVerifier(PreIncrementExpression::class,childVerifier)

fun not(
    childVerifier: ASTVerifier<out ASTExpression>
) = UnaryExpressionVerifier(NotExpression::class,childVerifier)

fun bnot(
    childVerifier: ASTVerifier<out ASTExpression>
) = UnaryExpressionVerifier(BitwiseComplementExpression::class, childVerifier)


fun Assert<ASTNode>.matches(verifier: ASTVerifier<*>) = let(verifier::tryVerify)


