package io.github.elizabethlfransen.parser.util.tokendsl

import io.github.elizabethlfransen.parser.util.tokendsl.matcher.InverseTokenMatcher

internal class InverseTokenDsl(emitEOF: Boolean) : TokenDsl(emitEOF) {
    override fun token(type: Int, text: String?) {
        mutableTokenMatcherList.add(InverseTokenMatcher(type, text))
    }
}