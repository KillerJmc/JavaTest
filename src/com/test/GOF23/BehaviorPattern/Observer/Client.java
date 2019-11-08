package com.test.GOF23.BehaviorPattern.Observer;

public class Client {
    public static void main(String[] args) {
        //目标对象
        var subject = new ConcreteSubject();

        //创建多个观察者
        var obs1 = new ConcreteObserver();
        var obs2 = new ConcreteObserver();
        var obs3 = new ConcreteObserver();

        //添加到subject的观察队伍中
        subject.registerObserver(obs1, obs2, obs3);

        //改变subject的状态
        subject.setState(3000);

        System.out.println(obs1.getMyState());
        System.out.println(obs2.getMyState());
        System.out.println(obs3.getMyState());

        System.out.println("--------------------------------");

        subject.setState(30);

        System.out.println(obs1.getMyState());
        System.out.println(obs2.getMyState());
        System.out.println(obs3.getMyState());
    }
}
