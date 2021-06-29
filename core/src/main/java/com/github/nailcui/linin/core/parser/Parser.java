package com.github.nailcui.linin.core.parser;

import com.github.nailcui.linin.core.parser.ast.*;
import com.github.nailcui.linin.core.parser.ast.*;
import com.github.nailcui.linin.core.parser.lex.BaseLex;
import com.github.nailcui.linin.core.parser.lex.Lexer;
import com.github.nailcui.linin.core.parser.lex.Token;
import com.github.nailcui.linin.core.parser.lex.TokenType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nailcui
 * @date 2020-05-10 15:13
 */
public class Parser {
    public static Node parse(String sql) {
        Lexer lexer = BaseLex.build(sql);
        Token token = lexer.next();
        if (token.getType().equals(TokenType.SELECT)) {
            return parseSelect(lexer);
        }
        return null;
    }

    private static SelectNode parseSelect(Lexer lexer) {
        SelectNode selectNode = new SelectNode();
        selectNode.setSelectList(parseSelectList(lexer));
        selectNode.setFrom(parseFrom(lexer));
        selectNode.setWhere(parseWhere(lexer));
        selectNode.setOrderBy(parseOrderBy(lexer));
        return selectNode;
    }

    /**
     * order by id
     * order by id asc
     * order by id desc
     * order by id, name asc
     * order by id, name desc
     * order by id desc, name asc
     * order by id desc, name desc
     */
    private static ListNode parseOrderBy(Lexer lexer) {
        Token token = lexer.next();
        if (!token.getType().equals(TokenType.ORDER)) {
            lexer.back();
        }
        token = lexer.next();
        if (!token.getType().equals(TokenType.BY)) {
            lexer.back();
            lexer.back();
        }
        ListNode listNode = new ListNode();
        while (true) {
            // 读取一整个条件
            Token token1 = lexer.next();
            if (token1 == null) {
                break;
            } else if (token1.getType().equals(TokenType.LIMIT)) {
                lexer.back();
                break;
            } else {
                // 判断是否有 desc asc
                Token token2 = lexer.next();
                if (token2 == null) {
                    lexer.back();
                    listNode.getList().add(new IdentifierNode(token1.getLiteral()));
                } else if (token2.getType().equals(TokenType.ASC)) {
                    OperateNode operateNode = new OperateNode();
                    operateNode.setOperator(Operator.ASC);
                    operateNode.getOperands().add(new IdentifierNode(token1.getLiteral()));
                    listNode.getList().add(operateNode);
                } else if (token2.getType().equals(TokenType.DESC)) {
                    OperateNode operateNode = new OperateNode();
                    operateNode.setOperator(Operator.DESC);
                    operateNode.getOperands().add(new IdentifierNode(token1.getLiteral()));
                    listNode.getList().add(operateNode);
                } else {
                    lexer.back();
                    listNode.getList().add(new IdentifierNode(token1.getLiteral()));
                }
            }
            Token nextToken = lexer.next();
            if (nextToken == null) {
                break;
            } else if (nextToken.getType().equals(TokenType.COMMA)) {
                continue;
            } else {
                lexer.back();
                break;
            }

        }
        return listNode;
    }

    private static ListNode parseSelectList(Lexer lexer) {
        ListNode listNode = new ListNode();
        Token token = lexer.next();
        if (token.getType().equals(TokenType.FUZZY)) {
            listNode.getList().add(new IdentifierNode(TokenType.FUZZY.getLiteral()));
            return listNode;
        }
        lexer.back();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(token.getLiteral());
        while (true) {
            Token token1 = lexer.next();
            if (token1.getType().equals(TokenType.COMMA)) {
                listNode.getList().add(new IdentifierNode(stringBuilder.toString()));
                stringBuilder = new StringBuilder();
            } else if (token1.getType().equals(TokenType.FROM)) {
                listNode.getList().add(new IdentifierNode(stringBuilder.toString()));
                lexer.back();
                return listNode;
            }
            stringBuilder.append(token1);
        }
    }

    private static Node parseFrom(Lexer lexer) {
        Token token = lexer.next();
        if (!token.getType().equals(TokenType.FROM)) {
            throw new RuntimeException("parseFrom but first token is: " + token.getLiteral());
        }
        Token tableName = lexer.next();
        return new IdentifierNode(tableName.getLiteral());
    }

    /**
     * 解析 where 语法段
     *
     * where 语法:
     *  where cond
     *  where cond;
     *  where cond group by
     *  where cond order by
     *  where cond limit
     *
     * cond 语法:
     *  id = 3
     *  id >= 3
     *  id between 3 and 5
     */
    private static ListNode parseWhere(Lexer lexer) {
        Token token = lexer.next();
        ListNode where = new ListNode();
        if (token == null) {
            return where;
        }
        if (!token.getType().equals(TokenType.WHERE)) {
            lexer.back();
            return where;
        }
        while (true) {
            // 读取一整个条件
            List<Token> tokens = new ArrayList<>();
            boolean inBetween = false;
            while (true) {
                // 读取一个token，判断是否要结束或者跳转到下一个token
                token = lexer.next();
                if (token == null) {
                    // 到达末尾
                    lexer.back();
                    where.getList().add(buildWhere(tokens));
                    return where;
                } else if (token.getType().equals(TokenType.GROUP)) {
                    // 到达 group 段
                    lexer.back();
                    where.getList().add(buildWhere(tokens));
                    return where;
                } else if (token.getType().equals(TokenType.LIMIT)) {
                    // 到达 limit 段
                    lexer.back();
                    where.getList().add(buildWhere(tokens));
                    return where;
                } else if (token.getType().equals(TokenType.ORDER)) {
                    // 到达 order 段
                    lexer.back();
                    where.getList().add(buildWhere(tokens));
                    return where;
                } else if (token.getType().equals(TokenType.SEMICOLON)) {
                    // 到达末尾
                    lexer.back();
                    where.getList().add(buildWhere(tokens));
                    return where;
                } else if (token.getType().equals(TokenType.BETWEEN)) {
                    // 到达 between 条件，标记下来
                    inBetween = true;
                    tokens.add(token);
                } else if (inBetween && token.getType().equals(TokenType.AND)) {
                    // 到达 between 条件中的 and
                    inBetween = false;
                    tokens.add(token);
                } else if (token.getType().equals(TokenType.AND)) {
                    // 本条件结束, 暂存本条件, 开启下一个条件
                    where.getList().add(buildWhere(tokens));
                    break;
                } else {
                    tokens.add(token);
                }
            }
        }
    }

    /**
     * 构造 where 语句中的单个条件
     * cond 语法:
     *  id = 3
     *  id >= 3
     *  id between 3 and 5
     */
    private static Node buildWhere(List<Token> tokens) {
        if (tokens.size() == 3) {
            // id = 3
            // id != 3
            // id < 3
            // id <= 3
            // id > 3
            // id >= 3
            // id like 'ab%'
            OperateNode operateNode = new OperateNode();
            operateNode.getOperands().add(new IdentifierNode(tokens.get(0).getLiteral()));
            operateNode.getOperands().add(new IdentifierNode(tokens.get(2).getLiteral()));
            if (tokens.get(1).getType().equals(TokenType.LT)) {
                // <
                operateNode.setOperator(Operator.LT);
            } else if (tokens.get(1).getType().equals(TokenType.LE)) {
                // <=
                operateNode.setOperator(Operator.LE);
            } else if (tokens.get(1).getType().equals(TokenType.EQ)) {
                // =
                operateNode.setOperator(Operator.EQ);
            } else if (tokens.get(1).getType().equals(TokenType.NE)) {
                // !=
                operateNode.setOperator(Operator.NE);
            } else if (tokens.get(1).getType().equals(TokenType.GT)) {
                // >
                operateNode.setOperator(Operator.GT);
            } else if (tokens.get(1).getType().equals(TokenType.GE)) {
                // >=
                operateNode.setOperator(Operator.GE);
            } else if (tokens.get(1).getType().equals(TokenType.LIKE)) {
                // >=
                operateNode.setOperator(Operator.LIKE);
            } else {
                throw new RuntimeException("build where condition, un support operator: " + tokens.get(1).getLiteral());
            }
            return operateNode;
        } else if (tokens.size() == 5) {
            if (tokens.get(1).getType().equals(TokenType.BETWEEN) && tokens.get(3).getType().equals(TokenType.AND)) {
                OperateNode operateNode = new OperateNode();
                operateNode.setOperator(Operator.BETWEEN);
                operateNode.getOperands().add(new IdentifierNode(tokens.get(0).getLiteral()));
                operateNode.getOperands().add(new IdentifierNode(tokens.get(2).getLiteral()));
                operateNode.getOperands().add(new IdentifierNode(tokens.get(4).getLiteral()));
                return operateNode;
            }
        }
        throw new RuntimeException("无法解析的where条件: " + tokens);
    }
}
