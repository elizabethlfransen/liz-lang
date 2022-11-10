package io.github.elizabethlfransen.parser.util.tokendsl.matcher

import org.antlr.v4.runtime.Token

interface TokenMatcher {
    fun assertMatches(tokenIndex: Int, token: Token)
}