lexer grammar LizLangLexer;


// Fragments
fragment IdentifierStart: [a-zA-Z_$];
fragment IdentifierPart: [a-zA-Z0-9_$];

// Tokens
IDENTIFIER: IdentifierStart IdentifierPart*;

// Skip whitespace and newlines
WS: [ \t\r\n] -> channel(HIDDEN);