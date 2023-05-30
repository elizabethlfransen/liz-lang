package io.github.elizabethlfransen.lizlang.ast

interface LizLangASTVisitor<T> {
    fun visitIdentifier(node: IdentifierASTNode): T {
        throw NotImplementedError("Visitor path not implemented")
    }

    fun visitLizLangFile(node: LizLangFileASTNode): T {
        throw NotImplementedError("Visitor path not implemented")
    }
}