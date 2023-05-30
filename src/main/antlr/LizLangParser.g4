parser grammar LizLangParser;
options {
    tokenVocab=LizLangLexer;
}

lizLangFile:
    identifier* EOF;
identifier:
    IDENTIFIER;