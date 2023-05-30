package io.github.elizabethlfransen.lizlang.internal.util

fun Char.repeat(times: Int): String {
    return String(CharArray(times) { this })
}