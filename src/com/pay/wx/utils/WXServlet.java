package com.pay.wx.utils;

import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pay.wx.conf.WXConfig;
import com.pay.wx.http.HttpRequest;

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
		SortedMap<String, Object> parameters =new TreeMap<String, Object>();
		String reXml = WXCommonUtil.getRequestXml(parameters);
		reXml = "<xml>\r\n" + 
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
		String xml= HttpRequest.sendPost(WXConfig.GATEWAYURL, reXml);
		System.out.println(xml);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
