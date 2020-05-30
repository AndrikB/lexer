package com.blagij;


import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        String filename = "test.txt";
        Lexer lexer = new Lexer(filename);
        lexer.parse();
        List<Token> tokens=lexer.getTokens();
        Printer printer=new Printer(tokens);
        printer.printInHTML(filename);
    }
}
