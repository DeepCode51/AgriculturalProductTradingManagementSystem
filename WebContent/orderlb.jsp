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
    <link href="css/my_order.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
    <script type="text/javascript" src="js/slide.js"></script>
    <style>
            .ordertable{
            border:1px solid #E8E8E8;
            border-collapse: collapse;
            margin: 0px;
            padding: 0px;
            }
            .tableth{
            background: #F7F7F7;
            height: 50px;
            }
            
            .ordertable td{
            border: 1px solid #E8E8E8;
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
	<div class="position"><a href="#">首页</a> > <a href="#">会员中心</a> > <a href="#">个人资料</a></div>
    
    <div class="m_d">
    	<jsp:include page="grleft.jsp"></jsp:include>
        <div class="right" style="border:0px solid red;">
        <!-- s -->
        <div class="middle" style="border:0px solid red;margin: 0px;float: left;width: 100%;padding:0px;">
        <div class="fr" style="border:0px solid black;margin: 0px;width: 100%;padding:0px;">
        <div class="c_r_o" style="margin: 0px;padding: 0px;">
            <!------------表单------------->
            <table class="ordertable"  bordercolor="#E8E8E8" cellpadding="1" cellspacing="0" >
                <tr class="tableth" >
                    <td style="font-size:12px;">订单号</td>
                    <td style="font-size:12px;">商品信息</td>
                    <td style="font-size:12px;">总计（元）</td>
                    <td style="font-size:12px;">订单状态</td>
                    <td style="font-size:12px;">收货地址</td>
                    <td style="font-size:12px;">收货方式</td>
                    <td style="font-size:12px;">支付方式</td>
                    <td style="font-size:12px;">操作</td>
                </tr>
                <c:forEach items="${pageInfo.list}" var="ordermsg">
                <tr class="thitems">
                    <td style="height: 40px;font-size:12px;">
                    ${ordermsg.ddno }
                    </td>
                    <td style="font-size:12px; text-align: left; " >
                      <c:forEach items="${ordermsg.dddetailist }" var="detail">
                      <a href="productDetails.do?id=${detail.product.id}">
                      <img src="upload/${detail.product.filename}" style="width: 60px;height: 60px;"/>
                      </a>&nbsp;
                      	<a href="productDetails.do?id=${detail.product.id}">${detail.product.productname}</a>&nbsp;数量:${detail.num}<br/>
                      </c:forEach>
                    </td>
                    <td style="font-size:12px;">${ordermsg.total}</td>
                    <td style="font-size:12px;">${ordermsg.fkstatus }</td>
                    <td style="font-size:12px;text-align: left;" >${ordermsg.address.name}<br/>${ordermsg.address.addr}<br/>${ordermsg.address.tel}<br/>
                  <c:if test="${ordermsg.remark ne null }">
                    【${ordermsg.remark }】
                    </c:if>
                    </td>
                    <td style="font-size:12px;">${ordermsg.shfs }</td>
                    <td style="font-size:12px;">${ordermsg.zffs }</td>
                    <td style="font-size:12px; text-align: left;">
                    
                    <c:if test="${ordermsg.fkstatus eq '待付款'}">
                    <a href="skipFukuan.do?id=${ordermsg.id}" style="color: green">【支付】</a><br/>
                    <a href="qxOrdermsg.do?id=${ordermsg.id}" style="color: green">【取消订单】</a><br/>
                    </c:if>
                    
                    <c:if test="${ordermsg.isdd eq '抵达目的地' and ordermsg.fkstatus eq '已发货'}">
                    <a href="qianShou.do?id=${ordermsg.id}" style="color: green">【签收】</a><br/>
                    </c:if>
                    
                    <c:if test="${fn:length(ordermsg.wllist) gt 0}">
                    <a href="wlLb.do?ddno=${ordermsg.ddno}" style="color: green">【物流信息】</a><br/>
                    </c:if>
                    
                    <c:if test="${ordermsg.fkstatus eq '交易完成'}">
                    <a href="toShapply.do?id=${ordermsg.id}" style="color: green">【申请售后】</a><br/>
                    <a href="orderSc.do?id=${ordermsg.id}" style="color: green">【删除】</a>
                    </c:if>
                    
                    </td>
                </tr>
                </c:forEach>
            </table>
            <!--------------列表----------->
            <!-------------------列表----------------------->
				  
        </div>
        
        <a href="orderLb.do?pageNum=${pageInfo.hasPreviousPage==false?1:pageInfo.prePage}">上一页</a>
				  <c:forEach begin="1" end="${pageInfo.pages}" varStatus="status">
				  <a href="orderLb.do?pageNum=${status.count}" ${status.count eq pageInfo.pageNum ?"class='page wt1080 acvtive'":""}}>${status.count}</a>
				  </c:forEach>
				  <a href="orderLb.do?pageNum=${pageInfo.hasNextPage==false? pageInfo.pages : pageInfo.nextPage}">下一页</a>
        
        </div>

        
        </div>
                
        <!-- e -->
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
<%
String msg = (String)request.getAttribute("msg");
if(msg!=null){
%>
layer.msg('<%=msg%>');
<%}%>
</script>
</html>
