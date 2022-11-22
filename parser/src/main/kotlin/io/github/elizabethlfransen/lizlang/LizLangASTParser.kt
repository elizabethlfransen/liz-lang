package io.github.elizabethlfransen.lizlang

import io.github.elizabethlfransen.lizlang.parser.LizLangBaseVisitor
import io.github.elizabethlfransen.lizlang.parser.LizLangParser
import java.lang.Integer.parseInt

/**
 * This class is used to take the result from [LizLangParser] and generate a [AST] which can be used in [ParserVisitor]
 */
open class LizLangASTParser : LizLangBaseVisitor<AST>() {
    override fun visitLiteral(ctx: LizLangParser.LiteralContext): AST {
        // remove underscores
        var intLiteral = ctx.text.replace("_", "")
        var radix = 10
        // if hexadecimal number then remove prefix and change radix to 16
        if(intLiteral.startsWith("0x", ignoreCase = true)) {
            intLiteral = intLiteral.substring(2)
            radix = 16
        }
        // parse the integer
        val value = parseInt(intLiteral, radix)

        return buildASTFromContext(ctx) { text, start, stop -> IntLiteral(value, text, start, stop) }
    }
}

