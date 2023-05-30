package io.github.elizabethlfransen.lizlang.ast

interface ASTNode {
    val children: List<ASTNode>
}