package com.github.bioce.linin.example.jsondb;

import com.github.bioce.linin.core.engine.AbstractEngine;
import com.github.bioce.linin.core.engine.Engine;

/**
 * @author nailcui
 * @date 2020-06-06 23:00
 */
public class JsonEngine extends AbstractEngine {
    private final String name;

    public JsonEngine(String name) {
        this.name = name;
    }
}
