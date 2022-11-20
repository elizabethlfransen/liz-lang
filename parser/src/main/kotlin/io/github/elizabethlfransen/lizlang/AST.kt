package io.github.elizabethlfransen.lizlang

import io.github.elizabethlfransen.lizlang.util.TextLocation

sealed interface AST {
    val text: String
    val start: TextLocation

}