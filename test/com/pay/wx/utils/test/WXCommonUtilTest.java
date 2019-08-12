package com.pay.wx.utils.test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.pay.wx.utils.WXCommonUtil;

public class WXCommonUtilTest {
	
	@Test
	public void testCreateSign() {
		SortedMap<String, Object> parameters =new TreeMap<String, Object>();
		parameters.put("UID", "aa");
		parameters.put("UAmt", "100");
		parameters.put("Mid", "1");
		parameters.put("AID", "20");
		parameters.put("VID", "1");
		parameters.put("SID", "12");
		parameters.put("PID", "");
		parameters.put("UDID", "");
		String sign = WXCommonUtil.createSign("UTF-8", parameters);
		System.out.println(sign);
	}

	@Test
	public void testGetRequestXml() {
		System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
	}

	@Test
	public void testGetNonceStr() throws DocumentException {
		//System.out.println(WXCommonUtil.getNonceStr());
		String xml = "<xml>\r\n" + 
				"   <return_code><![CDATA[SUCCESS]]></return_code>\r\n" + 
				"   <return_msg><![CDATA[OK]]></return_msg>\r\n" + 
				"   <appid><![CDATA[wx2421b1c4370ec43b]]></appid>\r\n" + 
				"   <mch_id><![CDATA[10000100]]></mch_id>\r\n" + 
				"   <nonce_str><![CDATA[IITRi8Iabbblz1Jc]]></nonce_str>\r\n" + 
				"   <sign><![CDATA[7921E432F65EB8ED0CE9755F0E86D72F]]></sign>\r\n" + 
				"   <result_code><![CDATA[SUCCESS]]></result_code>\r\n" + 
				"   <prepay_id><![CDATA[wx201411101639507cbf6ffd8b0779950874]]></prepay_id>\r\n" + 
				"   <trade_type><![CDATA[APP]]></trade_type>\r\n" + 
				"</xml>";
		System.out.println(xml);
        JSONObject json = WXCommonUtil.xml2Json(xml);
        System.out.println(json);
	}

}
