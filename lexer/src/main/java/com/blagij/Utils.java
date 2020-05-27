package com.blagij;

import java.util.Arrays;
import java.util.List;

public class Utils {
    private final List<Character> escapeCharacters =
            Arrays.asList('\'', '"', '\\', 'n', 'r', 't', 'b', 'f', '0');

    private final List<Character> bracketCharacters =
            Arrays.asList('(', ')', '[',']','{','}');

    private final List<Character> separatorCharacters =
            Arrays.asList(';',',',':');

    public boolean isEscapeCharacter(Character c) {
        return escapeCharacters.contains(c);
    }

    public boolean isBracketCharacter(Character c){
        return bracketCharacters.contains(c);
    }

    public boolean isSeparatorCharacter(Character c){
        return separatorCharacters.contains(c);
    }

    public boolean isSlashChar(Character c){
        return isEscapeCharacter(c) || Character.isDigit(c) || c=='x';
    }
}
