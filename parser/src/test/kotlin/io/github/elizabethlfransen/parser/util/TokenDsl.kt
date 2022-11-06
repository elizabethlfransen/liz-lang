package io.github.elizabethlfransen.parser.util

import io.github.elizabethlfransen.lizlang.parser.LizLangLexer

data class TokenMatcher(val type: Int, val text: String?)

class TokenDsl {
    private val _tokens = mutableListOf<TokenMatcher>()
    val tokenMatchers
        get() = _tokens.toList()

    fun token(type: Int, text: String? = null) {
        _tokens.add(TokenMatcher(type, text))
    }

    fun identifier(text: String) {
        token(LizLangLexer.Identifier, text)
    }

    fun eof() {
        token(LizLangLexer.EOF)
    }
}