package com.pay.wx.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.alibaba.fastjson.JSONObject;
import com.pay.wx.conf.WXConfig;
import com.pay.wx.utils.HttpRequest;
import com.pay.wx.utils.WXCommonUtil;

/**
 * Servlet implementation class WXServlet
 */
@WebServlet("/uniorder.do")
public class WXServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		SortedMap<String, Object> params =new TreeMap<String, Object>();
		/**
		 * 接收参数
		 */
		String UID = request.getParameter("UID");//	用户ID号
		String UAmt = request.getParameter("UAmt");//金额	
		String Mid = request.getParameter("Mid");//	商品id(默认填1)
		String AID = request.getParameter("AID");//	应用APPID	
		String VID = request.getParameter("VID");//	版本号
		String SID = request.getParameter("SID");//	系统号
		String PID = request.getParameter("PID");//	手机型号
		String UDID = request.getParameter("UDID");//手机唯一标志码
		
		/*
		 * 统一下单关键参数
		 */
		params.put("appid", AID);//应用ID
		params.put("mch_id", WXConfig.mch_id);//商户号
		params.put("device_info", UDID);//商户号(取手机唯一标识)
		params.put("nonce_str", WXCommonUtil.getNonceStr());//支付金额
		
		params.put("sign_type", WXConfig.sign_type);//签名类型
		params.put("body", "APP学分充值测试");//商品描述  
		params.put("detail", "");//商品详情-非必须
		params.put("attach", "立语科技APP");//附加数据-非必须
		//内部订单号
		String strTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String out_trade_no = UID + AID + strTime;
		params.put("out_trade_no", out_trade_no);
		params.put("fee_type", WXConfig.fee_type);//货币类型，默认CNY
		params.put("total_fee", UAmt);//支付总金额
		//终端IP(调用微信支付API的机器IP)
		String spbill_create_ip = request.getLocalAddr();
		params.put("spbill_create_ip", spbill_create_ip);
		//交易起始时间
		params.put("time_start", strTime);
		//接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数
		params.put("notify_url",WXConfig.notify_url);
		//交易类型
		params.put("trade_type",WXConfig.trade_type);
		
		//签名（要包含所有参数，所以应该放在最后
		String sign = WXCommonUtil.createSign("UTF-8", params);
		params.put("sign", sign);
		
		/*本地保存订单*/
		//WxServer wxServ = new WxServer();
		//wxServ.saveOrder();
		
		//
		String reqXml = WXCommonUtil.getRequestXml(params);
		//测试用的数据
		/*
		reqXml = "<xml>\r\n" + 
				"   <appid>wx2421b1c4370ec43b</appid>\r\n" + 
				"   <attach>支付测试</attach>\r\n" + 
				"   <body>APP支付测试</body>\r\n" + 
				"   <mch_id>1230000109</mch_id>\r\n" + 
				"   <nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>\r\n" + 
				"   <notify_url>http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php</notify_url>\r\n" + 
				"   <out_trade_no>1415659990</out_trade_no>\r\n" + 
				"   <spbill_create_ip>14.23.150.211</spbill_create_ip>\r\n" + 
				"   <total_fee>1</total_fee>\r\n" + 
				"   <trade_type>APP</trade_type>\r\n" + 
				"   <sign>0CB01533B8C1EF103065174F50BCA001</sign>\r\n" + 
				"</xml> ";
		*/
		
		/*调用统一下单接口，返回的也是xml格式*/
		String resXml= HttpRequest.sendPost(WXConfig.GATEWAYURL, reqXml);
		/*需要将xml格式转化为json格式，传给APP端*/
		JSONObject resJson = null;
		try {
			resJson = WXCommonUtil.xml2Json(resXml);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		System.out.println(resJson.toJSONString());
		out.append(resJson.toJSONString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
