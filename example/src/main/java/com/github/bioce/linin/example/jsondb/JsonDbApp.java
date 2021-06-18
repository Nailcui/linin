package com.github.bioce.linin.example.jsondb;

import com.github.bioce.linin.core.engine.Engine;
import com.github.bioce.linin.core.optimizer.Optimizer;
import com.github.bioce.linin.core.parser.Parser;
import com.github.bioce.linin.core.parser.ast.Node;
import com.github.bioce.linin.core.plan.Planner;
import com.github.bioce.linin.core.schema.Row;

import java.util.List;

/**
 * @author nailcui
 * @date 2020-06-06 22:58
 */
public class JsonDbApp {
    public static void main(String[] args) throws Exception {
        String sql = "select * from mydb";
        String dbName = "mydb";
        Node astNode = Parser.parse(sql);
        Planner planner = Optimizer.optimizer(astNode);
        Engine jsonEngine = new JsonEngine(dbName);
        List<Row> results = jsonEngine.exec(planner);
        for (Row row : results) {
            System.out.println(row);
        }
    }
}
