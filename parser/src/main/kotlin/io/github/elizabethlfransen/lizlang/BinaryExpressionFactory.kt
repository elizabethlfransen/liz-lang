package io.github.elizabethlfransen.lizlang

import io.github.elizabethlfransen.lizlang.ast.*
import io.github.elizabethlfransen.lizlang.parser.LizLangLexer
import io.github.elizabethlfransen.lizlang.parser.LizLangParser.BinaryExpContext
import io.github.elizabethlfransen.lizlang.parser.LizLangParserVisitor
import io.github.elizabethlfransen.lizlang.util.TextLocation

typealias BinaryExpressionBuilder = (
    left: ASTExpression,
    right: ASTExpression,
    text: String,
    start: TextLocation,
    stop: TextLocation
) -> BinaryExpression<ASTExpression,ASTExpression>
internal object BinaryExpressionFactory {
    private val expressionBuilders: Map<Int, BinaryExpressionBuilder> = mapOf(
        LizLangLexer.STAR to ::MultiplicationExpression,
        LizLangLexer.FORWARD_SLASH to ::DivideExpression,
        LizLangLexer.PLUS to ::AdditionExpression,
    )

    operator fun get(type: Int): BinaryExpressionBuilder {
        return expressionBuilders.getOrElse(type) {
            val tokenName = LizLangLexer.VOCABULARY.getDisplayName(type)
            throw IllegalArgumentException(
                "No unary expression defined for token type $tokenName"
            )
        }
    }

    fun buildExpression(visitor: LizLangParserVisitor<*>, ctx: BinaryExpContext) : BinaryExpression<ASTExpression,ASTExpression> {
        val builder = get(ctx.op.type)
        return buildASTFromContext(
            ctx,
            ctx.left.accept(visitor) as ASTExpression,
            ctx.right.accept(visitor) as ASTExpression,
            builder
        )
    }
}