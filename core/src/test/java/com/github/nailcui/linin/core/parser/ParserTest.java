package com.github.nailcui.linin.core.parser;

import com.github.nailcui.linin.core.parser.ast.Node;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author nailcui
 * @date 2020-05-10 15:19
 */
public class ParserTest {

    @Test
    public void testSelectParse1() {
        String sql = "select * from t_name";
        Node astNode = Parser.parse(sql);
        Assert.assertNotNull(astNode);
    }

    @Test
    public void testSelectParse2() {
        String sql = "select * from t_name where id = 3";
        Node astNode = Parser.parse(sql);
        Assert.assertNotNull(astNode);
    }

    @Test
    public void testSelectParse3() {
        String sql = "select * from mydb.t_name where id = 3 and name like 'ab%' and t_name.name between 3 and 8";
        Node astNode = Parser.parse(sql);
        Assert.assertNotNull(astNode);
    }

    @Test
    public void testSelectOrderByParse1() {
        String sql = "select * from t_name order by id";
        Node astNode = Parser.parse(sql);
        Assert.assertNotNull(astNode);
    }

    @Test
    public void testSelectOrderByParse2() {
        String sql = "select * from t_name order by id asc";
        Node astNode = Parser.parse(sql);
        Assert.assertNotNull(astNode);
    }

    @Test
    public void testSelectOrderByParse3() {
        String sql = "select * from t_name order by id desc";
        Node astNode = Parser.parse(sql);
        Assert.assertNotNull(astNode);
    }

    @Test
    public void testSelectOrderByParse4() {
        String sql = "select * from t_name order by id, name desc";
        Node astNode = Parser.parse(sql);
        Assert.assertNotNull(astNode);
    }

    @Test
    public void testSelectOrderByParse5() {
        String sql = "select * from t_name order by id asc, name desc";
        Node astNode = Parser.parse(sql);
        Assert.assertNotNull(astNode);
    }

}
