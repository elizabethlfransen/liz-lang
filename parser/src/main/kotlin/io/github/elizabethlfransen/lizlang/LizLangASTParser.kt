package io.github.elizabethlfransen.lizlang

import io.github.elizabethlfransen.lizlang.parser.LizLangParser
import io.github.elizabethlfransen.lizlang.parser.LizLangParser.FloatLiteralContext
import io.github.elizabethlfransen.lizlang.parser.LizLangParser.IntLiteralContext
import io.github.elizabethlfransen.lizlang.parser.LizLangParserBaseVisitor
import org.antlr.v4.runtime.tree.TerminalNode

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

    override fun visitDoubleLiteral(ctx: LizLangParser.DoubleLiteralContext): ASTNode {
        val value = ctx.text.replace("_", "")
            .toDouble()
        return buildASTFromContext(ctx, value, ::DoubleLiteral)
    }

    override fun visitTrueLiteral(ctx: LizLangParser.TrueLiteralContext): ASTNode {
        return buildASTFromContext(ctx, ::TrueLiteral)
    }

    override fun visitFalseLiteral(ctx: LizLangParser.FalseLiteralContext): ASTNode {
        return buildASTFromContext(ctx, ::FalseLiteral)
    }

    override fun visitStringLiteral(ctx: LizLangParser.StringLiteralContext): ASTNode {
        val value = ctx.STRING_CHARACTER()
            .joinToString(separator = "", transform = TerminalNode::getText)
        return buildASTFromContext(ctx, value, ::StringLiteral)
    }

    override fun visitCharLiteral(ctx: LizLangParser.CharLiteralContext): ASTNode {
        return buildASTFromContext(ctx, ctx.text[1], ::CharacterLiteral)
    }

    override fun visitLiteral(ctx: LizLangParser.LiteralContext): ASTLiteral<*> {
        return super.visitLiteral(ctx) as ASTLiteral<*>
    }


    override fun visitLiteralExp(ctx: LizLangParser.LiteralExpContext): LiteralExpression {
        return buildASTFromContext(
            ctx,
            visitLiteral(ctx.literal()),
            ::LiteralExpression
        )
    }

    override fun visitMultiplyExp(ctx: LizLangParser.MultiplyExpContext): MultiplicationExpression {
        return buildASTFromContext(
            ctx,
            ctx.left.accept(this) as ASTExpression,
            ctx.right.accept(this) as ASTExpression,
            ::MultiplicationExpression
        )
    }

    override fun visitAddExp(ctx: LizLangParser.AddExpContext): AdditionExpression {
        return buildASTFromContext(
            ctx,
            ctx.left.accept(this) as ASTExpression,
            ctx.right.accept(this) as ASTExpression,
            ::AdditionExpression
        )
    }

}

