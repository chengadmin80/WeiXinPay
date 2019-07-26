package com.pay.wx.conf;

public class WXConfig {
	public static String KEY="";//API密钥 
    //网关  
    public static final String GATEWAYURL ="https://api.mch.weixin.qq.com/pay/unifiedorder"; 
    //公众号ID  
    public static String appid="";
    //公众账号ID  
    public static String mch_id="";
    //设备号  
    public static String device_info="";
    //随机字符串  
    public static String nonce_str="";  
    //签名  
    public static String sign="";  
    //签名类型  
    public static String sign_type="MD5";  
    //商品描述  
    public static String body="";  
    //商品详情  
    public static String detail="";  
    //附加数据  
    public static String attach="";  
    //商城订单号  
    public static String out_trade_no="";  
    //币种  
    public static String fee_type="CNY";  
    //交易金额（为分）例如12.53 应该（12.53*100） 放入当前值  
    public static int total_fee=0;  
    //客户IP  
    public static String spbill_create_ip="";  
    //交易起始时间yyyyMMddHHmmss  
    public static String time_start="";  
    //交易结束时间  
    public static String time_expire="";  
    //这个字段空着即可（订单优惠标记）  
    public static String goods_tag="";  
    //微信回调接口（重要）  
    public static String notify_url="";  
    //支付交易类型  
    public static String trade_type="NATIVE";  
    //支付订单号  
    public static String product_id="";  
    //指定支付方式是否限定用户用户信用卡 (这个可以空着)  
    public static String limit_pay="";  
    //扫描支付不需要可以空着  
    public static String openid="";
    //场景信息（可以空着） 
    public static String scene_info="";
}
