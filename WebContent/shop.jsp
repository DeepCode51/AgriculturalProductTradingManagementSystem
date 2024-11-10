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
<!--------------logo搜索------------->
<!--------------导航------------------>
<div class="top">
    <div class="wt1080">
        <div class="fl">
            <a href="index.do">首页</a>&emsp;|&emsp;请&emsp;<a href="skipLogin.do" style="color: #ff9900">登陆</a>&emsp;或&emsp;<a href="skipReg.do">用户注册</a>&emsp;<a href="skipshopRetist.do">商家注册</a>
        </div>
        <div class="fr">
            <ul>
                <c:if test="${sessionScope.sessionmember ne null}">
                <!-- 
                <li style="position: relative;" id="my">
                    <a href="my_order.html">我的主页 <img src="image/sanjiao.png"></a>
                    <div class="personal">
                        <dl>
                            <dt><a href="my_order.html">我的订单</a></dt>
                            <dd><a href="my_youhuijuan.html">我的优惠卷</a></dd>
                            <dd><a href="my_jifen.html">我的积分</a></dd>
                            <dt><a href="my_order.html">个人信息</a></dt>
                        </dl>
                    </div>
                </li>
                 -->
                <li><span><a href="liwu.do">领取生日礼包</a></span></li>
                <li><span><a href="memberHome.do">我的主页</a></span></li>
                <li><span class="shop">购物车<a href="cartList.do">${fn:length(cartlist)}</a>件</span></li>
                <li><span><a href="memberExit.do">安全退出</a></span></li>
                </c:if>
            </ul>
        </div>
    </div>
</div>
<!--------------logo搜索------------->
<div class="wt1080 header">
    <div class="logo fl"><a href="shopGoods.do?shopid=${shop.id}" style="font-size:28px;color:#FF9900">${shop.realname}</a></div>
    <form action="Search.do" method="post" name="searchForm" id="searchForm">
    <div class="search fl">
        <div>
            <input name="key" value="${key}" type="text" class="a_search fl" placeholder="请输入关键字">
            <span class="b_search fl" id="searchbutton"></span>
            <div class="clear"></div>
        </div>
    </div>
    </form>
    <a class="my_shop fr" href="cartList.do">我的购物车
    <c:if test="${sessionScope.sessionmember ne null}">
    <span>${fn:length(cartlist)}</span>
    </c:if>
    </a>
    <div class="clear"></div>
</div>
<!--------------导航------------------>
<div class="nav">
    <div class="wt1080">
        <ul>
            <li>
                <a href="index.do" class="current"><span>首页</span></a>
            </li>
            
            <c:forEach items="${ctlist}" var="fcategory">
            <li>
                <a href="shopGoods.do?fid=${fcategory.id}&shopid=${shop.id}"><span>${fcategory.name}</span></a>
                    <div class="details">
                        <div class="ctgnamebox">
                          <c:forEach items="${fcategory.scategorylist}" var="scategory">
                            <a href="shopGoods.do?sid=${scategory.id}&shopid=${shop.id}" >${scategory.name}</a>
                            <!-- class="current" -->
                          </c:forEach>
                        </div>
                    </div>
            </li>
            </c:forEach>
            <li><a href="fubiLb.do"><span>积分商品</span></a></li>
            <!-- 
            <li><a href="lanmu.html"><span>家具生活</span></a></li>
            <li><a href="lanmu.html"><span>食品营养</span></a></li>
            <li><a href="lanmu.html"><span>全球直邮</span></a></li>
            <li><a href="lanmu.html"><span>合作申请</span></a></li>
             -->
        </ul>
        <div style="clear:both"></div>
    </div>
</div>

<!----------------轮播图-------------------->

</body>
<script type="text/javascript">
$("#searchbutton").click(function(){
	$("#searchForm").submit();
})
</script>
<!---------------------导航完--------------------->
<div class="content">
    <!-------------------分类------------------>
    <!-------------------位置------------------>
    <div class="place">
        位置：<a class="check" href="#">${shop.realname}>>${categroystr}</a>共<span class="number">${fn:length(nlist)}</span>件商品
        <a class="biaoqian pa3" href="shopGoods.do?ph=price&fid=${fid}&sid=${sid}&shopid=${shopid}">价格 ↑</a>
    </div>
    <!----------------产品详细------------------->
    <div class="product">
        <ul>
        <c:forEach items="${pageInfo.list}" var="product">
            <li>
                <div class="pic"><a href="productDetails.do?id=${product.id}"><img src="upload/${product.filename}"></a></div>
                <p class="one"><a href="productDetails.do?id=${product.id}">${product.productname}</a></p>
                <p class="two">
				  <c:choose>
                  <c:when test="${product.tprice gt 0}">
                  <span style="color: #fe5500;margin-right: 10px;">￥<span style="font-size:20px;">${product.tprice}</span></span>
                  <span style="text-decoration:line-through">￥${product.price}</span>
                  </c:when>
                  <c:otherwise>
                  <span style="color: #fe5500;margin-right: 10px;">￥<span style="font-size:20px;">${product.price}</span></span>
                  </c:otherwise>
                </c:choose>                
                </p>
            </li>
        </c:forEach>
        </ul>
        <div class="clear"></div>
    </div>
    <div class="page wt1080">
    <a href="shopGoods.do?pageNum=${pageInfo.hasPreviousPage==false?1:pageInfo.prePage}&ph=price&fid=${fid}&sid=${sid}&shopid=${shopid}">上一页</a>
    <c:forEach begin="1" end="${pageInfo.pages}" varStatus="status">
    <a href="shopGoods.do?pageNum=${status.count}&ph=price&fid=${fid}&sid=${sid}&shopid=${shopid}" ${status.count eq pageInfo.pageNum ?"class='page wt1080 acvtive'":""}}>${status.count}</a>
    </c:forEach>
    <a href="shopGoods.do?pageNum=${pageInfo.hasNextPage==false? pageInfo.pages : pageInfo.nextPage}&ph=price&fid=${fid}&sid=${sid}&shopid=${shopid}">下一页</a>
    </div>
</div>



<!---------------------保障------------------->

<!---------------底部--------------->
<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>
