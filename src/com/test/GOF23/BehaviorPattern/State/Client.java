/**
 * 状态模式
 * @Author Jmc
 *
 */
package com.test.GOF23.BehaviorPattern.State;

import com.jmc.decompile.ClassDecompile;
import com.test.GOF23.BehaviorPattern.Observer.Observer;

import static com.jmc.decompile.ClassDecompile.*;

public class Client {
    public static void main(String[] args) throws Exception {
        RoomContext ctx = new RoomContext();
        ctx.setState(new FreeState());
        ctx.setState(new BookedState());
    }
}
