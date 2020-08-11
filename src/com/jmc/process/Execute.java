/*
 * 作者: Jmc
 * 时间: 2019.2.15
 * 功能: 执行系统内部命令
 */
 
package com.jmc.process;
 
import java.io.*;
import com.jmc.exception.*;

public class Execute 
{
	public static String execToStr(String command) {
		//新建异常返回字串符
		String exceptions = "";

		try {				
			//新建Process
			Process proc = Runtime.getRuntime().exec(command);
		
			//新建正常信息输入流
			BufferedReader normalMsg = new BufferedReader(
				new InputStreamReader(proc.getInputStream())
			);

			//临时字符串
			String temp = "";
			String exit = "";
			StringBuffer preserve = new StringBuffer();

			//若信息流里还有字符串
			while ((temp = normalMsg.readLine()) != null) {
				preserve.append(temp + "\n");
			}

			//关闭流
			normalMsg.close();

			//判断是否执行完
			if (proc.waitFor() != 0) {
				//新建错误信息输入流
				BufferedReader ErrorMsg = new BufferedReader(
					new InputStreamReader(proc.getErrorStream())
				);

				//若信息流里还有字符串
				while ((temp = ErrorMsg.readLine()) != null) {
					preserve.append(temp + "\n");
				}

				//关闭流
				ErrorMsg.close();

				exit = "\n结束值为: " + proc.exitValue() + "\n";
			}			

			//返回执行过程系统输出内容
			return preserve.toString() + exit;
		} catch(IOException e) {		
			//异常给字符串
			exceptions += "\nIO异常: " + "\n" + ExceptionHandler.showCaused(e);
		} catch(InterruptedException e) {		
			//异常给字符串
			exceptions += "\n终止异常: " + "\n" + ExceptionHandler.showCaused(e);
		}
		
		//返回异常
		return exceptions;
	}
    
    public static void exec(String command) {
        System.out.println(execToStr(command));
    }
}
