package com.github.nailcui.linin.core.parser.ast;

/**
 * @author nailcui
 * @date 2020-05-10 15:32
 */
public enum NodeType {

    /**
     * identifier
     */
    IDENTIFIER,

    SELECT,

    CREATE,

    LIST,

    OPERATE,

    JOIN,

    ORDER_BY,

    ;

    NodeType() {
    }

}
