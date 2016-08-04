package com.sogou.map.android.com.sogou.map.android.com.sogou.map.android.reponse;

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

    private String name;

    public List<RestaurantVotingRes> getDinnerVotingRes() {
        return dinnerVotingRes;
    }

    public void setDinnerVotingRes(List<RestaurantVotingRes> dinnerVotingRes) {
        this.dinnerVotingRes = dinnerVotingRes;
    }

    private List<RestaurantVotingRes> dinnerVotingRes;

    public List<RestaurantVotingRes> getLaunchVotingRes() {
        return launchVotingRes;
    }

    public void setLaunchVotingRes(List<RestaurantVotingRes> launchVotingRes) {
        this.launchVotingRes = launchVotingRes;
    }

    private List<RestaurantVotingRes> launchVotingRes;

    public VotingResultEntity(){
        if(dinnerVotingRes==null)
            dinnerVotingRes=new ArrayList<RestaurantVotingRes>();
        if(launchVotingRes==null)
            launchVotingRes=new ArrayList<RestaurantVotingRes>();
    }

    public static class RestaurantVotingRes{
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getVote_num() {
            return vote_num;
        }

        public void setVote_num(int vote_num) {
            this.vote_num = vote_num;
        }

        private String id;
        private int vote_num;
    }
}
