grammar LizLang;

options {
    tokenVocab=LizLangLexer;
}

literal:
    floatLiteral
    | intLiteral
    | booleanLiteral
    ;
booleanLiteral: trueLiteral | falseLiteral;
intLiteral: INTEGER;
floatLiteral: FLOAT;
trueLiteral: TRUE;
falseLiteral: FALSE;