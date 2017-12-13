package com.hj.utils;

import java.io.*;

/**
 * Created by hongjin on 2017/12/8.
 */
public class CloneUtil {
    private CloneUtil(){
        throw new AssertionError();
    }

//    @SuppressWarnings("unchecked")
    //克隆一个对象
    public static <T extends Serializable> T clone(T obj) throws Exception{
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bout);
        oos.writeObject(obj);

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bin);
        return (T) ois.readObject();

    }
}
