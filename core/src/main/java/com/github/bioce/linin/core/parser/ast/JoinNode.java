package com.github.bioce.linin.core.parser.ast;

/**
 * @author nailcui
 * @date 2020-05-10 19:21
 */
public class JoinNode extends Node {

    private Node left;
    private Node right;

    @Override
    NodeType getType() {
        return NodeType.JOIN;
    }
}
