package io.github.elizabethlfransen.parser.util.tokendsl.matcher

import io.github.elizabethlfransen.lizlang.parser.LizLangLexer
import io.github.elizabethlfransen.parser.util.tokendsl.matcher.TokenMatcher
import org.antlr.v4.runtime.Token
import kotlin.test.fail

data class InverseTokenMatcher(val type: Int, val text: String?) : TokenMatcher {
    override fun assertMatches(tokenIndex: Int, token: Token) {
        val expectedTokenDisplayName = LizLangLexer.VOCABULARY.getDisplayName(type)
        val actualTokenDisplayName = LizLangLexer.VOCABULARY.getDisplayName(token.type)
        if(token.type != type) return
        if(text == null)
            fail("expected tokens[$tokenIndex] to not be $expectedTokenDisplayName but was $actualTokenDisplayName")
        if(token.text == text)
            fail("expected tokens[$tokenIndex] to not be $expectedTokenDisplayName('$text') but was $actualTokenDisplayName('${token.text}')")
    }
}