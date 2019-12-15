package com.test.gof23.behavior.state;

public interface State {
    void handle();
}

class FreeState implements State {
    @Override
    public void handle() {
        System.out.println("房间空闲，没人住！");
    }
}

class BookedState implements State {
    @Override
    public void handle() {
        System.out.println("房间已预订，别人不能订！");
    }
}

class CheckedState implements State {
    @Override
    public void handle() {
        System.out.println("房间已被入住，请勿打扰！");
    }
}

