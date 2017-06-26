package pers.xjh.test.util;

import java.io.File;
import java.util.Comparator;

/**
 * 根据文件的最后修改日期进行修改
 * Created by xjh on 2017/2/8.
 */
public class CompratorByLastModified implements Comparator<File> {
    @Override
    public int compare(File lhs, File rhs) {
        long diff = rhs.lastModified() - lhs.lastModified();
        if (diff > 0)
            return 1;
        else if (diff == 0)
            return 0;
        else
            return -1;
    }
}
