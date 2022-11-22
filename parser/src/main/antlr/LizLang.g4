grammar LizLang;

options {
    tokenVocab=LizLangLexer;
}

literal
    : intLiteral
    | doubleLiteral
    | floatLiteral
    | trueLiteral
    | falseLiteral
    | stringLiteral
    | charLiteral;
intLiteral: INTEGER;
floatLiteral: FLOAT;
trueLiteral: TRUE;
falseLiteral: FALSE;
doubleLiteral: DOUBLE;
stringLiteral: START_STRING STRING_CHARACTER+ CLOSE_STRING;
charLiteral: CHARACTER;