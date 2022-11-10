package io.github.elizabethlfransen.parser.util

import assertk.Assert
import assertk.assertThat
import io.github.elizabethlfransen.lizlang.parser.LizLangLexer
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.TokenSource


fun Assert<String>.tokenize(): Assert<TokenSource> {
    return transform {actual ->
        CharStreams.fromString(actual)
            .let(::LizLangLexer)
    }
}

fun verifyTokens(actual: String) = assertThat(actual).tokenize()