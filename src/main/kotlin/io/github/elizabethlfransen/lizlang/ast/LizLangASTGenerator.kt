package io.github.elizabethlfransen.lizlang.ast

import io.github.elizabethlfransen.lizlang.internal.LizLangParser
import io.github.elizabethlfransen.lizlang.internal.LizLangParserBaseVisitor

class LizLangASTGenerator : LizLangParserBaseVisitor<ASTNode>() {
    override fun visitLizLangFile(ctx: LizLangParser.LizLangFileContext) =
        ctx
            .identifier()
            .map(this::visitIdentifier)
            .let(::LizLangFileASTNode)

    override fun visitIdentifier(ctx: LizLangParser.IdentifierContext) = IdentifierASTNode(ctx)
}