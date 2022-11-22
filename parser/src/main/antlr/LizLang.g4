grammar LizLang;

options {
    tokenVocab=LizLangLexer;
}

literal
    : intLiteral
    | doubleLiteral
    | floatLiteral
    | trueLiteral
    | falseLiteral;
intLiteral: INTEGER;
floatLiteral: FLOAT;
trueLiteral: TRUE;
falseLiteral: FALSE;
doubleLiteral: DOUBLE;