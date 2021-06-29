package com.github.nailcui.linin.core.parser.lex;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nailcui
 * @date 2020-05-02 23:37
 */
public enum TokenType {

    /* < */
    IDENT("main"),
    LT("<"),
    LE("<="),
    EQ("="),
    NE("!="),
    GT(">"),
    GE(">="),
    COMMA(","),
    FUZZY("*"),
    FROM("from"),
    JOIN("join"),
    WHERE("where"),
    SELECT("select"),
    DOT("."),
    LPAREN("("),
    RPAREN(")"),
    GROUP("group"),
    ORDER("order"),
    BY("by"),
    BETWEEN("between"),
    AND("and"),
    SEMICOLON(";"),
    LIMIT("limit"),
    LIKE("like"),
    ASC("asc"),
    DESC("desc"),
    ;
    private String literal;

    TokenType(String literal) {
        this.literal = literal;
    }

    private static Map<String, TokenType> tokenLiteralMaps = new HashMap<String, TokenType>();

    static {
        for (TokenType tokenType : TokenType.values()) {
            tokenLiteralMaps.put(tokenType.literal.toLowerCase(), tokenType);
            tokenLiteralMaps.put(tokenType.literal.toUpperCase(), tokenType);
        }
    }

    public static TokenType getTokenType(String literal) {
        return tokenLiteralMaps.getOrDefault(literal, IDENT);
    }

    public String getLiteral() {
        return literal;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }
}
