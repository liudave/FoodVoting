package com.sogou.map.android.com.sogou.map.android.util;

import com.sogou.map.android.com.sogou.map.android.model.Restaurant;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Dawei on 2016/7/31.
 */
public class RestaurantManager {
    private static String xmlFileName=UserManger.class.getResource("/").getPath()+"/config/restaurant.xml";;
    private static RestaurantManager restaurantManager;

    public static RestaurantManager getInstance(){
        if(restaurantManager==null){
            restaurantManager=new RestaurantManager();
        }
        return restaurantManager;
    }

    /**
     * 初始化餐厅信息
     * @return
     */
    public List<Restaurant> loadRestaurantInfo(){
        List<Restaurant> restaurantList=new ArrayList<Restaurant>();
        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(xmlFileName); //加载xml文件
            List peoples = doc.selectNodes("//*[@name]"); //选择所有具有name属性的节点(即demo.xml中的所有card节点)
            for (Iterator iter = peoples.iterator(); iter.hasNext(); ) {
                Restaurant restaurant=null;
                Element card = (Element) iter.next();
                List attrList = card.attributes();
                //输出每个item所有属性
                for (Iterator attr = attrList.iterator(); attr.hasNext(); ) {
                    if(restaurant==null)
                        restaurant=new Restaurant();
                    Attribute a = (Attribute) attr.next();
                    System.out.println(a.getName() + "=" + a.getValue());
                    if(a.getName().equals("id")){
                        restaurant.setId(a.getValue());
                    }else if(a.getName().equals("name")){
                        restaurant.setName(a.getValue());
                    }
                }
                if(restaurant!=null)
                    restaurantList.add(restaurant);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return  restaurantList;
        }
    }
}
