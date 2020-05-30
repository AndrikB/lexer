package com.blagij;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Printer {
    private final List<Token> tokens;

    public Printer(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void printInHTML(String filename) {
        StringBuilder html = new StringBuilder(
                "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "\n" +
                        "    <title>Lexer c++ </title>\n" +
                        "\n" +
                        "<style>body{white-space: pre-wrap;}</style>\n" +
                        "</head>\n" +
                        "<body>");
        final String template = "<font color='#%s'>%s</font>";
        for (Token token : tokens) {
            html.append(String.format(template, token.getTokenType().getColor(), token.getValue()));
        }
        html.append("</body>\n" +
                "</html>");

        try {
            FileWriter myWriter = new FileWriter(filename+".html");
            myWriter.write(html.toString());
            myWriter.close();
            System.out.println("write correct");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
