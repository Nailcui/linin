package com.github.nailcui.linin.core.parser.ast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nailcui
 * @date 2020-04-24 22:35
 */
public class ListNode extends Node {
    private List<Node> list;

    @Override
    NodeType getType() {
        return NodeType.LIST;
    }

    public ListNode() {
        this.list = new ArrayList<>();
    }

    public List<Node> getList() {
        return list;
    }

    public void setList(List<Node> list) {
        this.list = list;
    }
}
