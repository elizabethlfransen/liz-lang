package io.github.elizabethlfransen.lizlang

import io.github.elizabethlfransen.lizlang.ast.IdentifierASTNode
import io.github.elizabethlfransen.lizlang.ast.LizLangASTVisitor
import io.github.elizabethlfransen.lizlang.ast.LizLangFileASTNode
import io.github.elizabethlfransen.lizlang.internal.util.repeat
import javax.print.DocFlavor.STRING

class LizLangASTFormatter : LizLangASTVisitor<String> {
    private var indentation = 0
    private val indentationSize = 2
    private val indentationCharacter = ' '
    private val newLineCharacter = System.lineSeparator()!!

    private fun indent(func: () -> String): String {
        indentation += indentationSize
        val result = func()
        indentation -= indentationSize
        return result
    }

    override fun visitLizLangFile(node: LizLangFileASTNode): String {
        val indentation = indentationCharacter.repeat(this.indentation)
        val children = indent {
            node.children.joinToString(
                separator = newLineCharacter,
                prefix = newLineCharacter,
                postfix = newLineCharacter,
                transform = ::visitIdentifier
            )
        }
        return "${indentation}lizLangFile {$children}"
    }

    override fun visitIdentifier(node: IdentifierASTNode): String {
        return indentationCharacter.repeat(this.indentation) + node.identifier
    }
}