package com.sogou.map.android;

import com.sogou.map.android.com.sogou.map.android.com.sogou.map.android.reponse.LoginInfo;
import com.sogou.map.android.com.sogou.map.android.com.sogou.map.android.reponse.RestaurantResultEntity;
import com.sogou.map.android.com.sogou.map.android.com.sogou.map.android.reponse.Upload;
import com.sogou.map.android.com.sogou.map.android.com.sogou.map.android.reponse.VotingResultEntity;
import com.sogou.map.android.com.sogou.map.android.model.Restaurant;
import com.sogou.map.android.com.sogou.map.android.model.User;
import com.sogou.map.android.com.sogou.map.android.util.NullUtils;
import com.sogou.map.android.com.sogou.map.android.util.RestaurantManager;
import com.sogou.map.android.com.sogou.map.android.util.UserManger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liudawei on 2016/7/29.
 */
@Path("/vote")
public class Voting {
    public  static List<User> userList=new ArrayList<User>();
    public static List<Restaurant> restaurantList=new ArrayList<Restaurant>();
    private static final Logger logger = LogManager.getLogger(Voting.class.getName());
    @GET
    @Path("/login")
//    @Produces(MediaType.APPLICATION_JSON+";"+MediaType.CHARSET_PARAMETER+"=utf-8")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseDTO login(@QueryParam("name") String name,@QueryParam("pass") String pass,@QueryParam("deveid") String deveid){
        LoginInfo loginInfo=outhUser(name,pass);
        ResponseDTO responseDTO;
        if(loginInfo!=null){
            responseDTO=new ResponseDTO(0,"你成功了!",loginInfo);
        }else{
            responseDTO=new ResponseDTO(1,"登录失败",loginInfo);
        }
//        return (new Gson()).toJson(responseDTO);
        logger.debug(new MarkerManager.Log4jMarker("login"),"name="+name);
        return responseDTO;
    }

    private LoginInfo outhUser(String name,String pwd){
        LoginInfo loginInfo=null;
        if(userList!=null&&userList.size()>0){
            for (User user:userList) {
                if(user.getName().equals(name)&&user.getPwd().equals(pwd)){
                    loginInfo=new LoginInfo();
                    loginInfo.setName(name);
                    loginInfo.setReal_name(user.getReal_name());
                    loginInfo.setId(user.getId());
                    break;
                }
            }
        }
        return loginInfo;
    }

    @GET
    @Path("/uploadPicks")
    @Produces(MediaType.APPLICATION_JSON+";"+MediaType.CHARSET_PARAMETER+"=utf-8")
    public ResponseDTO uploadPicks(@QueryParam("name") String name,@QueryParam("dinner") String dinner,@QueryParam("launch") String launch,@QueryParam("deveid") String deveid){
        logger.debug(new MarkerManager.Log4jMarker("uploadPicks"),"name="+name+";dinner="+dinner+";launch="+launch);
        if(isValidUser(name)){
            Upload upload=new Upload();
            upload.setName(name);
            ResponseDTO responseDTO=new ResponseDTO(0,"提交成功",upload);
            String validDinnerToSave="";
            String validLaunchToSave="";
            if(!dinnerVotingForUser.containsKey(name)){
                upatePickLst(dinnerVotingResult,dinner);
                validDinnerToSave=dinner;
            }
            if(!launchVotingForUser.containsKey(name)){
                upatePickLst(launchVotingResult,launch);
                validLaunchToSave=launch;
            }
            saveUserVotingInfo(name,validLaunchToSave,validDinnerToSave);
            if(NullUtils.isNull(validLaunchToSave)&&NullUtils.isNull(validDinnerToSave)){
                return new ResponseDTO(2,"提交失败,重复提交",null);
            }
            return responseDTO;
        }else{
            return new ResponseDTO(1,"提交失败",null);
        }
    }

    /**
     * 累加本地的投票结果数据
     * @param pickMap
     * @param votings
     */
    private void upatePickLst(HashMap<String,Integer> pickMap,String votings){
        if(votings!=null){
            String[] voteResults=votings.split(",");
            for(int i=0;i<voteResults.length;i++){
                if(pickMap.containsKey(voteResults[i])){
                    int voteNo= pickMap.get(voteResults[i]);
                    pickMap.put(voteResults[i],++voteNo);
                }else{
                    pickMap.put(voteResults[i],1);
                }
            }
        }
    }

    @GET
    @Path("/getRestaurants")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseDTO getRestaurants(@QueryParam("name") String name,@QueryParam("deveid") String deveid){
        logger.debug(new MarkerManager.Log4jMarker("getRestaurants"),"name="+name);
        if(NullUtils.isNull(name)){
            return new ResponseDTO(2,"缺少参数-name","");
        }
        if(!isValidUser(name)){
            return new ResponseDTO(1,"获取餐厅列表失败","");
        }
        RestaurantResultEntity resultEntity=new RestaurantResultEntity();
        resultEntity.setRestaurants(this.restaurantList);
        if(restaurantList!=null){
            return new ResponseDTO(0,"获取餐厅列表成功",resultEntity);
        }
        return new ResponseDTO(1,"获取餐厅列表失败",resultEntity);
    }

    @GET
    @Path("/resume")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseDTO resumeVoting(@QueryParam("name") String name,@QueryParam("deveid") String deveid){
        logger.debug(new MarkerManager.Log4jMarker("resume"),"name="+name);
        if(NullUtils.isNull(name)){
            return new ResponseDTO(2,"缺少参数-name","");
        }
        if(!isValidUser(name)){
            return new ResponseDTO(1,"获取失败","");
        }
        doResume();
        if(restaurantList!=null){
            return new ResponseDTO(0,"重新投票设置完成",null);
        }
        return new ResponseDTO(1,"重新投票设置失败",null);
    }

    private void doResume(){
        dinnerVotingResult.clear();
        launchVotingResult.clear();
        launchVotingForUser.clear();
        dinnerVotingForUser.clear();
    }

    @GET
    @Path("/getCurrentPicks")
    @Produces(MediaType.APPLICATION_JSON+";"+MediaType.CHARSET_PARAMETER+"=utf-8")
    public ResponseDTO getCurrentPicks(@QueryParam("name") String name,@QueryParam("deveid") String deveid){
        logger.debug(new MarkerManager.Log4jMarker("getCurrentPicks"),"name="+name);
        if(NullUtils.isNull(name)){
            return new ResponseDTO(2,"缺少参数-name","");
        }
        if(!isValidUser(name)){
            return new ResponseDTO(1,"获取失败","");
        }
        VotingResultEntity resultEntity=new VotingResultEntity();
        resultEntity.setName(name);
        List<VotingResultEntity.RestaurantVotingRes> dinnerVotingResLst=new ArrayList<VotingResultEntity.RestaurantVotingRes>();
        List<VotingResultEntity.RestaurantVotingRes> launchVotingResLst=new ArrayList<VotingResultEntity.RestaurantVotingRes>();
        for (Restaurant restaurant:restaurantList) {
            VotingResultEntity.RestaurantVotingRes dinnerVotingRes=new VotingResultEntity.RestaurantVotingRes();
            VotingResultEntity.RestaurantVotingRes launchVotingRes=new VotingResultEntity.RestaurantVotingRes();
            dinnerVotingRes.setId(restaurant.getId());
            dinnerVotingRes.setName(restaurant.getName());
            launchVotingRes.setId(restaurant.getId());
            launchVotingRes.setName(restaurant.getName());
            if(dinnerVotingResult.containsKey(restaurant.getId())){
                dinnerVotingRes.setVote_num(dinnerVotingResult.get(restaurant.getId()));
            }
            if(launchVotingResult.containsKey(restaurant.getId())){
                launchVotingRes.setVote_num(launchVotingResult.get(restaurant.getId()));
            }
            dinnerVotingResLst.add(dinnerVotingRes);
            launchVotingResLst.add(launchVotingRes);
        }
        resultEntity.setDinnerVotingRes(dinnerVotingResLst);
        resultEntity.setLaunchVotingRes(launchVotingResLst);
        ResponseDTO responseDTO=new ResponseDTO(0,"获取成功",resultEntity);
        return responseDTO;
    }


    @GET
    @Path("/reloadUser")
    @Produces(MediaType.APPLICATION_JSON+";"+MediaType.CHARSET_PARAMETER+"=utf-8")
    public ResponseDTO reloadUser(@QueryParam("name") String name,@QueryParam("deveid") String deveid){
        if(NullUtils.isNull(name)){
            return new ResponseDTO(2,"缺少参数-name","");
        }
        if(!isValidUser(name)){
            return new ResponseDTO(1,"重新加载用户失败","");
        }
        Voting.userList= UserManger.getInstance().loadUserInfo();
        doResume();
        return new ResponseDTO(0,"重新加载用户成功","");
    }

    @GET
    @Path("/reloadRestaurant")
    @Produces(MediaType.APPLICATION_JSON+";"+MediaType.CHARSET_PARAMETER+"=utf-8")
    public ResponseDTO reloadRestaurant(@QueryParam("name") String name,@QueryParam("deveid") String deveid){
        if(!isValidUser(name)){
            return new ResponseDTO(1,"操作失败","");
        }
        Voting.restaurantList= RestaurantManager.getInstance().loadRestaurantInfo();
        doResume();
        return new ResponseDTO(0,"重新加载餐厅成功","");
    }

    private static HashMap<String,Integer> dinnerVotingResult =new HashMap<String, Integer>();
    private static HashMap<String,Integer> launchVotingResult=new HashMap<String, Integer>();

    /**
     * 是否是有效用户
     * @return
     */
    private boolean isValidUser(String name){
        boolean ret=false;
        if(userList.size()>0&&name!=null){
            for (User user:userList) {
                if(user.getName().equals(name)){
                    ret=true;
                    break;
                }
            }
        }
        return ret;
    }
    private static HashMap<String,String> dinnerVotingForUser =new HashMap<String, String>();
    private static HashMap<String,String> launchVotingForUser=new HashMap<String, String>();
    private void saveUserVotingInfo(String name,String launchVoting,String dinnerVoting){
        if(launchVoting!=null&&!launchVoting.equals(""))
            launchVotingForUser.put(name,launchVoting);
        if(dinnerVoting!=null&&!dinnerVoting.equals(""))
            dinnerVotingForUser.put(name,dinnerVoting);
    }
}
