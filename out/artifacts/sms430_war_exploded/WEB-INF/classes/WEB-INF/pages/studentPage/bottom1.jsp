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
<!-- 页尾 版权声明 -->
    <div class="container">
		<div class="col-sm-12 foot-css">
		        <p class="text-muted credit">
		            Copyright © 2018 中兴软件 版权所有
		        </p>
	    </div>
    </div>

<div class="modal fade" id="modfiyUSER" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="myModalLabel">学生更新个人信息</h4>
			</div>
			<form action="<%=basePath%>/student/modifyStudentInfo.do" class="form-horizontal" method="post">
				<input type="hidden" id="username" value="${user.username}">
				<div class="form-group">
					<label class="col-sm-2 control-label">学生编号：</label>
					<div class="col-sm-6">
						<input class="form-control" type="text" value="1" readonly="readonly">
					</div>
					<label class="col-sm-4 control-label error-info" style="text-align:left;">*不可修改</label>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">登录账户：</label>
					<div class="col-sm-6">
						<input class="form-control" type="text" value="tom" readonly="readonly">
					</div>
					<label class="col-sm-4 control-label error-info" style="text-align:left;">*不可修改</label>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">学生姓名：</label>
					<div class="col-sm-6">
						<input class="form-control" type="text" value="mike">
					</div>
					<label class="col-sm-4 control-label error-info" style="text-align:left;">*不能为空</label>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄：</label>
					<div class="col-sm-6">
						<input class="form-control" type="text" value="1">
					</div>
					<label class="col-sm-4 control-label error-info" style="text-align:left;">*不能为空</label>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</label>
					<div class="col-sm-6">
						<div class="radio">
							<label>
								<input type="radio" value="male" name="sex" checked="checked"> 男
							</label>
							&nbsp;&nbsp;&nbsp;
							<label>
								<input type="radio" value="female" name="sex"> 女
							</label>
						</div>
					</div>
					<label class="col-sm-4 control-label error-info" style="text-align:left;"></label>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">班&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级：</label>
					<div class="col-sm-6">
						<input class="form-control" type="text" value="tom" readonly="readonly">
					</div>
					<label class="col-sm-4 control-label error-info" style="text-align:left;">*不可修改</label>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">课&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;程：</label>
					<div class="col-sm-6">
						<input class="form-control" type="text" value="tom" readonly="readonly">
					</div>
					<label class="col-sm-4 control-label error-info" style="text-align:left;">*不可修改</label>
				</div>
				<div class="form-group">
					<div class="col-sm-6  col-sm-offset-2">
						<div class="col-sm-6">
							<button type="reset" class="btn btn-primary btn-block">重&nbsp;&nbsp;置</button>
						</div>
						<div class="col-sm-6">
							<button type="submit" class="btn btn-primary btn-block">修&nbsp;&nbsp;改</button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
    <!-- 密码修改model窗口 -->
    <div class="modal fade" id="modfiyPWD" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">用户密码修改</h4>
	      </div>
	      <form action="" class="form-horizontal" method="post" id="modFrm">
		      <div class="modal-body">
			     <div class="form-group">
			       <label class="col-sm-3 control-label">登录密码：</label>
			       <div class="col-sm-6">
			         <input class="form-control" type="password" id="oldPass">
			       </div>
			       <label class="col-sm-3 control-label error-info" style="text-align:left;">*不可为空</label>
			    </div>
			     <div class="form-group">
			       <label class="col-sm-3 control-label">新的密码：</label>
			       <div class="col-sm-6">
			         <input class="form-control" type="password" id="newPass">
			       </div>
			       <label class="col-sm-3 control-label error-info" style="text-align:left;">*不可为空</label>
			    </div>
			     <div class="form-group">
			       <label class="col-sm-3 control-label">重复密码：</label>
			       <div class="col-sm-6">
			         <input class="form-control" type="password">
			       </div>
			       <label class="col-sm-3 control-label error-info" style="text-align:left;">*不可为空</label>
			    </div>
		      </div>
		      <div class="modal-footer">
		          <button type="button" class="btn btn-default" data-dismiss="modal" aria-label="Close">关&nbsp;&nbsp;闭</button>
		          <button type="reset" class="btn btn-default">重&nbsp;&nbsp;置</button>
		          <button type="button" class="btn btn-default" onclick="modifyPwd()">修&nbsp;&nbsp;改</button>
			  </div>
		  </form>
	    </div>
	  </div>
	</div>
	<!-- MODEL结束 -->
</body>
</html>