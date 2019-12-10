package com.test.Annotation;

//{} can be bypassed if strs has an only element.
@MyAnno(age = 12, per = Person.P1, anno2 = @MyAnno2, strs = {"sss", "ddd"})
@MyAnno3
public class Worker {
    @MyAnno3
    int i = 0;

    @MyAnno3
    public void show() {

    }
}
