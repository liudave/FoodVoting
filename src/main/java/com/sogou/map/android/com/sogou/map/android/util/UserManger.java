package com.sogou.map.android.com.sogou.map.android.util;

import com.sogou.map.android.com.sogou.map.android.model.User;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Dawei on 2016/7/31.
 */
public class UserManger {

    private static String xmlFileName=UserManger.class.getResource("/").getPath()+"/config/User.xml";;
    private static UserManger userManger;

    public static UserManger getInstance(){
        if(userManger==null){
            userManger=new UserManger();
        }
        return userManger;
    }

    /**
     * 初始化用户信息
     * @return
     */
    public List<User> loadUserInfo(){
        List<User> userList=new ArrayList<User>();
        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(xmlFileName); //加载xml文件
            List peoples = doc.selectNodes("//*[@name]"); //选择所有具有name属性的节点(即demo.xml中的所有card节点)
            for (Iterator iter = peoples.iterator(); iter.hasNext(); ) {
                User user=null;
                Element card = (Element) iter.next();
                List attrList = card.attributes();
                //输出每个card的所有属性
                for (Iterator attr = attrList.iterator(); attr.hasNext(); ) {
                    Attribute a = (Attribute) attr.next();
                    System.out.println(a.getName() + "=" + a.getValue());
                    if(user==null)
                        user =new User();
                    if(a.getName().equals("id")){
                        user.setId(a.getValue());
                    }else if(a.getName().equals("name")){
                        user.setName(a.getValue());
                    }else if(a.getName().equals("real_name")){
                        user.setReal_name(a.getValue());
                    }else if(a.getName().equals("pwd")){
                        user.setPwd(a.getValue());
                    }
                }
                if(user!=null)
                    userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return  userList;
        }
    }

    public static void Test() {
//        String fileName = "classpath:config/demo.xml"; //当前路径下的demo.xml
        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(xmlFileName); //加载xml文件

            List peoples = doc.selectNodes("//*[@name]"); //选择所有具有name属性的节点(即demo.xml中的所有card节点)
            for (Iterator iter = peoples.iterator(); iter.hasNext(); ) {
                Element card = (Element) iter.next();
                List attrList = card.attributes();
                //输出每个card的所有属性
                for (Iterator attr = attrList.iterator(); attr.hasNext(); ) {
                    Attribute a = (Attribute) attr.next();
                    System.out.println(a.getName() + "=" + a.getValue());
                }
                System.out.println(
                        "----------------------------------------------------");
            }

//            Element zhangsan = (Element) doc.selectSingleNode("//card[@id='2']"); //查找“id属性”=2的card元素
//            System.out.println("张三的名称：" + zhangsan.attribute("name").getValue()); //输出zhangsan的name属性
//
//            Node addrFamily = zhangsan.selectSingleNode("./address/item[2]"); //选择zhangsan元素下的address节点下的第2个item子节点
//            System.out.println("张三的单位地址：" + addrFamily.getStringValue()); //输出cdata内容
//
//            System.out.println(
//                    "----------------------------------------------------");
//            //为zhangsan下增加二个节点
//            zhangsan.addElement("email").addAttribute("type",
//                    "工作").addText("work@some-domain.com");
//            zhangsan.addElement("email").addAttribute("type",
//                    "私人").addCDATA("private@some-domain.com"); //设置CDATA内容
//
//            System.out.println(zhangsan.asXML()); //打印zhangsan节点的xml内容(调试用)
//            System.out.println(
//                    "----------------------------------------------------");
//
//            //将上述改动保存到文件demo2.xml
//            writerDocumentToNewFile(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //document写入新的文件
    public static void writerDocumentToNewFile(Document document)throws Exception{
        //输出格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        //设置编码
        format.setEncoding("UTF-8");
        //XMLWriter 指定输出文件以及格式
        XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(new File(xmlFileName)),"UTF-8"), format);
        //写入新文件
        writer.write(document);
        writer.flush();
        writer.close();
    }

}
