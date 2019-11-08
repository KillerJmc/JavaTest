/*
 * 作者: Jmc
 * 时间: 2019.2.15
 * 功能: 异常处理
 */

package com.jmc.exception;

import java.io.*;

public class ExceptionHandler
{
	public static String showCaused(Exception e) {
		
		//把异常编进一个字符串		
		StringWriter sw = new StringWriter();		

		//设置用来接收的输出流
		e.printStackTrace(
			new PrintWriter(sw,true)
		);		

		//异常字符串
		String exception = sw.toString();

		//过滤文本
		if (exception.contains("Caused")) {
			//Caused位置
			int position = exception.indexOf("Caused");

			//去除Caused之前内容
			exception = exception.substring(position);

			//剩余部分第一个换行位置
			position = exception.indexOf("\n");

			//去除换行之后内容
			exception = exception.substring(0,position);

			//剩余部分Exception冒号位置之后的位置
			position = exception.lastIndexOf(": ") + 2;

			//在这个位置换行
			exception = exception.substring(0,position) + "\n"
				+ exception.substring(position) + "\n";
		}

		//返回处理完的异常字符串
		return exception;
	}
}
