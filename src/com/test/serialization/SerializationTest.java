package com.test.serialization;

import com.test.main.Tools;

import java.io.*;

public class SerializationTest {
    public static void main(String[] args) throws Exception {
        //backup();
        recover();
    }

    private static void backup() throws Exception {
        var path = Tools.getCwd(SerializationTest.class) + "A.bak";
        var oos = new ObjectOutputStream(new FileOutputStream(path));
        for (int i = 0; i < 10; i++) {
            oos.writeObject(new A(i));
        }
        oos.close();
    }

    private static void recover() throws Exception {
        var path = Tools.getCwd(SerializationTest.class) + "A.bak";
        var ois = new ObjectInputStream(new FileInputStream(path));
        for (int i = 0; i < 10; i++) {
            System.out.println(ois.readObject());
        }
        ois.close();
    }

}

class A implements Serializable {
    private int id;

    public A(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "A{" +
                "id=" + id +
                '}';
    }
}
