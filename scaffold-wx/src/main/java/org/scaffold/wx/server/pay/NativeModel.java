package org.scaffold.wx.server.pay;

public class NativeModel {

    private String appid;//   应用ID	appid	string[1,32]	是	body 由微信生成的应用ID，全局唯一。请求基础下单接口时请注意APPID的应用属性，例如公众号场景下，需使用应用属性为公众号的APPID 示例值：wxd678efh567hg6787
    private String mchid;//  直连商户号	mchid	string[1,32]	是	body 直连商户的商户号，由微信支付生成并下发。示例值：1230000109
    private String description;//   商品描述	description	string[1,127]	是	body 商品描述 示例值：Image形象店-深圳腾大-QQ公仔

    private String outTradeNo;// 商户订单号	out_trade_no	string[6,32]	是	body 商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一 示例值：1217752501201407033233368018

    private String timeExpire;//   交易结束时间	time_expire	string[1,64]	否	body 订单失效时间，遵循rfc3339标准格式，格式为yyyy-MM-DDTHH:mm:ss+TIMEZONE，yyyy-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，HH:mm:ss表示时分秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC8小时，即北京时间）。例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日 13点29分35秒。 示例值：2018-06-08T10:34:56+08:00
    private String attach;//  附加数据	attach	string[1,128]	否	body 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用，实际情况下只有支付完成状态才会返回该字段。 示例值：自定义数据
    private String notifyUrl;//   通知地址	notify_url	string[1,256]	是	body 通知URL必须为直接可访问的URL，不允许携带查询串，要求必须为https地址。 格式：URL 示例值：https://www.weixin.qq.com/wxpay/pay.php
    private String goodsTag;//   订单优惠标记	goods_tag	string[1,32]	否	body 订单优惠标记 示例值：WXG
    private boolean supportFapiao;//  电子发票入口开放标识	support_fapiao	boolean	否	body 传入true时，支付成功消息和支付详情页将出现开票入口。需要在微信支付商户平台或微信公众平台开通电子发票功能，传此字段才可生效。

    private OrderAmountModel amount;

    private OrderDiscountsModel detail;

    private SceneInfoModel sceneInfo;
    private SettleInfoModel settleInfo;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public boolean isSupportFapiao() {
        return supportFapiao;
    }

    public void setSupportFapiao(boolean supportFapiao) {
        this.supportFapiao = supportFapiao;
    }

    public OrderAmountModel getAmount() {
        return amount;
    }

    public void setAmount(OrderAmountModel amount) {
        this.amount = amount;
    }

    public OrderDiscountsModel getDetail() {
        return detail;
    }

    public void setDetail(OrderDiscountsModel detail) {
        this.detail = detail;
    }

    public SceneInfoModel getSceneInfo() {
        return sceneInfo;
    }

    public void setSceneInfo(SceneInfoModel sceneInfo) {
        this.sceneInfo = sceneInfo;
    }

    public SettleInfoModel getSettleInfo() {
        return settleInfo;
    }

    public void setSettleInfo(SettleInfoModel settleInfo) {
        this.settleInfo = settleInfo;
    }
}
