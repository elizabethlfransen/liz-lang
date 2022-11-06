package io.github.elizabethlfransen.lizlang

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class ParserTests {
    @Test
    fun addShouldAdd() {
        assertThat(add(1, 1))
            .isEqualTo(2)
    }
}