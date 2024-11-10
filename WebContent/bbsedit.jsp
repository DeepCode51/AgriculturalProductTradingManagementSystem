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
    <script type="text/javascript" src="layer/jquery-2.0.3.min.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
    <script type="text/javascript" src="js/slide.js"></script>
    <style type="text/css">
    input{
    padding-left:10px;
    }
    </style>
    
    <script charset="utf-8" src="kindeditor/kindeditor-all.js"></script>
<script charset="utf-8" src="kindeditor/lang/zh-CN.js"></script>
<script>
    KindEditor.ready(function(K) {
        window.editor = K.create('#editor_id');
    });
</script>
<script>
    KindEditor.ready(function(K) {
        K.create('textarea[name="note"]', {
            uploadJson : 'kindeditor/jsp/upload_json.jsp',
            fileManagerJson : 'kindeditor/jsp/file_manager_json.jsp',
            allowFileManager : true,
            allowImageUpload : true,
            autoHeightMode : true,
            afterCreate : function() {this.loadPlugin('autoheight');},
            afterBlur : function(){ this.sync(); }  //Kindeditor下获取文本框信息
        });
    });
</script>

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
	<div class="position"><a href="#">首页</a> > <a href="#">会员中心</a> > <a href="#">编辑帖子</a></div>
    
    <div class="m_d">
    	<jsp:include page="grleft.jsp"></jsp:include>
        <div class="right" >
             
            <dl class="dl02">
            	<dt>
                	<span>编辑帖子</span>
                </dt>
                <dd>
                <form action="bbsEdit.do" method="post">
                <input type="hidden" id="id" name="id" value="${bbs.id }">
                	<div class="item"><span>标题：</span><input value="${bbs.title }" type="text" class="txt" name="title"  placeholder="标题" required oninvalid="setCustomValidity('请输入标题')" oninput="setCustomValidity('');"/></div>
                	<div class="item"><span>内容：</span></div>
                	<textarea  id="note" name="note" required style="width: 800px;height: 300px">${bbs.note }</textarea>
                	
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
