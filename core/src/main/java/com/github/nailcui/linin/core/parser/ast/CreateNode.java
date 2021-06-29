package com.github.nailcui.linin.core.parser.ast;

/**
 * @author nailcui
 * @date 2020-05-10 23:06
 */
public class CreateNode extends Node {
    @Override
    NodeType getType() {
        return NodeType.CREATE;
    }
}
