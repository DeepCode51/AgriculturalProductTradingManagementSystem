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
    <link href="css/article.css" type="text/css" rel="stylesheet"/>
    <link href="css/liebiao.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="js/slide.js"></script>
    
     <style type="text/css">
    .page.wt1080.acvtive{
    border:1px solid red;
    background-color: #FE5500;
    color:white;
    border:1px solid #FE5500;
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
<jsp:include page="top.jsp"></jsp:include>
<!--------------logo搜索------------->

<!---------------------导航完--------------------->

<!-----------------内容------------------>
<div class="wt1080 middle" >
    <div class="fr" style="border:0px solid red;float: left;width: 99%">
        <div class="m_f_t">当前位置：<span class="place" style="border: 0px;">社区</span></div>
        <div class="con" >
           <ul>
           
           <c:forEach items="${pageInfo.list}" var="bbs">
             <li style="margin-bottom: 20px;border-bottom: 1px dashed #EEEEEE;padding-bottom: 5px;">
               <h3 style="font-size:14px;"><a href="bbsShow.do?id=${bbs.id }">${bbs.title }</a></h3>
               <p style="margin-top: 10px;"><a href="bbsShow.do?id=${bbs.id }" style="color:gray;">${bbs.member.tname} / ${bbs.savetime}</a></p>
             </li>
             
          </c:forEach>   
             
           </ul>
        </div>

    </div>
</div>

<div class="page wt1080">
  <a href="bbsLb.do?pageNum=${pageInfo.hasPreviousPage==false?1:pageInfo.prePage}">上一页</a>
  <c:forEach begin="1" end="${pageInfo.pages}" varStatus="status">
  <a href="bbsLb.do?pageNum=${status.count}" ${status.count eq pageInfo.pageNum ?"class='page wt1080 acvtive'":""}}>${status.count}</a>
  </c:forEach>
  <a href="bbsLb.do?pageNum=${pageInfo.hasNextPage==false? pageInfo.pages : pageInfo.nextPage}">下一页</a>
  </div>

<!---------------------热销------------------>
<div class="hot wt1080" >
    <div class="title">热销推荐</div>
    <div class="content" style="background-color: white;">
        <ul>
        <c:forEach items="${zphlist}" var="product" begin="0" end="4">
	        <li>
	            <div class="pic"><a href="productDetails.do?id=${product.id}"><img src="upload/${product.filename}" style="height: 130px;"></a></div>
	            <p class="c_t"><a href="productDetails.do?id=${product.id}">${product.productname}</a></p>
	            <p class="price">￥${product.price}</p>
	            <a href="productDetails.do?id=${product.id}" class="goumai">立即购买</a>
	        </li>
        </c:forEach>
        </ul>
        <div class="clear"></div>
    </div>
</div>

<!---------------------保障------------------->
<jsp:include page="foot.jsp"></jsp:include>
</body>

</html>



