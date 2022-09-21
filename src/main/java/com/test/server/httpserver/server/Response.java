package com.test.server.httpserver.server;

/**
 * 功能：响应
 */

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

public class Response
{
	//常量
	public static final String CRLF = "\r\n";
	public static final String BLANK = " ";
	//响应头
	private StringBuilder headInfo;
	//正文长度
	private int len;
	//正文
	private StringBuilder content;
	//输出流
	private BufferedWriter bw;
	//正文是否为超文本类型
	private boolean isHtml;
	
	//构造方法
	public Response() {
		headInfo = new StringBuilder();
		content = new StringBuilder();
		len = 0;
	}
	
	public Response(OutputStream os, boolean isHtml) {
		this();
		this.bw = new BufferedWriter(new OutputStreamWriter(os));
		this.isHtml = isHtml;
	}
	
	//构建响应头
	private void createHeadInfo(int code) {
		//1.HTTP协议版本，状态代码，描述
		headInfo.append("HTTP/1.1").append(BLANK).append(code).
			append(BLANK);
		switch (code) {
			case 200:
				headInfo.append("OK");
				break;
			case 404:
				headInfo.append("NOT FOUND");
				break;
			case 500:
				headInfo.append("SERVER ERROR");
				break;
		}		
		headInfo.append(CRLF);
		
		//2.响应头
		headInfo.append("Server:Jmc Server/1.1.6.0").append(CRLF);
		headInfo.append("Date:").append(new Date()).append(CRLF);
		//"text/html;"超文本
		headInfo.append("Content-type:text/")
			.append(isHtml? "html" : "plain").append(";charset=utf-8").append(CRLF);
		
		//正文长度
		headInfo.append("Content-Length=")
			.append(len).append(CRLF);

		//正文前空一行
		headInfo.append(CRLF);
	}
	
	//打印
	public Response print(String msg) {
		content.append(msg);
		len += content.toString().getBytes().length;
		return this;
	}
	
	//换行打印
	public Response println(String msg) {
		content.append(msg);
		content.append(CRLF);
		len += content.toString().getBytes().length;
		return this;
	}
	
	//导出到客户端
	public void pushToClient(int code) throws IOException {
		//如果出故障
		if (null == headInfo) code = 500;
		
		//响应头
		createHeadInfo(code);
		bw.append(headInfo.toString()); 
		
		//正文
		bw.append(content.toString());
		System.out.println("\n" + headInfo.toString() + content.toString());
		
		bw.flush();
		bw.close();
	}
}
