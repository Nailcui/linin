package com.github.nailcui.linin.core.parser.lex;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nailcui
 * @date 2020-05-03 00:07
 */
public class BaseLex implements Lexer {
    public static final String C_IGNORE = " \n`";
    public static final String C_SINGLE = ",;*={}().";
    public static final String C_IDENTITY = "_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String C_NUMBER   = "0123456789";

    private StringReader reader;
    private String sql;
    private List<Token> tokens;
    private int cur;

    private BaseLex(String sql) {
        this.reader = StringReader.build(sql);
        this.sql = sql;
        readTokens();
        aDotb();
        this.cur = 0;
    }

    public static Lexer build(String sql) {
        return new BaseLex(sql);
    }

    public Token next() {
        if (this.cur >= this.tokens.size()) {
            return null;
        }
        Token token = this.tokens.get(this.cur);
        this.cur++;
        return token;
    }

    public void back() {
        this.cur--;
    }

    private void readTokens() {
        this.tokens = new ArrayList<Token>();
        while (true) {
            int poi = this.reader.getPoi();
            char ch = this.reader.read();
            String chStr = String.valueOf(ch);
            if ((char) -1 == ch) {
                break;
            } else if (';' == ch) {
                break;
            } else if (C_IGNORE.contains(chStr)) {
            } else if (C_SINGLE.contains(chStr)) {
                this.tokens.add(Token.build(chStr, poi));
            } else if ('\'' == ch) {
                readText();
            } else if ('"' == ch) {
                readText();
            } else if ('<' == ch) {
                int poi2 = this.reader.getPoi();
                char ch2 = this.reader.read();
                if ('>' == ch2) {
                    this.tokens.add(Token.build("<>", poi));
                } else if ('=' == ch2) {
                    this.tokens.add(Token.build("<=", poi));
                } else {
                    this.reader.setPoi(poi2);
                    this.tokens.add(Token.build("<", poi));
                }
            } else if ('>' == ch) {
                int poi2 = this.reader.getPoi();
                char ch2 = this.reader.read();
                if ('=' == ch2) {
                    this.tokens.add(Token.build(">=", poi));
                } else {
                    this.reader.setPoi(poi2);
                    this.tokens.add(Token.build(">", poi));
                }
            } else if ('!' == ch) {
                int poi2 = this.reader.getPoi();
                char ch2 = this.reader.read();
                if ('=' == ch2) {
                    this.tokens.add(Token.build("<=", poi));
                } else {
                    throw new RuntimeException("BaseLex 解析出错，当前字符: !" + ch2);
                }
            } else if (C_IDENTITY.contains(chStr)) {
                this.reader.setPoi(poi);
                readText();
            } else if (C_NUMBER.contains(chStr)) {
                this.reader.setPoi(poi);
                readNumber();
            } else {
                throw new RuntimeException("BaseLex 解析出错，当前字符: " + ch);
            }
        }
    }

    private void readText() {
        int startPoi = this.reader.getPoi();
        while (true) {
            int poi = this.reader.getPoi();
            char ch = this.reader.read();
            String chStr = String.valueOf(ch);
            if (C_IDENTITY.contains(chStr)) {
                continue;
            } else if ('%' == ch) {
                continue;
            } else if ('\'' == ch) {
                this.tokens.add(Token.build(this.sql.substring(startPoi, poi), startPoi));
                return;
            } else if ('"' == ch) {
                this.tokens.add(Token.build(this.sql.substring(startPoi, poi), startPoi));
                return;
            } else {
                this.reader.setPoi(poi);
                this.tokens.add(Token.build(this.sql.substring(startPoi, poi), startPoi));
                return;
            }
        }
    }

    private void readNumber() {
        int startPoi = this.reader.getPoi();
        while (true) {
            int poi = this.reader.getPoi();
            char ch = this.reader.read();
            String chStr = String.valueOf(ch);
            if (C_NUMBER.contains(chStr)) {
                continue;
            } else if ('.' == ch) {
                continue;
            } else {
                this.reader.setPoi(poi);
                this.tokens.add(Token.build(this.sql.substring(startPoi, poi), startPoi));
                return;
            }
        }
    }

    private void aDotb() {
        List<Token> result = new ArrayList<>();
        for (int i = 1; i < this.tokens.size() - 1; i++) {
            if (this.tokens.get(i).getType().equals(TokenType.DOT)) {
                Token token1 = tokens.get(i - 1);
                Token token2 = tokens.get(i);
                Token token3 = tokens.get(i + 1);
                result.add(Token.build(token1.getLiteral() + token2.getLiteral() + token3.getLiteral(), token1.getPoi()));
                i += 2;
            } else {
                result.add(this.tokens.get(i - 1));
            }
        }
        result.add(this.tokens.get(this.tokens.size() - 2));
        result.add(this.tokens.get(this.tokens.size() - 1));
        this.tokens = result;
    }

    private static class StringReader {

        private String string;
        private int poi;

        private StringReader(String s) {
            this.string = s;
            this.poi = 0;
        }

        private static StringReader build(String s) {
            return new StringReader(s);
        }

        private char read() {
            if (this.poi == this.string.length()) {
                return (char) -1;
            }
            return string.charAt(poi++);
        }

        private int getPoi() {
            return poi;
        }

        private void setPoi(int poi) {
            this.poi = poi;
        }
    }

}
