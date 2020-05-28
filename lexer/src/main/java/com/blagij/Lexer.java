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
    private final Utils utils = new Utils();
    private final List<Token> tokens;
    private State state;
    private int currentPos;
    private StringBuilder buffer;

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

    private String getBuffer(){
        return buffer.toString();
    }

    private void setState(State state) {
        this.state = state;
    }

    private void rollBack() {
        currentPos--;
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
                case SIMPLE_OPERATOR:
                    simpleOperator(c);
                    break;
                case SINGLE_MINUS:
                    singleMinus(c);
                    break;
                case SINGLE_PLUS:
                    singlePlus(c);
                    break;
                case SINGLE_LESS:
                    singleLess(c);
                    break;
                case SINGLE_GREATER:
                    singleGreater(c);
                    break;
                case SINGLE_PIPE:
                    singlePipe(c);
                    break;
                case SINGLE_AMPERSAND:
                    singleAmpersand(c);
                    break;
                case SINGLE_COLON:
                    singleColon(c);
                    break;
                case IDENTIFIER:
                    identifier(c);
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
        } else if (utils.isSeparatorCharacter(c)) {
            addToken(TokenType.SEPARATOR, c);
        } else if (c == '~' || c == '?') {
            addToken(TokenType.OPERATOR, c);
        } else if (c == '=' || c == '*' || c == '%' || c == '!' || c == '^') {
            addBufferAndSetState(c, SIMPLE_OPERATOR);
        } else if (c == '-') {
            addBufferAndSetState(c, SINGLE_MINUS);
        } else if (c == '+') {
            addBufferAndSetState(c, SINGLE_PLUS);
        } else if (c == '<') {
            addBufferAndSetState(c, SINGLE_LESS);
        } else if (c == '>') {
            addBufferAndSetState(c, SINGLE_GREATER);
        } else if (c == '|') {
            addBufferAndSetState(c, SINGLE_PIPE);
        } else if (c == '&') {
            addBufferAndSetState(c, SINGLE_AMPERSAND);
        } else if (c == ':') {
            addBufferAndSetState(c, SINGLE_COLON);
        } else if (Character.isJavaIdentifierStart(c)) {
            addBufferAndSetState(c, IDENTIFIER);
        }

    }

    private void singleSlash(char c) {
        if (c == '/') {
            addBufferAndSetState(c, SINGLE_LINE_COMMENT);
        } else if (c == '*') {
            addBufferAndSetState(c, MULTI_LINE_COMMENT);
        } else if (c == '=') {
            addBufferAndSetState(c, INITIAL);
            addToken(TokenType.OPERATOR);
        } else {
            addToken(TokenType.OPERATOR);
            setState(INITIAL);
            rollBack();
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
        } else if (c == '*') {
            addToBuffer(c);
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

    private void simpleOperator(char c) {
        if (c == '=') {
            addBufferAndSetState(c, INITIAL);
            addToken(TokenType.OPERATOR);
        } else {
            addToken(TokenType.OPERATOR);
            setState(INITIAL);
            rollBack();
        }
    }

    private void singleMinus(char c) {
        if (c == '-' || c == '=' || c == '>') {
            addBufferAndSetState(c, INITIAL);
            addToken(TokenType.OPERATOR);
        } else {
            addToken(TokenType.OPERATOR);
            setState(INITIAL);
            rollBack();
        }
    }

    private void singlePlus(char c) {
        if (c == '+' || c == '=') {
            addBufferAndSetState(c, INITIAL);
            addToken(TokenType.OPERATOR);
        } else {
            addToken(TokenType.OPERATOR);
            setState(INITIAL);
            rollBack();
        }
    }

    private void singleLess(char c) {
        if (c == '<') {
            addBufferAndSetState(c, SIMPLE_OPERATOR);
        } else if (c == '=') {
            addBufferAndSetState(c, INITIAL);
            addToken(TokenType.OPERATOR);
        } else {
            addToken(TokenType.OPERATOR);
            setState(INITIAL);
            rollBack();
        }
    }

    private void singleGreater(char c) {
        if (c == '>') {
            addBufferAndSetState(c, SIMPLE_OPERATOR);
        } else if (c == '=') {
            addBufferAndSetState(c, INITIAL);
            addToken(TokenType.OPERATOR);
        } else {
            addToken(TokenType.OPERATOR);
            setState(INITIAL);
            rollBack();
        }
    }

    private void singlePipe(char c) {
        if (c == '|' || c == '=') {
            addBufferAndSetState(c, INITIAL);
            addToken(TokenType.OPERATOR);
        } else {
            addToken(TokenType.OPERATOR);
            setState(INITIAL);
            rollBack();
        }
    }

    private void singleAmpersand(char c) {
        if (c == '&' || c == '=') {
            addBufferAndSetState(c, INITIAL);
            addToken(TokenType.OPERATOR);
        } else {
            addToken(TokenType.OPERATOR);
            setState(INITIAL);
            rollBack();
        }
    }

    private void singleColon(char c) {
        if (c == ':') {
            addBufferAndSetState(c, INITIAL);
            addToken(TokenType.OPERATOR);
        } else {
            addToken(TokenType.OPERATOR);
            setState(INITIAL);
            rollBack();
        }
    }

    private void identifier(char c) {
        if (Character.isJavaIdentifierPart(c)) {
            addToBuffer(c);
        }else if (utils.isKeyWord(getBuffer())){
            addToken(TokenType.KEYWORD);
            setState(INITIAL);
            rollBack();
        } else {
            addToken(TokenType.IDENTIFIER);
            setState(INITIAL);
            rollBack();
        }
    }

}
