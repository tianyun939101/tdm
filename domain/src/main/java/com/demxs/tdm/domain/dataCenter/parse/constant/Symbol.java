package com.demxs.tdm.domain.dataCenter.parse.constant;

/**
 * @Auther: Jason
 * @Date: 2020/4/1 17:27
 * @Description:
 */
public enum Symbol {

    SPACE("空格"," "),
    TABLE("tab制表符","\t"),
    COLON("冒号","："),
    ;

    private String symbol;
    private String val;

    Symbol(String symbol, String val) {
        this.symbol = symbol;
        this.val = val;
    }

    public String getSymbol() {
        return symbol;
    }

    public Symbol setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public String getVal() {
        return val;
    }

    public Symbol setVal(String val) {
        this.val = val;
        return this;
    }
}
