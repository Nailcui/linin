package com.github.bioce.linin.core.parser;

import com.github.bioce.linin.core.parser.lex.BaseLex;
import com.github.bioce.linin.core.parser.lex.Lexer;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author nailcui
 * @date 2020-05-10 12:37
 */
public class BaseLexTest {

    @Test
    public void testNew() {
        String sql = "select * from `mydb`.`t_name` where id = 3 and t_name.name = 'jac' and create_time >= 3;";
        Lexer lexer = BaseLex.build(sql);
        Assert.assertNotNull(lexer);
    }

}
