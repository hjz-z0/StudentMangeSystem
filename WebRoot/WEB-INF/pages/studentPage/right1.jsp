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
</head>
<body>
<div class="col-sm-4">
    		<div class="panel panel-default">
    		  <div class="panel-heading">
    		  	 <img alt="" src="<%=basePath%>/images/user.png">
			     <span class="font-style"> 欢迎您：${user.username}学生！</span>
    		  </div>
			  <div class="panel-body">
			  	<div class="col-sm-12">
				   	<ul class="nav nav-pills nav-stacked">
					  <li><a class="btn btn-link" data-toggle="modal" data-target="#modfiyPWD" style="text-align: left;">修改登录密码</a></li>
					  <li><a onclick="logOut()" style="cursor: pointer;">退出校园系统</a></li>
					</ul>
			  	</div>
			  </div>
			</div>
			<div class="panel panel-default">
    		  <div class="panel-heading">
    		  	 <img alt="" src="<%=basePath%>/images/message.png">
			     <span class="font-style">&nbsp;联系我们：</span>
    		  </div>
			  <div class="panel-body">
			  	<address class="padding-left-10 font-info">
				  <strong>联系地址：</strong><br>
				  南京市紫荆花路68号中兴通讯南京研发中心<br>
				  <strong>联系电话：</strong><br>
				   025-12345678
				</address>
			  </div>
			</div>
   		</div>
   	</div>
</body>
</html>