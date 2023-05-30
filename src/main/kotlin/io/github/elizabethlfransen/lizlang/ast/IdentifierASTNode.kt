package io.github.elizabethlfransen.lizlang.ast

import io.github.elizabethlfransen.lizlang.internal.LizLangParser

data class IdentifierASTNode(
    val identifier: String,
    val ctx: LizLangParser.IdentifierContext
) : TerminalASTNode {
    constructor(ctx: LizLangParser.IdentifierContext) : this(ctx.IDENTIFIER().text, ctx)

    override fun toString(): String {
        return "ID(${identifier})"
    }
}