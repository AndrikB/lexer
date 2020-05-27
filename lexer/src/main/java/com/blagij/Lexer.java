package com.blagij;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

import static com.blagij.State.*;

public class Lexer {
    private final String code;
    private State state;
    private int currentPos;
    private List<Token> tokens;
    private StringBuilder buffer;
    private Utils utils = new Utils();

    public Lexer(final String filePath) throws IOException {
        state = INITIAL;
        currentPos = 0;
        tokens = new LinkedList<>();
        buffer = new StringBuilder();

        byte[] bytes = Files.readAllBytes(new File(filePath).toPath());

        code = new String(bytes, StandardCharsets.UTF_8).replaceAll("\r", "");
    }

    private void addBufferAndSetState(char c, State state) {
        addToBuffer(c);
        setState(state);
    }

    private void addToBuffer(char c) {
        buffer.append(c);
    }

    private void addToken(TokenType tokenType, String value) {
        System.out.println(tokenType + " " + value);
        tokens.add(new Token(tokenType, value));
    }

    private void addToken(TokenType tokenType, char c) {
        System.out.println(tokenType + " " + (tokenType == TokenType.WHITESPACE ? String.valueOf((int) c) : c));
        tokens.add(new Token(tokenType, String.valueOf(c)));
    }

    private void addToken(TokenType tokenType) {
        addToken(tokenType, buffer.toString());
        buffer = new StringBuilder();
    }

    private void setState(State state) {
        this.state = state;
    }

    public void parse() {
        for (currentPos = 0; currentPos < code.length(); currentPos++) {
            char c = code.charAt(currentPos);
            switch (state) {
                case INITIAL:
                    initialState(c);
                    break;
                case SINGLE_SLASH:
                    singleSlash(c);
                    break;
                case SINGLE_LINE_COMMENT:
                    singleLineComment(c);
                    break;
                case MULTI_LINE_COMMENT:
                    multiLineComment(c);
                    break;
                case STAR_IN_MULTI_LINE_COMMENT:
                    starInMultiLineComment(c);
                    break;
                case STRING_LITERAL:
                    stringLiteral(c);
                    break;
                case STRING_LITERAL_SLASH:
                    stringLiteralSlash(c);
                    break;
                case CHAR_LITERAL:
                    charLiteral(c);
                    break;
                case CHAR_LITERAL_SLASH:
                    charLiteralSlash(c);
                    break;

                default:
                    break;
            }
        }

    }

    private void initialState(char c) {
        if (c == '/') {
            addBufferAndSetState(c, SINGLE_SLASH);
        } else if (c == '\"') {
            addBufferAndSetState(c, STRING_LITERAL);
        } else if (c == '\'') {
            addBufferAndSetState(c, CHAR_LITERAL);
        } else if (Character.isWhitespace(c)) {
            addToken(TokenType.WHITESPACE, c);
        } else if (utils.isBracketCharacter(c)) {
            addToken(TokenType.BRACKET, c);
        } else if (utils.isSeparatorCharacter(c)) {
            addToken(TokenType.SEPARATOR, c);
        }

    }

    private void singleSlash(char c) {
        if (c == '/') {
            addBufferAndSetState(c, SINGLE_LINE_COMMENT);
        } else if (c == '*') {
            addBufferAndSetState(c, MULTI_LINE_COMMENT);
        }
    }

    private void singleLineComment(char c) {
        if (c == '\n') {
            addToken(TokenType.COMMENT);
            addToken(TokenType.WHITESPACE, c);
            setState(INITIAL);
        } else {
            addToBuffer(c);
        }
    }

    private void multiLineComment(char c) {
        if (c == '*') {
            addBufferAndSetState(c, STAR_IN_MULTI_LINE_COMMENT);
        } else {
            addToBuffer(c);
        }
    }

    private void starInMultiLineComment(char c) {
        if (c == '/') {
            addBufferAndSetState(c, INITIAL);
            addToken(TokenType.COMMENT);
        } else {
            addBufferAndSetState(c, MULTI_LINE_COMMENT);
        }
    }

    private void stringLiteral(char c) {
        if (c == '\"') {
            addBufferAndSetState(c, INITIAL);
            addToken(TokenType.STRING_LITERAL);
        } else if (c == '\n') {
            addToken(TokenType.ERROR);
            addToken(TokenType.WHITESPACE, c);
            setState(INITIAL);
        } else if (c == '\\') {
            addBufferAndSetState(c, STRING_LITERAL_SLASH);
        } else {
            addToBuffer(c);
        }
    }

    private void stringLiteralSlash(char c) {
        if (!utils.isSlashChar(c)) {
            buffer.deleteCharAt(buffer.length() - 1);
        }
        addBufferAndSetState(c, STRING_LITERAL);
    }

    private void charLiteral(char c) {
        if (c == '\'') {
            addBufferAndSetState(c, INITIAL);
            addToken(TokenType.CHAR_LITERAL);
        } else if (c == '\n') {
            addToken(TokenType.ERROR);
            addToken(TokenType.WHITESPACE, c);
            setState(INITIAL);
        } else if (c == '\\') {
            addBufferAndSetState(c, CHAR_LITERAL_SLASH);
        } else {
            addToBuffer(c);
        }
    }

    private void charLiteralSlash(char c) {
        if (!utils.isSlashChar(c)) {
            buffer.deleteCharAt(buffer.length() - 1);
        }
        addBufferAndSetState(c, CHAR_LITERAL);
    }

}
