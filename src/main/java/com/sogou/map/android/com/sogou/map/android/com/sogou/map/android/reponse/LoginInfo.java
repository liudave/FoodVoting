package com.sogou.map.android.com.sogou.map.android.com.sogou.map.android.reponse;


import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by liudawei on 2016/7/29.
 */
@XmlRootElement
public class LoginInfo {
    private String name;
    private String real_name;
    private String id;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
