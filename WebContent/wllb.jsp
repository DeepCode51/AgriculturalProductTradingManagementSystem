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
    <link href="css/public.css" type="text/css" rel="stylesheet"/>
    <link href="css/liebiao.css" type="text/css" rel="stylesheet"/>
    <link href="css/reg.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
    <script type="text/javascript" src="js/slide.js"></script>
    <style type="text/css">
    input{
    padding-left:10px;
    }
    </style>
</head>
<script>
    $(function(){
        $('.nav ul li').hover(function(){
            $(this).children(".details").show();
        },function(){
            $(this).children(".details").hide();
        });
        $('#my').hover(function(){
            $(this).find("div").show();
        },function(){
            $(this).find("div").hide();
        });
    });
</script>

<body>
<!------------顶部---------------->
<!--------------logo搜索------------->
<!--------------导航------------------>
<jsp:include page="top.jsp"></jsp:include>
<!---------------------导航完--------------------->
<div class="w1200" style="margin-bottom: 60px;">
	<div class="position"><a href="#">首页</a> > <a href="#">物流信息</a></div>
    
    <div class="m_d">
    	<jsp:include page="grleft.jsp"></jsp:include>
        <div class="right" >
            <dl class="dl02">
            	<dt>
                	<span>物流信息</span>
                </dt>
                <dd style="border:0px solid red;">
                <c:forEach items="${pageInfo.list}" var="wlrecord">
                	<div class="item"  style="border:0px solid black;margin: 0px;">
                	<font style="font-weight: bold;">[${wlrecord.savetime}]&nbsp;&nbsp;</font>抵达&nbsp;&nbsp;${wlrecord.content}&nbsp;&nbsp;${wlrecord.status}
                	</div>
                </c:forEach>
                </dd>
            </dl>
        </div>
        <div class="clear"></div>
    </div>
</div>



<!---------------------保障------------------->
<jsp:include page="foot.jsp"></jsp:include>
</body>
<script type="text/javascript">


<%
String suc = (String)request.getAttribute("suc");
if(suc!=null){
%>
layer.msg('<%=suc%>');
<%}%>
</script>
</html>
