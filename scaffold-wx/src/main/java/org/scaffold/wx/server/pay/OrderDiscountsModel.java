package org.scaffold.wx.server.pay;

/**
 * 优惠功能
 */
public class OrderDiscountsModel {
   private int costPricce;// 订单原价	cost_price	int	否	1、商户侧一张小票订单可能被分多次支付，订单原价用于记录整张小票的交易金额。 2、当订单原价与支付金额不相等，则不享受优惠。3、该字段主要用于防止同一张小票分多次支付，以享受多次优惠的情况，正常支付订单不必上传此参数。 示例值：608800
   private String invoiceId;// 商品小票ID	invoice_id	string[1,32]	否	商家小票ID 示例值：微信123

   private GoodsDetailModel goodsDetail;

   public int getCostPricce() {
      return costPricce;
   }

   public void setCostPricce(int costPricce) {
      this.costPricce = costPricce;
   }

   public String getInvoiceId() {
      return invoiceId;
   }

   public void setInvoiceId(String invoiceId) {
      this.invoiceId = invoiceId;
   }

   public GoodsDetailModel getGoodsDetail() {
      return goodsDetail;
   }

   public void setGoodsDetail(GoodsDetailModel goodsDetail) {
      this.goodsDetail = goodsDetail;
   }
}
