package org.scaffold.wx.server.pay;

public class OrderAmountModel {
    private int total;//订单总金额，单位为分。示例值：100
    private String currency;// CNY：人民币，境内商户号仅支持人民币。示例值：CNY

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
