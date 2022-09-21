package com.test.gof23.behavior.observer;

public class ConcreteObserver implements Observer {
    /**
     * myState需要跟目标对象State值保证一致！
     */
    private int myState;

    @Override
    public void update(Subject subject) {
        myState = ((ConcreteSubject)subject).getState();
    }

    public int getMyState() {
        return myState;
    }

    public void setMyState(int myState) {
        this.myState = myState;
    }
}
