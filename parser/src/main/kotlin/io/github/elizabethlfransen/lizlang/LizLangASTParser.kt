package io.github.elizabethlfransen.lizlang

import io.github.elizabethlfransen.lizlang.ast.*
import io.github.elizabethlfransen.lizlang.parser.LizLangParser
import io.github.elizabethlfransen.lizlang.parser.LizLangParser.*
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

    override fun visitMultiplyExp(ctx: MultiplyExpContext): MultiplicationExpression {
        return buildASTFromContext(
            ctx,
            ctx.left.accept(this) as ASTExpression,
            ctx.right.accept(this) as ASTExpression,
            ::MultiplicationExpression
        )
    }

    override fun visitAddExp(ctx: AddExpContext): AdditionExpression {
        return buildASTFromContext(
            ctx,
            ctx.left.accept(this) as ASTExpression,
            ctx.right.accept(this) as ASTExpression,
            ::AdditionExpression
        )
    }

    override fun visitParenthesisExp(ctx: ParenthesisExpContext): ParenthesisExpression {
        return buildASTFromContext(
            ctx,
            ctx.child.accept(this) as ASTExpression,
            ::ParenthesisExpression
        )
    }

    override fun visitUnaryMinusExp(ctx: UnaryMinusExpContext): UnaryMinusExpression {
        return buildASTFromContext(
            ctx,
            ctx.child.accept(this) as ASTExpression,
            ::UnaryMinusExpression
        )
    }

    override fun visitUnaryPlusExp(ctx: UnaryPlusExpContext): UnaryPlusExpression {
        return buildASTFromContext(
            ctx,
            ctx.child.accept(this) as ASTExpression,
            ::UnaryPlusExpression
        )
    }

    override fun visitPostDecrementExp(ctx: PostDecrementExpContext): PostDecrementExpression {
        return buildASTFromContext(
            ctx,
            ctx.child.accept(this) as ASTExpression,
            ::PostDecrementExpression
        )
    }


    override fun visitPostIncrementExp(ctx: PostIncrementExpContext): PostIncrementExpression {
        return buildASTFromContext(
            ctx,
            ctx.child.accept(this) as ASTExpression,
            ::PostIncrementExpression
        )
    }

    override fun visitPreDecrementExp(ctx: PreDecrementExpContext): PreDecrementExpression {
        return buildASTFromContext(
            ctx,
            ctx.child.accept(this) as ASTExpression,
            ::PreDecrementExpression
        )
    }

    override fun visitPreIncrementExp(ctx: PreIncrementExpContext) : PreIncrementExpression {
        return buildASTFromContext(
            ctx,
            ctx.child.accept(this) as ASTExpression,
            ::PreIncrementExpression
        )
    }

    override fun visitLogicalNotExp(ctx: LogicalNotExpContext): ASTNode {
        return buildASTFromContext(
            ctx,
            ctx.child.accept(this) as ASTExpression,
            ::NotExpression
        )
    }

    override fun visitBitwiseComplementExp(ctx: BitwiseComplementExpContext): BitwiseComplementExpression {
        return buildASTFromContext(
            ctx,
            ctx.child.accept(this) as ASTExpression,
            ::BitwiseComplementExpression
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

    override fun visitDivideExpression(ctx: DivideExpressionContext): DivideExpression {
        return buildASTFromContext(
            ctx,
            ctx.left.accept(this) as ASTExpression,
            ctx.right.accept(this) as ASTExpression,
            ::DivideExpression
        )
    }
}

