package io.github.elizabethlfransen.parser.util

import org.antlr.v4.runtime.BaseErrorListener
import org.antlr.v4.runtime.RecognitionException
import org.antlr.v4.runtime.Recognizer

class CollectingErrorListener : BaseErrorListener() {
    data class SyntaxErrorEntry(
        val recognizer: Recognizer<*, *>,
        val offendingSymbol: Any,
        val line: Int,
        val charPositionInLine: Int,
        val msg: String,
        val exception: RecognitionException
    ) {
        override fun toString(): String {
            return "line $line:$charPositionInLine $msg"
        }
    }
    private val _errors = mutableListOf<SyntaxErrorEntry>()
    val errors
        get() = _errors.toList()
    override fun syntaxError(
        recognizer: Recognizer<*, *>,
        offendingSymbol: Any,
        line: Int,
        charPositionInLine: Int,
        msg: String,
        e: RecognitionException
    ) {
        _errors += SyntaxErrorEntry(
            recognizer,
            offendingSymbol,
            line,
            charPositionInLine,
            msg,
            e
        )
    }

    override fun toString(): String {
        return errors.joinToString(
            transform = SyntaxErrorEntry::toString,
            separator = System.lineSeparator()
        )
    }
}