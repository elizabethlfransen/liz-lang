package io.github.elizabethlfransen.parser.util.tokendsl.matcher

import io.github.elizabethlfransen.lizlang.parser.LizLangLexer
import io.github.elizabethlfransen.parser.util.tokendsl.matcher.TokenMatcher
import org.antlr.v4.runtime.Token
import kotlin.test.fail

data class SimpleTokenMatcher(val type: Int, val text: String?) : TokenMatcher {
    override fun assertMatches(tokenIndex: Int, token: Token) {
        val expectedTokenDisplayName = LizLangLexer.VOCABULARY.getDisplayName(type)
        val actualTokenDisplayName = LizLangLexer.VOCABULARY.getDisplayName(token.type)
        if(token.type != type)
            fail("expected tokens[$tokenIndex] to have type '$expectedTokenDisplayName' but was '$actualTokenDisplayName'")
        if(text != null && text != token.text)
            fail("expected tokens[$tokenIndex] to have text '$text' but was '${token.text}'")
    }
}