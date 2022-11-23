grammar LizLang;

options {
    tokenVocab=LizLangLexer;
}

expression
    : left=expression MULTIPLICATION right=expression  # multiplicationExpression
    | literal # literalExpression;


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