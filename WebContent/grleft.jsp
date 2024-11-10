<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="x-ua-compatible" content="IE=edge, chrome=1">
    <title>农产品交易平台</title>
    <meta name="description" content="" />
    <meta name="keywords" content="" />
</head>

<body>
    	<div class="left">
        	<dl><dt>账户管理</dt>
            <dd>
            	<a href="memberHome.do" class="on">个人资料</a>
                <a href="orderLb.do">订单信息</a>
                <a href="favLb.do">收藏商品</a>
                <a href="shapply.do">售后申请</a>
               <!-- <a href="toBbsAdd.do">发布帖子</a>
                <a href="mybbsList.do">我的帖子</a>
                <a href="favbbsLb.do">收藏帖子</a> --> 
                <a href="messageAdd.jsp">发表留言</a>
                <a href="message_List.do">留言板</a>
            </dd>
            </dl>
        </div>
</body>
</html>
