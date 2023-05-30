package io.github.elizabethlfransen.lizlang.ast

class LizLangFileASTNode(override val children: List<IdentifierASTNode>) : ASTNode {
    override fun toString(): String {
        return "LizLang"
    }
}