package com.test.NewFeatures.NewDate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatThreadLocal {
	//ThreadLocal为每个线程提供了一个变量副本 是空间换时间 
	//实现访问并行化 对象独享化
	private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMdd");
		};
	};
	
	public static Date convert(String src) throws ParseException {
		//获取初始变量的值
		//format: 按指定的目标格式把Date对象转换为String
		//parse: 按指定的源格式把String转换为Date对象
		return df.get().parse(src);
	}
}
