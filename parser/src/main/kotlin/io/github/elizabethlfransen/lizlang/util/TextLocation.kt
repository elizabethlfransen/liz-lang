package io.github.elizabethlfransen.lizlang.util

import org.antlr.v4.runtime.Token

data class TextLocation(
    val line: Int,
    val charPositionInLine: Int
) {
    companion object {
        fun fromToken(token: Token): TextLocation {
            return TextLocation(token.line,token.charPositionInLine)
        }
    }
}
