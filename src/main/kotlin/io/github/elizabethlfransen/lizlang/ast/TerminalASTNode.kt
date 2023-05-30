package io.github.elizabethlfransen.lizlang.ast

interface TerminalASTNode : ASTNode {
    override val children: List<ASTNode>
        get() = emptyList()
}