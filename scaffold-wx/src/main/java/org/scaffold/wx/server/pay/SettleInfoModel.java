package org.scaffold.wx.server.pay;

/**
 * 结算信息
 */
public class SettleInfoModel {

    private boolean profitSharing;// 是否指定分账	profit_sharing	boolean	否	是否指定分账 示例值：false

    public boolean isProfitSharing() {
        return profitSharing;
    }

    public void setProfitSharing(boolean profitSharing) {
        this.profitSharing = profitSharing;
    }
}
