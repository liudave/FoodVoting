package com.sogou.map.android;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liudawei on 2016/7/29.
 */
@XmlRootElement
public class VotingResultEntity {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HashMap<String, String>> getDinnerLst() {
        return dinner;
    }

    public void setDinnerLst(List<HashMap<String, String>> dinnerLst) {
        VotingResultEntity.dinner = dinnerLst;
    }

    public List<HashMap<String, String>> getLunchLst() {
        return launch;
    }

    public void setLunchLst(List<HashMap<String, String>> lunchLst) {
        VotingResultEntity.launch = lunchLst;
    }

    private String name;
    private static List<HashMap<String,String>> dinner;
    private static List<HashMap<String,String>> launch;

    public VotingResultEntity(){
        if(dinner ==null)
            dinner =new ArrayList<HashMap<String, String>>();
        if(launch ==null)
            launch =new ArrayList<HashMap<String, String>>();
    }
}
