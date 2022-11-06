package io.github.elizabethlfransen.parser.util

import io.github.elizabethlfransen.lizlang.parser.LizLangLexer

data class TokenMatcher(val type: Int, val text: String?)

class TokenDsl(private val emitEOF: Boolean) {
    private val _tokens = mutableListOf<TokenMatcher>()
    val tokenMatchers: List<TokenMatcher>
        get() {
            if(emitEOF) {
                val eofToken = TokenMatcher(LizLangLexer.EOF, null)
                return (_tokens + eofToken).toList()
            }
            return _tokens.toList()
        }
    val eof
        get() = token(LizLangLexer.EOF)

    fun token(type: Int, text: String? = null) {
        _tokens.add(TokenMatcher(type, text))
    }

    fun identifier(text: String) {
        token(LizLangLexer.Identifier, text)
    }
}