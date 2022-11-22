grammar LizLang;

options {
    tokenVocab=LizLangLexer;
}

literal: floatLiteral | intLiteral;
intLiteral: INTEGER;
floatLiteral: FLOAT;