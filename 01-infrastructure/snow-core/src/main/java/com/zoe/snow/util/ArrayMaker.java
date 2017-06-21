package com.zoe.snow.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ArrayMaker
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/6/20
 */
public class ArrayMaker<T> {
    private Class<T> type;

    public ArrayMaker(Class<T> type) {
        this.type = type;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        /*
         * Even though kind is stored as Class<T> , erasure means that it is actually just being stored as a Class, with
         * no parameter. So, when you do some thing with it, as in creating an array, Array.newInstance( ) doesn’t
         * actually have the type information that’s implied in kind; so it cannot produce the specific result, wh ich
         * must therefore be cast, which produces a warning that you cannot satisfy.
         */
        ArrayMaker<Type> am2 = new ArrayMaker<Type>(Type.class);
        System.out.println(Arrays.asList(am2.createArray(10)));
        System.out.println(Arrays.asList(am2.createList()));
    }

    @SuppressWarnings("unchecked")
    T[] createArray(int size) {
        return (T[]) Array.newInstance(type, size);
    }

    List<T> createList() {
        return new ArrayList<T>();
    }

}

class Type {
    @Override
    public String toString() {
        return "type";
    }
}