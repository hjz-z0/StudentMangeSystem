<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
	$(function(){
	   //正确显示导航栏
	   let navIndex = ${index};
	   //console.log($('ul.nav li').size());
	   $('ul.nav li').each(function(i){
		   //alert(i);
		   //将所有导航栏背景清空
		   $(this).removeClass('active');
		   if(navIndex==i){
			   $(this).addClass('active');
		   }
	   });

	   $('#yuliu').click(function () {

		   $('ul.nav li').each(function(i){
			   //alert(i);
			   //将所有导航栏背景清空
			   $(this).removeClass('active');
		   });
		  $('.dropdown').addClass('active');

	   })

		$('#myBlog').click(function () {
			alert("请从首页日志查看！");
		})
	   
	   
	});


</script>
</head>
<body>
<%
	Date d = new Date();
	SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
	String now = df.format(d);
%>
<div class="container nav-height">
   		<div class="col-sm-3">
   			<img alt="" src="<%=basePath%>/images/logn.png">
   		</div>
   		<div class="col-md-3 col-md-offset-6 visible-md-block visible-lg-block">
   			<p class="p-css"><%=now %></p>
   		</div>
    </div>
    <div class="nav-style">
    	<div class="container">
	    	<div class="col-sm-12">
	    		<ul class="nav nav-pills">
                    <li class="active"><a href="<%=basePath%>/sysuser/toStudentMain.do">首&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;页</a></li>
                    <li><a href="<%=basePath%>/student/myBlog.do">我的日志</a></li>
                    <li><a href="<%=basePath%>/newBlog.do">发表日志</a></li>
                    <li class="dropdown" id="yuliu">
                        <a href="#" data-toggle="dropdown">预留链接 <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="<%=basePath%>/student/modifyStudentInfo.do?username=${user.username}">更新个人信息</a></li>
                            <li><a href="#" data-toggle="modal" data-target="#modfiyPWD">修改登录密码</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#" onclick="logOut()">退出校园系统</a></li>
                        </ul>
                    </li>
				</ul>
	   		</div>
    	</div>
    </div>
</body>
</html>