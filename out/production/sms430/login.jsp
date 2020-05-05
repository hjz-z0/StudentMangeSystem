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
    <title>学生管理系统</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="<%=basePath%>/bootstrap/css/bootstrap.min.css" type="text/css"></link>
    <link rel="stylesheet" href="<%=basePath%>/css/mycss.css" type="text/css"></link>
    <script type="text/javascript" src="<%=basePath%>/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript">
    	//页面加载完成后调用
    	$(function(){
    		//alert(1);
    		//失去焦点时非空校验
    		$('#username').on('blur',function(){
    			//console.log(123);
    			if($(this).val()==''){
    				//alert('用户名不能为空');
    				//初始化tooltip
    				$(this).tooltip({
    					title:'用户名不能为空',
    					placement:'top',
    					trigger:'manual'//手动控制tooltip的显示和隐藏
    				}).tooltip('show');//显示该提示框
    				//将该元素边框设置error样式
    				$(this).parent().parent().addClass('has-error');
    			}
    			else{
    				$(this).parent().parent().removeClass('has-error');
    				$(this).tooltip('hide');
    			}
    		});
    		
    		$('#password').on('blur',function(){
    			if($(this).val()==''){
    				$(this).tooltip({
    					title:'密码不能为空',
    					trigger:'manual'
    				}).tooltip('show');
    				$(this).parent().parent().addClass('has-error');
    			}
    			else{
    				$(this).parent().parent().removeClass('has-error');
    				$(this).tooltip('hide');
    			}
    			
    		});
    		$('#state').on('blur',function(){
    			if($(this).val()==''){
    				$(this).tooltip({
    					title:'请选择身份',
    					trigger:'manual'
    				}).tooltip('show');
    				$(this).parent().parent().addClass('has-error');
    			}
    			else{
    				$(this).parent().parent().removeClass('has-error');
    				$(this).tooltip('hide');
    			}
    			
    		});
    		
    		//表单提交非空校验
    		$('#frmLogin').submit(function(){
    			//alert(1);
    			if($('#username').val()==''){
    				//alert('用户名不能为空');
    				//初始化tooltip
    				$('#username').tooltip({
    					title:'用户名不能为空',
    					placement:'top',
    					trigger:'manual'//手动控制tooltip的显示和隐藏
    				}).tooltip('show');//显示该提示框
    				//将该元素边框设置error样式
    				$('#username').parent().parent().addClass('has-error');
    				//不提交表单
    				return false;
    			}
    			
    			
    			if($('#password').val()==''){
    				$('#password').tooltip({
    					title:'密码不能为空',
    					trigger:'manual'
    				}).tooltip('show');
    				$('#password').parent().parent().addClass('has-error');
    				//不提交表单
    				return false;
    			}
    			    			
    			if($('#state').val()==''){
    				$('#state').tooltip({
    					title:'请选择身份',
    					trigger:'manual'
    				}).tooltip('show');
    				$('#state').parent().parent().addClass('has-error');
    				//不提交表单
    				return false;
    			}
    			
    			//提交表单
    			return true;
    			
    		});
    		
    		//服务器端校验
    		//alert('${error}');
    		let error='${error}';
    		if(error!=''){
    			$('#frmLogin').tooltip({
    				title:error,
    				trigger:'manual'
    			}).tooltip('show');
    		}
    		
    		//完成表单提交后表单的tooltip在显示后2秒钟以后自动关闭
    		$('[data-toggle="tooltip"]').each(function(){
    			//这个this表示的是表单中的元素
    			$(this).on('shown.bs.tooltip',function(){//绑定事件，当tooltip显示之后触发
    			   //这个this表示当前tooltip,
    			   let _this=this;
    			   setTimeout(function(){
    				 $(_this).tooltip('hide');  
    			   },2000);
    			});
    			
    		});
    		
    	});
    </script>
</head>
<body>
  	<!-- 使用自定义css样式 div-signin 完成元素居中-->
    <div class="container div-signin">
      <div class="panel panel-primary div-shadow">
      	<!-- h3标签加载自定义样式，完成文字居中和上下间距调整 -->
	    <div class="panel-heading">
	    	<h3>学生管理系统 3.0</h3>
	    	<span>Student Management System</span>
	    </div>
	   
	    <div class="panel-body">
	      <!-- login form start -->
	      <form action="<%=basePath%>/sysuser/login.do" class="form-horizontal" method="post" id="frmLogin" data-toggle="tooltip">
		     <div class="form-group">
		       <label class="col-sm-3 control-label">用户名：</label>
		       <div class="col-sm-9">
		         <input class="form-control" type="text" placeholder="请输入用户名" name="username" id="username">
		       </div>
		    </div>
		     <div class="form-group">
		       <label class="col-sm-3 control-label">密&nbsp;&nbsp;&nbsp;&nbsp;码：</label>
		       <div class="col-sm-9">
		         <input class="form-control" name="password" id="password" type="password" placeholder="请输入密码">
		       </div>
		    </div>
		     <div class="form-group">
		       <label class="col-sm-3 control-label">身&nbsp;&nbsp;&nbsp;&nbsp;份：</label>
		       <div class="col-sm-9">
		       	 <select class="form-control" name="state" id="state">
		       	 	<option value="">-请选择身份-</option>
		       	 	<option value="student">学生</option>
		       	 	<option value="admin">管理员</option>
		       	 </select>
		       </div>
		    </div>
		     <div class="form-group">
		       <label class="col-sm-3 control-label">验证码：</label>
		       <div class="col-sm-4">
		         <input class="form-control" type="text" placeholder="请输入验证码">
		       </div>
		       <div class="col-sm-2">
		       	  <!-- 验证码图片加载（需引入验证码文件）图像高度经过测试，建议不要修改 -->
			      <img class="img-rounded" src="<%=basePath%>/images/image.jpg" alt="验证码" style="height: 32px; width: 70px;"/>
		       </div>
		       <div class="col-sm-2">
		         <button type="button" class="btn btn-link">看不清</button>
		       </div>
		    </div>
		    <div class="form-group">
		       <div class="col-sm-3">
			        <button type="button" class="btn btn-link btn-block" data-toggle="modal" data-target="#registModal">注册账号</button>
		       </div>
		       <div class="col-sm-9 padding-left-0">
		       	 <div class="col-sm-4">
			         <button type="submit" class="btn btn-primary btn-block">登&nbsp;&nbsp;陆</button>
		       	 </div>
		       	 <div class="col-sm-4">
			         <button type="reset" class="btn btn-primary btn-block">重&nbsp;&nbsp;置</button>
		       	 </div>
		       	 <div class="col-sm-4">
			         <button type="button" class="btn btn-link btn-block">忘记密码？</button>
		       	 </div>
		       </div>
		    </div>
	      </form>
	      <!-- login form end -->
	    </div>
	  </div>
    </div>
    <!-- 页尾 版权声明 -->
    <div class="container">
		<div class="col-sm-12 foot-css">
		        <p class="text-muted credit">
		            Copyright © 2018 中兴软件 版权所有
		        </p>
	    </div>
    </div>

    <!-- 注册账号modal -->
	<div class="modal fade" id="registModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">注册账号</h4>
	      </div>
	      <div class="modal-body">
	        <form id="frmRegist" class="form-horizontal" method="post" action="">
		        <div class="form-group">
		            <label class="control-label col-sm-3">用户名</label>
		            <div class="col-sm-6">
		                <input type="text" class="form-control" name="username" />
		            </div>
		             <label class="col-sm-3 control-label error-info" style="text-align:left;">*不可为空</label>
		        </div>
		        <div class="form-group">
		            <label class="control-label col-sm-3">密码</label>
		            <div class="col-sm-6">
		                <input type="password" class="form-control" name="password" />
		            </div>
		             <label class="col-sm-3 control-label error-info" style="text-align:left;">*不可为空</label>
		        </div>
		        <div class="form-group">
		            <label class="control-label col-sm-3">确认密码</label>
		            <div class="col-sm-6">
		                <input type="password" class="form-control" name="repassword" />
		            </div>
		             <label class="col-sm-3 control-label error-info" style="text-align:left;">*不可为空</label>
		        </div>
		        <div class="form-group">
		            <div class="col-sm-6 col-sm-offset-3">
		                <div class="checkbox">
		                    <input type="checkbox" name="protocol" /> 同意相关服务条款和隐私政策
		                </div>
		            </div>
		        </div>
		    </form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关&nbsp;&nbsp;&nbsp;&nbsp;闭</button>
	        <button type="submit" class="btn btn-default">注&nbsp;&nbsp;&nbsp;&nbsp;册</button>
	      </div>
	    </div>
	  </div>
	</div>
	<!-- 注册账号modal结束 -->
  </body>
</html>