package com.test.gof23.behavior.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 目标对象
 */
public class Subject {
    protected List<Observer> list = new ArrayList<>();

    public void registerObserver(Observer... obss) {
        for (var obs : obss) {
            list.add(obs);
        }
    }

    public void removeObserver(Observer obs) {
        list.remove(obs);
    }

    /**
     * 通知所有观察者更新状态
     */
    public void notifyAllObservers() {
        for (var obs : list) {
            obs.update(this);
        }
    }
}
