<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 2020/5/4
  Time: 17:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>学生管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="<%=basePath%>/bootstrap/css/bootstrap.min.css" type="text/css"></link>
    <link rel="stylesheet" href="<%=basePath%>/css/mycss.css" type="text/css"></link>
    <script type="text/javascript" src="<%=basePath%>/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/bootstrap/css/bootstrap-table.css"/>
    <script type="text/javascript" src="<%=basePath%>/bootstrap/js/bootstrap-table.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/bootstrap/css/bootstrapValidator.min.css" type="text/css">
    <script type="text/javascript" src="<%=basePath%>/bootstrap/js/bootstrapValidator.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ckeditor/ckeditor.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/css/style.css" type="text/css"/>
</head>
<script type="text/javascript">
    //修改个人信息
    function modifyUSER(){
        //alert(1);
        //获取修改窗口中的值
        let id='${user.id}';
        let username=$('#username').val();
        //console.log('id='+id+',oldPass='+oldPass+',newPass='+newPass);
        let params={"id":id,"username":username};
        $.post('<%=basePath%>/sysuser/modifyUSER.do',params,function(data){
            if(data=='success'){
                alert('修改信息成功');
                //返回登录页面继续登录
                window.location='<%=basePath%>/login.jsp';
            }
            else{
                alert('修改信息失败');
                //清空表单数据
                $('#modFrm2')[0].reset();


            }

        });



    }
    //修改密码
    function modifyPwd(){
        //alert(1);
        //获取修改窗口中的值
        let id='${user.id}';
        let oldPass=$('#oldPass').val();
        let newPass=$('#newPass').val();
        //console.log('id='+id+',oldPass='+oldPass+',newPass='+newPass);
        let params={"id":id,"oldPass":oldPass,"newPass":newPass};
        $.post('<%=basePath%>/sysuser/modifyPwd.do',params,function(data){
            if(data=='success'){
                alert('修改密码成功');
                //返回登录页面继续登录
                window.location='<%=basePath%>/login.jsp';
            }
            else{
                alert('修改密码失败');
                //清空表单数据
                $('#modFrm')[0].reset();


            }

        });



    }

    //退出系统
    function logOut(){
        $.ajax({
            method: 'post',
            url:'<%=basePath%>/sysuser/logOut.do',
            success:function(){
                alert('你已退出系统');
                //重新定位到login.jsp,继续登录
                location='<%=basePath%>/login.jsp';
            }
        });
    }

    $(function () {
        $('#modify').click(function () {
            var title = $('#title').val();
            var text1= $('#content').val();
            if (title.trim()==""){
                alert("请输入标题内容！");
                return false;
            }else if(text1.trim()==""){
                alert("请输入正文内容！");
                return false;
            }else {
                return true;
            }

        })
    })
</script>
<body>
<% request.setAttribute("index",1); %>
<jsp:include page="top1.jsp"/>
<div class="container margin-top-10">
    <div class="col-sm-8">
        <div class="panel panel-default">
            <div class="panel-body">
                <form action="<%=basePath%>/blog/modifyBlog.do" method="post">
                    <div class="form-group">
                        <label>日志编号：</label>
                        <input type="text" class="form-control" readonly="readonly" name="bid" value="${blog.bid}">
                    </div>
                    <div class="form-group">
                        <label>日志标题：</label><label class="control-label error-info" style="text-align:left;">*不能为空</label>
                        <input type="text" class="form-control" name="title" id="title" value="${blog.title}">
                    </div>
                    <div class="form-group">
                        <label>日志正文：</label><label class="control-label error-info" style="text-align:left;">*不能为空</label>
                        <textarea class="ckeditor" rows="10" name="content" id="content" style="resize:none;">${blog.content}
				    </textarea>
                    </div>
                    <div style="text-align: right;">
                        <button type="submit" class="btn btn-success" id="modify">更新</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <jsp:include page="right1.jsp"/>
</div>
<jsp:include page="bottom1.jsp"/>
</body>
</html>
