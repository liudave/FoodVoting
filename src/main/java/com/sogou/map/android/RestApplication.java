package com.sogou.map.android;

import com.sogou.map.android.com.sogou.map.android.util.RestaurantManager;
import com.sogou.map.android.com.sogou.map.android.util.UserManger;
import org.apache.logging.log4j.web.Log4jServletContextListener;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.RequestContextFilter;

/**
 * Created by liudawei on 2016/7/29.
 */
public class RestApplication  extends ResourceConfig {
    public RestApplication() {

        //服务类所在的包路径
        packages("com.sogou.map.android");
        //注册JSON转换器
        register(JacksonJsonProvider.class);
        // register filters
        register(RequestContextFilter.class);
        register(CharacterEncodingFilter.class);
        register(Log4jServletContextListener.class);
        Voting.userList= UserManger.getInstance().loadUserInfo();
        Voting.restaurantList= RestaurantManager.getInstance().loadRestaurantInfo();
    }
}
