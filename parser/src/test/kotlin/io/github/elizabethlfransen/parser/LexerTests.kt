package io.github.elizabethlfransen.parser

import io.github.elizabethlfransen.parser.util.*
import org.junit.jupiter.api.Test

class LexerTests {
    @Test
    fun `lexer should be able to identify an identifier`() {
        verifyTokens("test123\$_")
            .expectIdentifier("test123\$_")
            .expectEOF()
    }

    @Test
    fun `given an integer literal with only digits an integer token should be emitted`() {
       verifyTokens("123")
           .expectIntegerLiteral("123")
           .expectEOF()
    }

    @Test
    fun `given an integer literal with underscores an integer token should be emitted`() {
        verifyTokens("1_2_3")
            .expectIntegerLiteral("1_2_3")
            .expectEOF()
    }

    @Test
    fun `given a double with a decimal point and two number strings a double literal should be emitted`() {
        verifyTokens("123.456")
            .expectDoubleLiteral("123.456")
            .expectEOF()
    }

    @Test
    fun `given a double with an exponent and no decimal a double literal should be emitted`() {
        verifyTokens("123e1")
            .expectDoubleLiteral("123e1")
            .expectEOF()
    }

    @Test
    fun `given a double with an exponent and decimal a double literal should be emitted`() {
        verifyTokens("123.456e1")
            .expectDoubleLiteral("123.456e1")
            .expectEOF()
    }

    @Test
    fun `given a double with an 'f' suffix a float should be emitted`() {
        verifyTokens("1233.456e1f")
            .expectFloatLiteral("1233.456e1f")
            .expectEOF()
    }

    @Test
    fun `given an integer with a '0x' prefix then an integer token should be emitted`() {
        verifyTokens("0x123")
            .expectIntegerLiteral("0x123")
            .expectEOF()
    }

    @Test
    fun `given the literal 'true' then a true token should be emitted`() {
        verifyTokens("true")
            .expectTrueLiteral()
            .expectEOF()
    }

    @Test
    fun `given the literal 'false' then a false literal should be emitted`() {
        verifyTokens("false")
            .expectFalseLiteral()
            .expectEOF()
    }

    @Test
    fun `given the literal 'False' with different case then a false literal should be emitted`() {
        verifyTokens("False")
            .expectFalseLiteral()
            .expectEOF()
    }

    @Test
    fun `given the literal 'True' with different case then a true literal should be emitted`() {
        verifyTokens("True")
            .expectTrueLiteral()
            .expectEOF()
    }

    @Test
    fun `given a simple string literal then a string literal should be emitted`() {
        verifyTokens("\"test\"")
            .expectStartString()
            .expectStringCharacter("test")
            .expectCloseString()
            .expectEOF()
    }

    @Test
    fun `given a character literal then a character literal then a character should be emitted`() {
        verifyTokens("'t'")
            .expectCharacterLiteral("'t'")
            .expectEOF()
    }
}