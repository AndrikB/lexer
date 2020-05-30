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
        String style = ".tooltip {\n" +
                "  position: relative;\n" +
                "}\n" +
                "\n" +
                ".tooltip .tooltiptext {\n" +
                "  visibility: hidden;\n" +
                "  width: 120px;\n" +
                "  background-color: #555;\n" +
                "  color: #fff;\n" +
                "  text-align: center;\n" +
                "  border-radius: 6px;\n" +
                "  padding: 5px 0;\n" +
                "  position: absolute;\n" +
                "  z-index: 1;\n" +
                "  bottom: 125%;\n" +
                "  left: 50%;\n" +
                "  margin-left: -60px;\n" +
                "  opacity: 0;\n" +
                "  transition: opacity 0.3s;\n" +
                "}\n" +
                "\n" +
                ".tooltip .tooltiptext::after {\n" +
                "  content: \"\";\n" +
                "  position: absolute;\n" +
                "  top: 100%;\n" +
                "  left: 50%;\n" +
                "  margin-left: -8px;\n" +
                "  border-width: 5px;\n" +
                "  border-style: solid;\n" +
                "  border-color: #555 transparent transparent transparent;\n" +
                "}\n" +
                "\n" +
                ".tooltip:hover .tooltiptext {\n" +
                "  visibility: visible;\n" +
                "  opacity: 1;\n" +
                "}";
        StringBuilder html = new StringBuilder(
                "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "\n" +
                        "    <title>Lexer c++ </title>\n" +
                        "\n" +
                        "<style>" +
                        "body{white-space: pre-wrap;}" +
                        "span {margin-left: -7px;}" +
                        style +
                        "</style>\n" +
                        "</head>\n" +
                        "<body>");
        final String template = "<span class='tooltip'><font color='#%s'>%s</font>" +
                "  <span class='tooltiptext'>%s</span>" +
                "</span>";
        for (Token token : tokens) {
            token.setValue(token.getValue().replace("<", "&#60;"));//< in value is no html tag
            html.append(String.format(template, token.getTokenType().getColor(), token.getValue(), token.getTokenType()));
        }
        html.append("</body>\n" +
                "</html>");

        try {
            FileWriter myWriter = new FileWriter(filename + ".html");
            myWriter.write(html.toString());
            myWriter.close();
            System.out.println("write correct");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
