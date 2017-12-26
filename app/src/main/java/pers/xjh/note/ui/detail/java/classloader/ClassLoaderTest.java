package pers.xjh.note.ui.detail.java.classloader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xjh on 17-12-26.
 */

public class ClassLoaderTest {

    public static void main(String[] args) {

        ClassLoader loader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";

                    InputStream is = getClass().getResourceAsStream(fileName);

                    if (is == null) {
                        return super.loadClass(name);
                    }

                    byte[] b = new byte[is.available()];

                    is.read(b);

                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        try {
            Object object = loader.loadClass("pers.xjh.note.ui.detail.java.C").newInstance();
            System.out.print(object.getClass());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
