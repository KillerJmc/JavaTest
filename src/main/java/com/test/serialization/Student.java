package com.test.serialization;

import lombok.Data;

import java.io.*;

@Data
public class Student implements Externalizable {
    @Serial
    private static final long serialVersionUID = 114515L;

    private String name;
    private String age;
    private String score;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(this.name);
        out.writeUTF(this.age);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.name = in.readUTF();
        this.age = in.readUTF();
    }
}
