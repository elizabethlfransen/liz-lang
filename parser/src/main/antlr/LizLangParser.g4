parser grammar LizLangParser;

options {
    tokenVocab=LizLangLexer;
}

program: expression EOF;

expression
    : OPEN_PARENTHESIS child=expression CLOSE_PARENTHESIS # ParenthesisExp
    | child=expression INCREMENT # PostIncrementExp
    | child=expression DECREMENT # PostDecrementExp
    | INCREMENT child=expression # PreIncrementExp
    | DECREMENT child=expression # PreDecrementExp
    | MINUS child=expression # UnaryMinusExp
    | PLUS child=expression # UnaryPlusExp
    | EXCLAMATION_MARK child=expression # LogicalNotExp
    | GRAVE child=expression # BitwiseComplementExp
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