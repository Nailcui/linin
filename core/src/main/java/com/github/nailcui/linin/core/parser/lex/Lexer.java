package com.github.nailcui.linin.core.parser.lex;

/**
 * @author nailcui
 * @date 2020-05-02 23:34
 */
public interface Lexer {

    /**
     * 读取下一个token
     * @return token
     */
    Token next();

    /**
     * 回退
     */
    void back();
}
