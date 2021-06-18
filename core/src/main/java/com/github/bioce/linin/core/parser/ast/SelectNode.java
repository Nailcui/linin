package com.github.bioce.linin.core.parser.ast;


/**
 * @author nailcuicui
 * @date 2020-04-24 22:31
 */
public class SelectNode extends Node {
    private ListNode selectList;
    private Node from;
    private ListNode where;
    private Node having;
    private ListNode groupBy;
    private ListNode orderBy;

    @Override
    NodeType getType() {
        return NodeType.SELECT;
    }

    public ListNode getSelectList() {
        return selectList;
    }

    public void setSelectList(ListNode selectList) {
        this.selectList = selectList;
    }

    public Node getFrom() {
        return from;
    }

    public void setFrom(Node from) {
        this.from = from;
    }

    public ListNode getWhere() {
        return where;
    }

    public void setWhere(ListNode where) {
        this.where = where;
    }

    public Node getHaving() {
        return having;
    }

    public void setHaving(Node having) {
        this.having = having;
    }

    public ListNode getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(ListNode groupBy) {
        this.groupBy = groupBy;
    }

    public ListNode getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(ListNode orderBy) {
        this.orderBy = orderBy;
    }
}
