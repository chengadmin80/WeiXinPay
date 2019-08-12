package com.pay.wx.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
	* @Title: getNonceStr
	* @Description: TODO(获取随机字符串，weixin统一下单中的一个必须参数)
	* @param @return    参数
	* @return String    返回类型
	* @throws
	*/
	public static String getNonceStr() {
		Random random = new Random();
		return WX_MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "");
	}
	
	/**
     * xml转json
     * 
     * @param xmlStr
     * @return
     * @throws DocumentException
     */
    public static JSONObject xml2Json(String xmlStr) throws DocumentException {
        Document doc = DocumentHelper.parseText(xmlStr);
        JSONObject json = new JSONObject();
        dom4j2Json(doc.getRootElement(), json);
        return json;
    }
 
    /**
     * xml转json
     * 
     * @param element
     * @param json
     */
    public static void dom4j2Json(Element element, JSONObject json) {
        // 如果是属性
        for (Object o : element.attributes()) {
            Attribute attr = (Attribute) o;
            if (!isEmpty(attr.getValue())) {
                json.put("@" + attr.getName(), attr.getValue());
            }
        }
        List<Element> chdEl = element.elements();
        if (chdEl.isEmpty() && !isEmpty(element.getText())) {// 如果没有子元素,只有一个值
            json.put(element.getName(), element.getText());
        }
 
        for (Element e : chdEl) {// 有子元素
            if (!e.elements().isEmpty()) {// 子元素也有子元素
                JSONObject chdjson = new JSONObject();
                dom4j2Json(e, chdjson);
                Object o = json.get(e.getName());
                if (o != null) {
                    JSONArray jsona = null;
                    if (o instanceof JSONObject) {// 如果此元素已存在,则转为jsonArray
                        JSONObject jsono = (JSONObject) o;
                        json.remove(e.getName());
                        jsona = new JSONArray();
                        jsona.add(jsono);
                        jsona.add(chdjson);
                    }
                    if (o instanceof JSONArray) {
                        jsona = (JSONArray) o;
                        jsona.add(chdjson);
                    }
                    json.put(e.getName(), jsona);
                } else {
                    if (!chdjson.isEmpty()) {
                        json.put(e.getName(), chdjson);
                    }
                }
 
            } else {// 子元素没有子元素
                for (Object o : element.attributes()) {
                    Attribute attr = (Attribute) o;
                    if (!isEmpty(attr.getValue())) {
                        json.put("@" + attr.getName(), attr.getValue());
                    }
                }
                if (!e.getText().isEmpty()) {
                    json.put(e.getName(), e.getText());
                }
            }
        }
    }
 
    public static boolean isEmpty(String str) {
 
        if (str == null || str.trim().isEmpty() || "null".equals(str)) {
            return true;
        }
        return false;
    }
	
}
