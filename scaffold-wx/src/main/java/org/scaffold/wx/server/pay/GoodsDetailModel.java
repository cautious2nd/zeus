package org.scaffold.wx.server.pay;

/**
 * 单品信息
 */
public class GoodsDetailModel {
    private String merchantGoodsId;//  商户侧商品编码	merchant_goods_id	string[1,32]	是	由半角的大小写字母、数字、中划线、下划线中的一种或几种组成。 示例值：1246464644
    private String wechatpayGoodsId;// 微信支付商品编码	wechatpay_goods_id	string[1,32]	否	微信支付定义的统一商品编号（没有可不传）示例值：1001
    private String goodsName;//  商品名称	goods_name	string[1,256]	否	商品的实际名称 示例值：iPhoneX 256G
    private int quantity;//  商品数量	quantity	int	是	用户购买的数量 示例值：1
    private int unitPrice;//   商品单价	unit_price	int	是	单位为：分。如果商户有优惠，需传输商户优惠后的单价(例如：用户对一笔100元的订单使用了商场发的纸质优惠券100-50，则活动商品的单价应为原单价-50) 示例值：528800

    public String getMerchantGoodsId() {
        return merchantGoodsId;
    }

    public void setMerchantGoodsId(String merchantGoodsId) {
        this.merchantGoodsId = merchantGoodsId;
    }

    public String getWechatpayGoodsId() {
        return wechatpayGoodsId;
    }

    public void setWechatpayGoodsId(String wechatpayGoodsId) {
        this.wechatpayGoodsId = wechatpayGoodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }
}
