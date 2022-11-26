package io.github.elizabethlfransen.parser.util.ast

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import assertk.assertions.support.expected
import assertk.assertions.support.show
import io.github.elizabethlfransen.lizlang.ast.*
import io.github.elizabethlfransen.lizlang.ast.exp.*
import kotlin.reflect.KClass

private fun <T : ASTNode> Assert<ASTNode>.isInstanceOfNode(type: KClass<T>) = transform(name) { actual ->
    if(type.isInstance(actual)) {
        @Suppress("UNCHECKED_CAST")
        return@transform actual as T
    }
    expected("to be a ${show(type.simpleName)} node but was a ${show(actual::class.simpleName)} node")
}

abstract class ASTVerifier<TNode : ASTNode>(private val nodeType: KClass<TNode>) {
    abstract fun verify(node: Assert<TNode>)
    open fun tryVerify(node: Assert<ASTNode>) {
        node.isInstanceOfNode(nodeType)
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

class BinaryExpressionVerifier<TNode: BinaryExpression<TLeft, TRight>, TLeft: ASTNode, TRight : ASTNode>(
    nodeType: KClass<TNode>,
    private val leftVerifier: ASTVerifier<out TLeft>,
    private val rightVerifier: ASTVerifier<out TRight>
) : ASTVerifier<TNode>(nodeType) {
    override fun verify(node: Assert<TNode>) {
        node.prop(BinaryExpression<TLeft, TRight>::left)
            .let(leftVerifier::tryVerify)
        node.prop(BinaryExpression<TLeft, TRight>::right)
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

class IdentifierVerifier(
    private val verifier: (Assert<String>) -> Unit
) : ASTVerifier<ASTIdentifier>(ASTIdentifier::class) {
    override fun verify(node: Assert<ASTIdentifier>) {
        node.prop(ASTIdentifier::identifier)
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

fun mod(
    leftVerifier: ASTVerifier<out ASTExpression>,
    rightVerifier: ASTVerifier<out ASTExpression>
) = BinaryExpressionVerifier(ModExpression::class, leftVerifier, rightVerifier)

fun sub(
    leftVerifier: ASTVerifier<out ASTExpression>,
    rightVerifier: ASTVerifier<out ASTExpression>
) = BinaryExpressionVerifier(SubtractionExpression::class, leftVerifier, rightVerifier)

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

fun cast(
    leftVerifier: ASTVerifier<out ASTExpression>,
    rightVerifier: ASTVerifier<out ASTIdentifier>
) = BinaryExpressionVerifier(CastExpression::class,leftVerifier, rightVerifier)

fun identifier(
    identifierVerifier: (Assert<String>) -> Unit
) = IdentifierVerifier(identifierVerifier)

fun identifier(
    expectedIdentifier: String
) = identifier { actual -> actual.isEqualTo(expectedIdentifier) }

fun divide(
    leftVerifier: ASTVerifier<out ASTExpression>,
    rightVerifier: ASTVerifier<out ASTExpression>
) = BinaryExpressionVerifier(DivideExpression::class, leftVerifier, rightVerifier)

fun shl(
    leftVerifier: ASTVerifier<out ASTExpression>,
    rightVerifier: ASTVerifier<out ASTExpression>
) = BinaryExpressionVerifier(ShiftLeftExpression::class, leftVerifier, rightVerifier)
fun shr(
    leftVerifier: ASTVerifier<out ASTExpression>,
    rightVerifier: ASTVerifier<out ASTExpression>
) = BinaryExpressionVerifier(ShiftRightExpression::class, leftVerifier, rightVerifier)
fun ushr(
    leftVerifier: ASTVerifier<out ASTExpression>,
    rightVerifier: ASTVerifier<out ASTExpression>
) = BinaryExpressionVerifier(UnsignedShiftRightExpression::class, leftVerifier, rightVerifier)
fun lt(
    leftVerifier: ASTVerifier<out ASTExpression>,
    rightVerifier: ASTVerifier<out ASTExpression>
) = BinaryExpressionVerifier(LessThanExpression::class, leftVerifier, rightVerifier)
fun lte(
    leftVerifier: ASTVerifier<out ASTExpression>,
    rightVerifier: ASTVerifier<out ASTExpression>
) = BinaryExpressionVerifier(LessThanOrEqualToExpression::class, leftVerifier, rightVerifier)
fun gt(
    leftVerifier: ASTVerifier<out ASTExpression>,
    rightVerifier: ASTVerifier<out ASTExpression>
) = BinaryExpressionVerifier(GreaterThanExpression::class, leftVerifier, rightVerifier)
fun gte(
    leftVerifier: ASTVerifier<out ASTExpression>,
    rightVerifier: ASTVerifier<out ASTExpression>
) = BinaryExpressionVerifier(GreaterThanOrEqualToExpression::class, leftVerifier, rightVerifier)


fun Assert<ASTNode>.matches(verifier: ASTVerifier<*>) = let(verifier::tryVerify)


