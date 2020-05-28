package com.blagij;

import java.util.Arrays;
import java.util.List;

public class Utils {
    private final List<Character> escapeCharacters =
            Arrays.asList('\'', '"', '\\', 'n', 'r', 't', 'b', 'f', '0');


    private final List<Character> separatorCharacters =
            Arrays.asList('(', ')', '[', ']', '{', '}', ';', ',');

    private final List<String> keyWords = Arrays.asList("alignas", "alignof ", "and", "and_eq", "asm", "auto",
            "bitand", "bitor", "bool", "break", "case", "catch", "char", "char16_t", "char32_t", "class",
            "compl", "const", "constexpr", "const_cast", "continue", "decltype", "default", "delete", "do",
            "double", "dynamic_cast", "else", "enum", "explicit", "export", "extern", "false", "float", "for",
            "friend", "goto", "if", "inline", "int", "long", "mutable", "namespace", "new", "noexcept", "not",
            "not_eq", "nullptr", "operator", "or", "or_eq", "private", "protected", "public", "register", "reinterpret_cast",
            "return", "short", "signed", "sizeof", "static", "static_assert", "static_cast", "struct", "switch", "template",
            "this", "thread_local", "throw", "true", "try", "typedef", "typeid", "typename", "union", "unsigned", "using",
            "virtual", "void", "volatile", "wchar_t", "while", "xor", "xor_eq");

    public boolean isEscapeCharacter(Character c) {
        return escapeCharacters.contains(c);
    }

    public boolean isSeparatorCharacter(Character c) {
        return separatorCharacters.contains(c);
    }

    public boolean isSlashChar(Character c) {
        return isEscapeCharacter(c) || Character.isDigit(c) || c == 'x';
    }

    public boolean isKeyWord(String s) {
        return keyWords.contains(s);
    }

}
