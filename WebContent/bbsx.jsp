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
    <link href="css/show.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="layer/jquery-2.0.3.min.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
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
        <div class="m_f_t">当前位置：<span class="place" style="border: 0px;">帖子详情</span></div>
        <div class="con" >
               <h3 style="font-size:24px;text-align: center;">${bbs.title }</h3>
               <p style="text-align: center;font-size:12px;"><span style="margin-right: 5px;">发布者:${bbs.member.tname }&nbsp;&nbsp;&nbsp;发布时间</span>${bbs.savetime}
               &nbsp;&nbsp;<a href="javascript:fav(${bbs.id })">[收藏]</a>
               &nbsp;&nbsp;<a href="javascript:jb(${bbs.id })">[举报]</a>
               </p>
               <p style="margin-top: 10px;">${bbs.note}</p>
        </div>
    </div>
    
    
    <div class="details">
        <div class="details_left">
            <div class="pingjia">
                <div class="pingjia_t">
                    <span>帖子评论</span>
                </div>
                <!----------评价----------->
                <div class="pingjia_d">
                    <div class="pingjia_d_t">
                    
                        <span class="current"><img src="image/d1.png">评论数（${fn:length(pageInfo.list)}）</span>
                    </div>
                    <!----------留言----------->
                    <div class="pingjia_d_l">
                        <ul>
						<c:forEach items="${pageInfo.list}" var="sbbs">
                            <li>
                                <p class="title"><span>${sbbs.member.tname}</span>（${sbbs.savetime}）</p>
                                <p class="mess">${sbbs.note}</p>
                            </li>
						</c:forEach>
                        </ul>
                    </div>
                    <!---------------分页--------------->
                <div class="page wt1080">
				  <a href="bbsShow.do?pageNum=${pageInfo.hasPreviousPage==false?1:pageInfo.prePage}&id=${bbs.id}">上一页</a>
				  <c:forEach begin="1" end="${pageInfo.pages}" varStatus="status">
				  <a href="bbsShow.do?pageNum=${status.count}&id=${bbs.id}" ${status.count eq pageInfo.pageNum ?"class='page wt1080 acvtive'":""}}>${status.count}</a>
				  </c:forEach>
				  <a href="bbsShow.do?pageNum=${pageInfo.hasNextPage==false? pageInfo.pages : pageInfo.nextPage}&id=${bbs.id}">下一页</a>
				  </div>
                <!-- 评价 -->
                <div class="wt1080" style="border:0px solid red;width: auto;margin-top: 30px;">
                <h2 style="padding-left: 10px;color:#B1585A;font-size:20px;margin-bottom: 30px;">请在下方评论</h2>
                <div style="padding-left: 10px;">
                  <form action="tzhf.do" method="post">
                  <input type="hidden" value="${bbs.id}" id="fid" name="fid"/>
                    <textarea rows="" cols="" name="note" style="width: 500px;height: 150px;padding:5px;border:1px solid #EEEEEE" placeholder="评论内容" required oninvalid="setCustomValidity('请输入评论内容')" oninput="setCustomValidity('');"></textarea>
                    <div style="margin-top: 15px;"><input type="submit" value="提交" style="width: 120px;height: 40px;color:white;background: #FF9900;font-size:18px;border:0px;cursor:pointer"/></div>
                  </form>
                </div>
                
                </div>
                <!-- 评价 -->
                </div>
            </div>
            <!---------------------常见问题------------------->
            <div class="problem">
            </div>
        </div>


    </div>
    
    
</div>


<!---------------------热销------------------>


<!---------------------保障------------------->
<jsp:include page="foot.jsp"></jsp:include>
</body>
<script type="text/javascript">


function fav(bbsid){
	$.ajax({
		type:"post",
		url:"addFavbbs.do?bbsid="+bbsid,
		date:"",
		success:function(msg){
		if(msg==1){
		  //location.reload();
			layer.msg("收藏成功");
		}else if(msg==0){
	    //layer.msg("请先登录");
		    location.replace("skipLogin.do");
		}else if(msg==2){
			layer.msg("不能重复操作");
		}
	}
	})
}

function jb(bbsid){
	$.ajax({
		type:"post",
		url:"tojbAddYz.do?bbsid="+bbsid,
		date:"",
		success:function(msg){
		if(msg==0){
	   		layer.msg("请先登录");
		}else{
			location.href="tojbAdd.do?bbsid="+bbsid;
		}
	}
	})
}

</script>
</html>



