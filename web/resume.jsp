<%--
  Created by IntelliJ IDEA.
  User: liudawei
  Date: 2016/8/2
  Time: 18:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>选餐后台系统</title>
</head>
<body>
<form action="/api/vote/resume" method="get">
    <input type="text" id="name" name="name"/><br/>
    <input type="submit" value="重置投票"/></form>
<br/>
<br/>
<form action="/api/vote/getCurrentPicks" method="get">
    <input type="text" id="name_2" name="name"/><br/>
    <input type="submit" value="获取投票结果"/></form>
</body>
<br/>
<br/>
<form action="/api/vote/reloadUser" method="get">
    <input type="text" id="name_3" name="name"/><br/>
    <input type="submit" value="重新加载用户"/></form>
<br/>
<br/>
<form action="/api/vote/reloadRestaurant" method="get">
    <input type="text" id="name_4" name="name"/><br/>
    <input type="submit" value="重新加载餐厅"/></form>
</body>
</html>
