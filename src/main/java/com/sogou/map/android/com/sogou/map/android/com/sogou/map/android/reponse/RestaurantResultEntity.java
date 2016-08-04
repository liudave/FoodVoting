package com.sogou.map.android.com.sogou.map.android.com.sogou.map.android.reponse;

import com.sogou.map.android.com.sogou.map.android.model.Restaurant;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Dawei on 2016/7/31.
 */
@XmlRootElement
public class RestaurantResultEntity {
    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
    private List<Restaurant> restaurants;
}
