package com.test.gof23.behavior.state;

/**
 * 房间对象
 * 这个类相当于银行账户（根据金额不同，切换状态--普通账户，白金账户）
 */
public class RoomContext {
    private State state;

    public void setState(State s) {
        System.out.println("修改状态！");
        this.state = s;
        state.handle();
    }

}
