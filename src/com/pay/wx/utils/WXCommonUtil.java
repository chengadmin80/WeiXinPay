package com.pay.wx.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;

import com.pay.wx.conf.WXConfig;

public class WXCommonUtil {
	
	/**
	* @Title: createSign
	* @Description: TODO(生成签名，weixin统一下单的必须参数)
	* @param @param characterEncoding
	* @param @param parameters
	* @param @return    参数
	* @return String    返回类型
	* @throws
	*/
	public static String createSign(String characterEncoding, SortedMap<String, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}

		sb.append("key=" + WXConfig.KEY);// 最后加密时添加商户密钥，由于key值放在最后，所以不用添加到SortMap里面去，单独处理，编码方式采用UTF-8
		String sign = WX_MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
		return sign;

	}
	
	/**
	* @Title: getRequestXml
	* @Description: TODO(将请求参数转化为xml格式)
	* @param @param parameters 
	*  为SortedMap类型，因为weixin统一下单接口要求：在对参数进行签名时，要先对参数按unioncode从小到大进行排序。
	*  TreeMap 继承了SortedMap，对元素的存储正是按unioncode从小到大的顺序
	* @param @return    参数
	* @return String    返回类型
	* @throws
	*/
	public static String getRequestXml(SortedMap<String, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if ("sign".equalsIgnoreCase(k)) {
			} else if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k)) {
				sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
			} else {
				sb.append("<" + k + ">" + v + "</" + k + ">");
			}
		}
		sb.append("<" + "sign" + ">" + "<![CDATA[" + parameters.get("sign") + "]]></" + "sign" + ">");
		sb.append("</xml>");
		return sb.toString();
	}

	
	/**
	* @Title: genNonceStr
	* @Description: TODO(获取随机字符串，weixin统一下单中的一个必须参数)
	* @param @return    参数
	* @return String    返回类型
	* @throws
	*/
	public static String genNonceStr() {
		Random random = new Random();
		return WX_MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "");
	}
	
	
}
