package io.github.elizabethlfransen.parser.util

import io.github.elizabethlfransen.lizlang.parser.LizLangLexer
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.Token

val String.tokens: List<Token>
    get() = this
        .let(CharStreams::fromString)
        .let(::LizLangLexer)
        .allTokens