grammar LizLang;

options {
    tokenVocab=LizLangLexer;
}

literal
    : numberLiteral
    | booleanLiteral
    ;
numberLiteral
    : intLiteral
    | doubleLiteral
    | floatLiteral
    ;
booleanLiteral: trueLiteral | falseLiteral;
intLiteral: INTEGER;
floatLiteral: FLOAT;
trueLiteral: TRUE;
falseLiteral: FALSE;
doubleLiteral: DOUBLE;