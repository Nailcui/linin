package com.github.bioce.linin.core.parser.lex;

/**
 * @author nailcui
 * @date 2020-05-10 11:56
 */
public class Token {
    /**
     * 开始位置
     */
    private int poi;

    /**
     * 字面量
     */
    private String literal;

    /**
     * token 类型
     */
    private TokenType type;

    private Token() {

    }

    public static Token build(String text, int poi) {
        Token token = new Token();
        token.literal = text.toLowerCase();
        token.poi = poi;
        token.type = TokenType.getTokenType(text);
        return token;
    }

    public int getPoi() {
        return poi;
    }

    public void setPoi(int poi) {
        this.poi = poi;
    }

    public String getLiteral() {
        return literal;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Token{" +
                "literal='" + literal + '\'' +
                '}';
    }
}
