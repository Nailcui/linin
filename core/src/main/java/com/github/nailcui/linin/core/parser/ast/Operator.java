package com.github.nailcui.linin.core.parser.ast;

/**
 * @author nailcui
 * @date 2020-05-10 16:17
 */
public enum Operator {
    LT("<"),
    LE("<="),
    EQ("="),
    NE("!="),
    GT(">"),
    GE(">="),
    ASC("asc"),
    DESC("desc"),
    LIKE("like"),
    BETWEEN("between"),
    ;
    private String literal;

    Operator(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }
}
