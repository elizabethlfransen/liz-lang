package io.github.elizabethlfransen.parser.util.tokendsl

import io.github.elizabethlfransen.lizlang.parser.LizLangLexer
import io.github.elizabethlfransen.parser.util.tokendsl.matcher.SimpleTokenMatcher
import io.github.elizabethlfransen.parser.util.tokendsl.matcher.TokenMatcher


open class TokenDsl(private val emitEOF: Boolean) {
    protected val mutableTokenMatcherList = mutableListOf<TokenMatcher>()
    val tokenMatchers: List<TokenMatcher>
        get() {
            if(emitEOF) {
                val eofToken = SimpleTokenMatcher(LizLangLexer.EOF, null)
                return (mutableTokenMatcherList + eofToken).toList()
            }
            return mutableTokenMatcherList.toList()
        }
    val eof
        get() = token(LizLangLexer.EOF)
    val identifier
        get() = token(LizLangLexer.IDENTIFIER)

    open fun token(type: Int, text: String? = null) {
        mutableTokenMatcherList.add(SimpleTokenMatcher(type, text))
    }

    fun identifier(text: String) {
        token(LizLangLexer.IDENTIFIER, text)
    }

    fun integer(text: String) {
        token(LizLangLexer.INTEGER, text)
    }

    fun integer(value: Int) {
        integer(value.toString())
    }

    fun not (init: TokenDsl.() -> Unit) {
        val tokens = InverseTokenDsl(false)
            .apply(init)
            .tokenMatchers
        mutableTokenMatcherList += tokens
    }
}