package io.github.elizabethlfransen.lizlang

import io.github.elizabethlfransen.lizlang.ast.ASTExpression
import io.github.elizabethlfransen.lizlang.ast.UnaryExpression
import io.github.elizabethlfransen.lizlang.ast.*
import io.github.elizabethlfransen.lizlang.parser.LizLangLexer
import io.github.elizabethlfransen.lizlang.parser.LizLangParser.UnaryExpContext
import io.github.elizabethlfransen.lizlang.parser.LizLangParserVisitor
import io.github.elizabethlfransen.lizlang.util.TextLocation

typealias UnaryExpressionBuilder = (
    child: ASTExpression,
    text: String,
    start: TextLocation,
    stop: TextLocation
) -> UnaryExpression<ASTExpression>

internal object UnaryExpressionFactory {
    private val expressionBuilders: Map<Int, UnaryExpressionBuilder> = mapOf(
        LizLangLexer.PLUS to ::UnaryPlusExpression,
        LizLangLexer.MINUS to ::UnaryMinusExpression,
        LizLangLexer.EXCLAMATION_MARK to ::NotExpression,
        LizLangLexer.GRAVE to ::BitwiseComplementExpression,
        LizLangLexer.INCREMENT to ::PreIncrementExpression,
        LizLangLexer.DECREMENT to ::PreDecrementExpression,
    )

    operator fun get(type: Int): UnaryExpressionBuilder {
        return expressionBuilders.getOrElse(type) {
            val tokenName = LizLangLexer.VOCABULARY.getDisplayName(type)
            throw IllegalArgumentException(
                "No unary expression defined for token type $tokenName"
            )
        }
    }

    fun buildExpression(visitor: LizLangParserVisitor<*>, ctx: UnaryExpContext) : UnaryExpression<ASTExpression> {
        val builder = get(ctx.op.type)
        return buildASTFromContext(
            ctx,
            ctx.child.accept(visitor) as ASTExpression,
            builder
        )
    }
}