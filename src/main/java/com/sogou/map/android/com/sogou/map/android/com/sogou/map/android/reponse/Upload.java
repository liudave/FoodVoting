package com.sogou.map.android.com.sogou.map.android.com.sogou.map.android.reponse;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by liudawei on 2016/7/29.
 */
@XmlRootElement
public class Upload {
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
