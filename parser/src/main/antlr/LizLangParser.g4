parser grammar LizLangParser;

options {
    tokenVocab=LizLangLexer;
}

program: expression EOF;

expression
    : OPEN_PARENTHESIS child=expression CLOSE_PARENTHESIS # ParenthesisExp
    | child=expression op=(INCREMENT|DECREMENT) # PostIncDecExp
    | <assoc=right> op=(PLUS|MINUS|EXCLAMATION_MARK|GRAVE|INCREMENT|DECREMENT) child=expression # UnaryExp
    | left=expression AS right=identifier # CastExpression
    | left=expression op=(STAR|FORWARD_SLASH|PERCENT) right=expression  # BinaryExp
    | left=expression op=(PLUS|MINUS) right=expression # BinaryExp
    | left=expression op=(SHIFT_LEFT|SHIFT_RIGHT|UNSIGNED_SHIFT_RIGHT) right=expression # BinaryExp
    | literal # LiteralExp;


identifier: IDENTIFIER;

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