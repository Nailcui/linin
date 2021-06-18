package com.github.bioce.linin.core.parser.ast;

/**
 * @author nailcui
 * @date 2020-04-24 22:32
 */
public class IdentifierNode extends Node {
    private String identifier;
    public IdentifierNode(String identifier) {
        this.identifier = identifier;
    }

    @Override
    NodeType getType() {
        return NodeType.IDENTIFIER;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
