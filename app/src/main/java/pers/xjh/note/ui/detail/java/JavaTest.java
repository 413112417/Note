package pers.xjh.note.ui.detail.java;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by XJH on 2017/5/19.
 */

public class JavaTest {

    public static void main(String[] args) {
        try {
            ArrayList<Integer> list = new ArrayList<>();
            Method method = list.getClass().getMethod("add", Object.class);
            method.invoke(list, "Java反射机制实例。");
            System.out.println(list.get(0));
        } catch (Exception e) {

        }
    }
}
