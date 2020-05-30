package com.blagij;

public enum TokenType {
    COMMENT("A7A6A7"),
    WHITESPACE("FFFFFF"),
    KEYWORD("0000FF"),
    SEPARATOR("41B4D6"),
    OPERATOR("000000"),
    IDENTIFIER("8F0689"),
    NUMBER_LITERAL("FF9E00"),
    STRING_LITERAL("116C2C"),
    CHAR_LITERAL("13FF57"),
    ERROR("FF0000"),
    DIRECTIVE("777777");

    private final String color;

    TokenType(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

}
