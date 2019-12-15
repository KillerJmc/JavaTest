/**
 * 状态模式
 * @Author Jmc
 *
 */
package com.test.gof23.behavior.state;

public class Client {
    public static void main(String[] args) throws Exception {
        RoomContext ctx = new RoomContext();
        ctx.setState(new FreeState());
        ctx.setState(new BookedState());
    }
}
