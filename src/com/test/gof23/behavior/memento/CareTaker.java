package com.test.gof23.behavior.memento;

/**
 * 负责人类（负责管理备忘录对象）
 * CareTaker（照顾者）
 */
public class CareTaker {
    private EmpMemento memento;
//    private List<EmpMemento> list = new ArrayList<>();

    public EmpMemento getMemento() {
        return memento;
    }

    public void setMemento(EmpMemento memento) {
        this.memento = memento;
    }
}
