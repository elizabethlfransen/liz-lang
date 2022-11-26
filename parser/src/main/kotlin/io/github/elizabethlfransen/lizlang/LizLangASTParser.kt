package io.github.elizabethlfransen.lizlang

import io.github.elizabethlfransen.lizlang.ast.*
import io.github.elizabethlfransen.lizlang.ast.exp.*
import io.github.elizabethlfransen.lizlang.parser.LizLangLexer
import io.github.elizabethlfransen.lizlang.parser.LizLangParser
import io.github.elizabethlfransen.lizlang.parser.LizLangParser.*
import io.github.elizabethlfransen.lizlang.parser.LizLangParserBaseVisitor
import org.antlr.v4.runtime.tree.TerminalNode
import java.lang.IllegalArgumentException

/**
 * This class is used to take the result from [LizLangParser] and generate a [ASTNode] which can be used in [ParserVisitor]
 */
open class LizLangASTParser : LizLangParserBaseVisitor<ASTNode>() {
    override fun visitIntLiteral(ctx: IntLiteralContext): ASTNode {
        // remove underscores
        var intLiteral = ctx.text.replace("_", "")
        var radix = 10
        // if hexadecimal number then remove prefix and change radix to 16
        if (intLiteral.startsWith("0x", ignoreCase = true)) {
            intLiteral = intLiteral.substring(2)
            radix = 16
        }
        // parse the integer
        val value = intLiteral.toInt(radix)

        return buildASTFromContext(ctx, value, ::IntLiteral)
    }

    override fun visitFloatLiteral(ctx: FloatLiteralContext): ASTNode {
        val value = ctx.text.replace("_", "")
            .toFloat()
        return buildASTFromContext(ctx, value, ::FloatLiteral)
    }

    override fun visitDoubleLiteral(ctx: DoubleLiteralContext): ASTNode {
        val value = ctx.text.replace("_", "")
            .toDouble()
        return buildASTFromContext(ctx, value, ::DoubleLiteral)
    }

    override fun visitTrueLiteral(ctx: TrueLiteralContext): ASTNode {
        return buildASTFromContext(ctx, ::TrueLiteral)
    }

    override fun visitFalseLiteral(ctx: FalseLiteralContext): ASTNode {
        return buildASTFromContext(ctx, ::FalseLiteral)
    }

    override fun visitStringLiteral(ctx: StringLiteralContext): ASTNode {
        val value = ctx.STRING_CHARACTER()
            .joinToString(separator = "", transform = TerminalNode::getText)
        return buildASTFromContext(ctx, value, ::StringLiteral)
    }

    override fun visitCharLiteral(ctx: CharLiteralContext): ASTNode {
        return buildASTFromContext(ctx, ctx.text[1], ::CharacterLiteral)
    }

    override fun visitLiteral(ctx: LiteralContext): ASTLiteral<*> {
        return super.visitLiteral(ctx) as ASTLiteral<*>
    }


    override fun visitLiteralExp(ctx: LiteralExpContext): LiteralExpression {
        return buildASTFromContext(
            ctx,
            visitLiteral(ctx.literal()),
            ::LiteralExpression
        )
    }

    override fun visitUnaryExp(ctx: UnaryExpContext): ASTNode {
        return UnaryExpressionFactory.buildExpression(this, ctx)
    }

    override fun visitBinaryExp(ctx: BinaryExpContext): ASTNode {
        return BinaryExpressionFactory.buildExpression(this, ctx)
    }

    override fun visitParenthesisExp(ctx: ParenthesisExpContext): ParenthesisExpression {
        return buildASTFromContext(
            ctx,
            ctx.child.accept(this) as ASTExpression,
            ::ParenthesisExpression
        )
    }

    override fun visitIdentifier(ctx: IdentifierContext): ASTIdentifier {
        return buildASTFromContext(
            ctx,
            ctx.IDENTIFIER().text,
            ::ASTIdentifier
        )
    }

    override fun visitCastExpression(ctx: CastExpressionContext): CastExpression {
        return buildASTFromContext(
            ctx,
            ctx.left.accept(this) as ASTExpression,
            ctx.right.let(this::visitIdentifier),
            ::CastExpression
        )
    }

    override fun visitPostIncDecExp(ctx: PostIncDecExpContext): ASTNode {
        val builder = when(ctx.op.type) {
            LizLangLexer.INCREMENT -> ::PostIncrementExpression
            LizLangLexer.DECREMENT -> ::PostDecrementExpression
            else -> {
                val tokenName = LizLangLexer.VOCABULARY.getDisplayName(ctx.op.type)
                throw IllegalArgumentException("Invalid token: $tokenName")
            }
        }
        return buildASTFromContext(
            ctx,
            ctx.child.accept(this) as ASTExpression,
            builder
        )
    }
}

