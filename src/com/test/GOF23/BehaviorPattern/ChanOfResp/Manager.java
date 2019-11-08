package com.test.GOF23.BehaviorPattern.ChanOfResp;

public class Manager extends Leader {
	public Manager(String name) {
		super(name);
	}

	@Override
	public void handleRequest(LeaveRequest request) {
		if (request.getLeaveDays() < 10) {
			System.out.println("员工：" + request.getEmpName()
				+ "请假，天数：" + request.getLeaveDays()
				+ "，理由：" + request.getReason());
			
			System.out.println("经理：" + this.name + "，审批通过！");
		} else {
			if (nextLeader != null) {
				this.nextLeader.handleRequest(request);
			}
		}
	}

}
