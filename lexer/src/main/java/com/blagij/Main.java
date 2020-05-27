package com.blagij;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        String filename = "test.txt";
        Lexer lexer = new Lexer(filename);
        lexer.parse();

    }
}
