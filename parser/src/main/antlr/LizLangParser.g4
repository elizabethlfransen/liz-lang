parser grammar LizLangParser;

options {
    tokenVocab=LizLangLexer;
}

program: expression EOF;

expression
    : OPEN_PARENTHESIS child=expression CLOSE_PARENTHESIS # ParenthesisExp
    | child=expression INCREMENT # PostIncrementExp
    | child=expression DECREMENT # PostDecrementExp
    | <assoc=right> INCREMENT child=expression # PreIncrementExp
    | <assoc=right> DECREMENT child=expression # PreDecrementExp
    | <assoc=right> MINUS child=expression # UnaryMinusExp
    | <assoc=right> PLUS child=expression # UnaryPlusExp
    | left=expression STAR right=expression  # MultiplyExp
    | left=expression PLUS right=expression # AddExp
    | literal # LiteralExp;


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