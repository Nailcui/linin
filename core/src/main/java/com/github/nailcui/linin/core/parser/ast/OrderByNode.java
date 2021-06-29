package com.github.nailcui.linin.core.parser.ast;

import java.util.List;

/**
 * @author nailcui
 * @date 2020-05-10 19:44
 */
public class OrderByNode extends Node {

    private List<Node> orders;

    @Override
    NodeType getType() {
        return NodeType.ORDER_BY;
    }

    public List<Node> getOrders() {
        return orders;
    }

    public void setOrders(List<Node> orders) {
        this.orders = orders;
    }
}
