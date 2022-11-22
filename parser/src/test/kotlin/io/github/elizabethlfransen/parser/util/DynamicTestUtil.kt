package io.github.elizabethlfransen.parser.util

import org.junit.jupiter.api.DynamicTest

fun <T> Iterable<T>.mapToDynamicTest(
    displayName: (value: T) -> String,
    executable: (value: T) -> Unit
) = map { value: T ->
    DynamicTest.dynamicTest(displayName(value)) {
        executable(value)
    }
}
