package com.sogou.map.android.com.sogou.map.android.util;

/**
 * Created by Dawei on 2016/7/31.
 */
public interface XmlInterface {
    /**
     * 建立XML文档
     * @param fileName 文件全路径名称
     */
    public void createXml(String fileName);
    /**
     * 解析XML文档
     * @param fileName 文件全路径名称
     */
    public void parserXml(String fileName);
}
