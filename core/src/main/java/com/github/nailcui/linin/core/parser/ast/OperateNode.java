package com.github.nailcui.linin.core.parser.ast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nailcui
 * @date 2020-05-10 16:16
 */
public class OperateNode extends Node {

    private Operator operator;
    private List<Node> operands;

    public OperateNode() {
        this.operands = new ArrayList<>();
    }

    @Override
    NodeType getType() {
        return NodeType.OPERATE;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public List<Node> getOperands() {
        return operands;
    }

    public void setOperands(List<Node> operands) {
        this.operands = operands;
    }
}
