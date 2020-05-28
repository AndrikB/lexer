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
    SINGLE_OPERATOR,// *,%,!,=,^
    SINGLE_MINUS,
    SINGLE_PLUS,
    SINGLE_LESS,
    DOUBLE_LESS,
    SINGLE_GREATER,
    DOUBLE_GREATER,
    SINGLE_PIPE,
    SINGLE_AMPERSAND
}
