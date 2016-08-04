package com.sogou.map.android.com.sogou.map.android.util;

/**
 * Created by liudawei on 2016/8/1.
 */
public class NullUtils {
    /**
     * true 数据为空
     *@author liudawei
     *2015-6-2
     * @param o
     * @return
     */
    public static boolean isNull(Object o) {
        if (o == null) {
            return true;
        }

        if (o instanceof String) {
            return ((String) o).trim().length() == 0;
        }
        return false;
    }

    /**
     * true 数据不为空
     *@author liudawei
     *2015-6-2
     * @param o
     * @return
     */
    public static boolean isNotNull(Object o) {
        return !isNull(o);
    }
}
