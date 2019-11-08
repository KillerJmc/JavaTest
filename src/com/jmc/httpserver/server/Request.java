package com.jmc.httpserver.server;

/**
 * 功能：请求
 */

import java.util.*;
import java.io.*;
import java.net.*;

public class Request
{
	//请求方式
	private String method;
	//请求资源
	private String url;
	//请求参数
	private Map<String,List<String>> parameterMapValues;
	//常量
	public static final String CRLF = "\r\n";
	public static final String BLANK = " ";
	//请求信息
	public String requestInfo;
	
	public Request() {
		method = "";
		url = "";
		parameterMapValues = new HashMap<String,List<String>>();
		requestInfo = "";
	}
	public Request(InputStream is) {
		this();
		//读取请求信息
		try {
			byte[] b = new byte[10240];
			int len = is.read(b);
			requestInfo = new String(b,0,len);
		} catch (Exception e) {
			return;
		}
		//分析请求信息
		analyseRequestInfo();
	}

	//分析请求信息
	private void analyseRequestInfo() {
		//检查是否为空
		if (null == requestInfo || (requestInfo = requestInfo.trim()).equals("")) {
			return;
		}
		
		//请求参数
		String paramString = "";
		
		//获取请求参数第一行
		String firstLine = requestInfo.substring(0,requestInfo.indexOf(CRLF));
		
		//斜杠的位置
		int idx = requestInfo.indexOf("/");
		method = requestInfo.substring(0,idx).trim();
		
		//获取请求资源信息
		String urlStr = firstLine.substring(idx,firstLine.indexOf("HTTP/")).trim();
		
		//如果为post方法
		if (method.equalsIgnoreCase("post")) {
			this.url= urlStr;
			paramString = requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
		} else if (method.equalsIgnoreCase("get")) {
			//如果包含请求参数
			if (urlStr.contains("?")) {
				String[] urlArr = urlStr.split("\\?");
				this.url = urlArr[0];
				paramString = urlArr[1];
			} else {
				this.url = urlStr;
			}
		}
		
		//不存在就返回
		if (requestInfo.equals("")) {
			return;
		}
	
		//把参数封装到Map
		packParams(paramString);
	}

	//封装参数
	private void packParams(String paramString) {
		//分割参数
		StringTokenizer token = new StringTokenizer(paramString , "&");
		while (token.hasMoreElements()) {
			String keyValue = token.nextToken();
			String[] keyValues = keyValue.split("=");
			//如果参数长度为1，如"uname="
			if (keyValues.length == 1) {
				keyValues = Arrays.copyOf(keyValues, 2);
				//第二项参数设为null
				keyValues[1] = null;
			}
			
			//转换成Map
			String key = keyValues[0].trim();
			String value = (null == keyValues[1]) ? null : decode(keyValues[1].trim(),"utf-8");
			
			//如果不包含这个参数名，就放入
			if (!parameterMapValues.containsKey(key)) {
				parameterMapValues.put(key, new ArrayList<String>());
			}
			
			List<String> values = parameterMapValues.get(key);
			values.add(value);
		}
	}
	
	//解析
	private String decode(String value, String code) {
		try {
			return URLDecoder.decode(value, code);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	//获取参数对应的多个值
	public String[] getParameterValues(String name) {
		List<String> values = null;
		if ((values = parameterMapValues.get(name)) == null) {
			return null;
		} else {
			//返回与values大小相当的数组
			return values.toArray(new String[values.size()]);
		}
	}
	
	//获取参数对应的1个值
	public String getParameterValue(String name) {
		String[] values = getParameterValues(name);
		if (null == values) {
			return null;
		} else {
			return values[0];
		}
	}
	
	//获取url
	public String getUrl() {
		return url;
	}
}
