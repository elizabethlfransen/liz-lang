package io.github.elizabethlfransen.lizlang

import io.github.elizabethlfransen.lizlang.parser.LizLangBaseVisitor
import io.github.elizabethlfransen.lizlang.parser.LizLangParser
import io.github.elizabethlfransen.lizlang.parser.LizLangParser.FloatLiteralContext
import io.github.elizabethlfransen.lizlang.parser.LizLangParser.IntLiteralContext
import org.antlr.v4.runtime.tree.TerminalNode

/**
 * This class is used to take the result from [LizLangParser] and generate a [ASTNode] which can be used in [ParserVisitor]
 */
open class LizLangASTParser : LizLangBaseVisitor<ASTNode>() {
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

        return buildSingleChildNodeFromExpression(ctx, value, ::IntLiteral)
    }

    override fun visitFloatLiteral(ctx: FloatLiteralContext): ASTNode {
        val value = ctx.text.replace("_", "")
            .toFloat()
        return buildSingleChildNodeFromExpression(ctx, value, ::FloatLiteral)
    }

    override fun visitDoubleLiteral(ctx: LizLangParser.DoubleLiteralContext): ASTNode {
        val value = ctx.text.replace("_", "")
            .toDouble()
        return buildSingleChildNodeFromExpression(ctx, value, ::DoubleLiteral)
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
        return buildSingleChildNodeFromExpression(ctx, value, ::StringLiteral)
    }

    override fun visitCharLiteral(ctx: LizLangParser.CharLiteralContext): ASTNode {
        return buildSingleChildNodeFromExpression(ctx, ctx.text[1], ::CharacterLiteral)
    }

    override fun visitLiteral(ctx: LizLangParser.LiteralContext): ASTLiteral<*> {
        return super.visitLiteral(ctx) as ASTLiteral<*>
    }

    override fun visitLiteralExpression(ctx: LizLangParser.LiteralExpressionContext): ASTNode {
        return buildSingleChildNodeFromExpression(
            ctx,
            visitLiteral(ctx.literal()),
            ::LiteralExpression
        )
    }
}

