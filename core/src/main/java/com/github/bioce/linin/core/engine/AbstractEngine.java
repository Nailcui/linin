package com.github.bioce.linin.core.engine;

import com.github.bioce.linin.core.plan.Planner;
import com.github.bioce.linin.core.schema.Row;

import java.util.List;

/**
 * @author nailcui
 * @date 2020-06-06 23:27
 */
public abstract class AbstractEngine implements Engine {
    @Override
    public List<Row> exec(Planner planner) {
        return null;
    }
}
