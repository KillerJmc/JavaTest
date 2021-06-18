package com.test.server.httpserver.server;

import com.test.server.httpserver.servlet.Servlet;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class WebApp {
	public static ServletContext context;
	
	static {
		try {
			WebHandler handler = new WebHandler();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			parser.parse(getInputStream(Server.projectPath,"com/com.jmc/httpserver/WEB_INFO/web.xml"),handler);
			
			context = new ServletContext();
			
			//将List转成Map
			Map<String, String> servlet = context.getServlet();
			for (Entity entity : handler.getEntityList()) {
				//servlet-name servlet-class
				servlet.put(entity.getName(), entity.getClz());
			}
			
			Map<String, String> mapping = context.getMapping();
			for (Mapping map : handler.getMappingList()) {
				List<String> urls = map.getUrlPattern();
				for (String url : urls) {
					//url-pattern servlet-name
					mapping.put(url, map.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Servlet getServlet(String url) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if (null == url || (url = url.trim()).equals("")) {
			return null;
		}
		
		//返回的是直接对象
		//return context.getServlet().get(context.getMapping().get(url));
		
		//返回的是对象完整路径
		String name = context.getServlet().get(context.getMapping().get(url));
		//反射，动态创建对象，确保空构造存在
		return (Servlet)Class.forName(name).newInstance();
	}
	
	//按照不同设备获取输入流
	public static InputStream getInputStream(String projectPath, String filePath) throws FileNotFoundException {
		//如果是安卓用户
		if (new File("/sdcard/").exists()) {
			return new FileInputStream(projectPath + "/" + filePath);
		}
		
		return Thread.currentThread().getContextClassLoader()
			.getResourceAsStream(filePath);
	}
}
