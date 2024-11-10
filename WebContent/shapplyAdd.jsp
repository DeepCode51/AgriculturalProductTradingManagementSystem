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
	<div class="position"><a href="#">首页</a> > <a href="#">会员中心</a> > <a href="#">申请售后</a></div>
    
    <div class="m_d">
    	<jsp:include page="grleft.jsp"></jsp:include>
        <div class="right" >
             
            <dl class="dl02">
            	<dt>
                	<span>申请售后</span>
                </dt>
                <dd>
                <form action="shapplyAdd.do" method="post">
                <input type="hidden" id="orderid" name="orderid" value="${orderid }">
                
                
                <div class="item"><span>商品：</span>
                	<select id="productid" name="productid" required>
                		<option value="" >请选择订单内商品</option>
                		<c:forEach items="${orderdetaillist }" var="orderdetail">
                		<option value="${orderdetail.product.id }" >${orderdetail.product.productname }</option>
                		</c:forEach>
                	</select>
                </div>
                
                	<div class="item"><span>联系人：</span><input type="text" class="txt" name="lxr"  placeholder="联系人" required oninvalid="setCustomValidity('请输入联系人')" oninput="setCustomValidity('');"/></div>
                	<div class="item"><span>联系人电话：</span><input type="text" class="txt" name="tel"  placeholder="联系人电话" required oninvalid="setCustomValidity('请输入联系人电话')" oninput="setCustomValidity('');"/></div>
                	<div class="item"><span>联系人地址：</span><input type="text" class="txt" name="addr"  placeholder="联系人地址" required oninvalid="setCustomValidity('请输入联系人地址')" oninput="setCustomValidity('');"/></div>
                	<div class="item"><span>原因：</span>
                	<textarea rows="5" cols="100" id="note" name="note" required oninvalid="setCustomValidity('请输入留言内容')" oninput="setCustomValidity('');"></textarea>
                	</div>
                    <div class="item"><input type="submit" class="sub" value="提交"/></div>
                </form>
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
