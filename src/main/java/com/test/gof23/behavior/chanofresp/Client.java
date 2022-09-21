/**
 * 责任链模式（chain of responsibility）
 * @author Jmc
 */

package com.test.gof23.behavior.chanofresp;

public class Client {
	public static void main(String[] args) {
		//如果新加责任关系，只需新加类指向下一个责任人并重新组织关系即可
		//不用修改已有代码
		Leader a = new Director("张三");
		Leader b = new Manager("李四");
		Leader c = new GeneralManager("王五");
		
		//组织责任链关系（类似节点）
		a.setNextLeader(b);
		b.setNextLeader(c);
		
		//开始请假操作
		var req = new LeaveRequest("Tom", 50, "回英国老家探亲");
		a.handleRequest(req);
	}
}
