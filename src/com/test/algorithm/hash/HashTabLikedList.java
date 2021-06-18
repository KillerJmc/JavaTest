package com.test.algorithm.hash;

import com.jmc.lang.reflect.Reflects;
import com.test.algorithm.list.linked.impl.SinglyLinkedList;

public class HashTabLikedList<T> extends SinglyLinkedList<T> {
    public T findById(int id) {
        for (T t : this) {
            var currentId = Reflects.invokeMethod(t, "id", Integer.class);
            if (currentId == id) return t;
        }
        return null;
    }

    public void deleteById(int id) {
        for (T t : this) {
            var currentId = Reflects.invokeMethod(t, "id", Integer.class);
            if (currentId == id) {
                remove(t);
                return;
            }
        }
    }
}
