package com.blagij;

public enum State {
    INITIAL,
    SINGLE_SLASH,
    SINGLE_LINE_COMMENT,
    MULTI_LINE_COMMENT,
    STAR_IN_MULTI_LINE_COMMENT,
    STRING_LITERAL,
    STRING_LITERAL_SLASH,
    CHAR_LITERAL,
    CHAR_LITERAL_SLASH,
    SIMPLE_OPERATOR,// *,%,!,=,^,>>,<<
    SINGLE_MINUS,
    SINGLE_PLUS,
    SINGLE_LESS,
    SINGLE_GREATER,
    SINGLE_PIPE,
    SINGLE_AMPERSAND,
    SINGLE_COLON,
    IDENTIFIER,
    SINGLE_ZERO,
    HEX_NUMBER,
    BINARY_NUMBER,
    OCTAL_NUMBER,
    FLOAT_NUMBER,
    INT_NUMBER,
    FLOAT_NUMBER_E,
    DIRECTIVE,
    DIRECTIVE_SLASH,
}
