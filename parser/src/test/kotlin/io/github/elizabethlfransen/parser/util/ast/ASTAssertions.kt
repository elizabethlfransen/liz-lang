package io.github.elizabethlfransen.parser.util.ast

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotNull
import assertk.assertions.prop
import assertk.assertions.support.appendName
import io.github.elizabethlfransen.lizlang.*
import io.github.elizabethlfransen.lizlang.parser.LizLangLexer
import io.github.elizabethlfransen.lizlang.parser.LizLangParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.ParserRuleContext

fun Assert<String>.asAST(parseFunction: (LizLangParser) -> ParserRuleContext): Assert<ASTNode> {
    val result = transform(appendName("AST")) { actual ->
        val inputStream = CharStreams.fromString(actual)
        val tokens = CommonTokenStream(LizLangLexer(inputStream))
        val parser = LizLangParser(tokens)
        val astParser = LizLangASTParser()
        parseFunction(parser).accept(astParser)
    }
    result.isNotNull()
    return result
}

fun Assert<ASTNode>.isIntLiteral(expectedValue: Int) {
    isInstanceOf(IntLiteral::class)
        .prop(IntLiteral::value)
        .isEqualTo(expectedValue)
}

fun Assert<ASTNode>.isFloatLiteral(expectedValue: Float) {
    isInstanceOf(FloatLiteral::class)
        .prop(FloatLiteral::value)
        .isEqualTo(expectedValue)
}

fun Assert<ASTNode>.isDoubleLiteral(expectedValue: Double) {
    isInstanceOf(DoubleLiteral::class)
        .prop(DoubleLiteral::value)
        .isEqualTo(expectedValue)
}

fun Assert<ASTNode>.isTrueLiteral() {
    isInstanceOf(TrueLiteral::class)
}

fun Assert<ASTNode>.isFalseLiteral() {
    isInstanceOf(FalseLiteral::class)
}

fun Assert<ASTNode>.isStringLiteral(expectedValue: String) {
    isInstanceOf(StringLiteral::class)
        .prop(StringLiteral::value)
        .isEqualTo(expectedValue)
}

fun Assert<ASTNode>.isCharLiteral(expectedValue: Char) {
    isInstanceOf(CharacterLiteral::class)
        .prop(CharacterLiteral::value)
        .isEqualTo(expectedValue)
}

data class LiteralExpressionAssertionContext(val child: Assert<ASTLiteral<*>>)

fun Assert<ASTNode>.isLiteralExpression(verify: LiteralExpressionAssertionContext.() -> Unit = {}) {
    val literalExpression = this.isInstanceOf(LiteralExpression::class)
    val child = literalExpression.prop(LiteralExpression::child)
    LiteralExpressionAssertionContext(child)
        .apply(verify)
}

data class InfixOperatorAssertionContext(val left: Assert<ASTExpression>, val right: Assert<ASTExpression>)

private inline fun <reified TExpressionType : InfixExpression> Assert<ASTNode>.isInfixOperatorExpression(verify: InfixOperatorAssertionContext.() -> Unit) {
    val infixExpression = this.isInstanceOf(TExpressionType::class)
    val left = infixExpression.prop(InfixExpression::left)
    val right = infixExpression.prop(InfixExpression::right)
    InfixOperatorAssertionContext(left, right)
        .apply(verify)
}

fun Assert<ASTNode>.isMultiplicationExpression(verify: InfixOperatorAssertionContext.() -> Unit = {}) =
    isInfixOperatorExpression<MultiplicationExpression>(verify)

