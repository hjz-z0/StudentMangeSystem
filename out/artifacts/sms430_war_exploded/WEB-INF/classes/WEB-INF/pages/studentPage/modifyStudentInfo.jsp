<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 2020/5/5
  Time: 17:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <link rel="stylesheet" href="<%=basePath%>/css/style.css" type="text/css"/>
    <script type="text/javascript">
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


            $("#modifyMyInfo").click(function(){
                var name = $("#modify_name").val();
                var age=$("#modify_age").val();
                if (name==null||name==""){
                    alert("姓名不能为空！");
                    return false;
                }else if(age==""||age==null){
                    alert("年龄不能为空！");
                    return false;
                }else {
                    return true;
                }
            })
        })
    </script>
</head>
<body>
<% request.setAttribute("index",3); %>
<jsp:include page="top1.jsp"/>
<div class="container margin-top-10">
    <div class="col-sm-8">
        <div class="panel panel-default">
            <div class="panel-body">
                <form action="<%=basePath%>/student/modifyMyInfo.do" class="form-horizontal" method="post">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">学生编号：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text" value="${student.sid}" readonly="readonly">
                        </div>
                        <label class="col-sm-4 control-label error-info" style="text-align:left;">*不可修改</label>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">登录账户：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text" name="username" value="${student.username}" readonly="readonly">
                        </div>
                        <label class="col-sm-4 control-label error-info" style="text-align:left;">*不可修改</label>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">学生姓名：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text" id="modify_name" value="${student.name}" name="name">
                        </div>
                        <label class="col-sm-4 control-label error-info" style="text-align:left;">*不能为空</label>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text" id="modify_age" value="${student.age}" name="age">
                        </div>
                        <label class="col-sm-4 control-label error-info" style="text-align:left;">*不能为空</label>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</label>
                        <div class="col-sm-6">
                            <div class="radio">

                                    <c:if test="${student.gender==0}">
                                        <label>
                                        <input type="radio" value="male" name="sex" checked="checked"> 男
                                        </label>
                                        &nbsp;&nbsp;&nbsp;
                                        <label>
                                        <input type="radio" value="female" name="sex"> 女
                                        </label>
                                    </c:if>
                                <c:if test="${student.gender==1}">
                                    <label>
                                        <input type="radio" value="male" name="sex"> 男
                                    </label>
                                    &nbsp;&nbsp;&nbsp;
                                    <label>
                                        <input type="radio" value="female" name="sex" checked="checked"> 女
                                    </label>
                                </c:if>
                            </div>
                        </div>
                        <label class="col-sm-4 control-label error-info" style="text-align:left;"></label>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">班&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text" value="${student.grade.gname}" readonly="readonly">
                        </div>
                        <label class="col-sm-4 control-label error-info" style="text-align:left;">*不可修改</label>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">课&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;程：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text" value="${student.course.cname}" readonly="readonly">
                        </div>
                        <label class="col-sm-4 control-label error-info" style="text-align:left;">*不可修改</label>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-6  col-sm-offset-2">
                            <div class="col-sm-6">
                                <button type="reset" class="btn btn-primary btn-block">重&nbsp;&nbsp;置</button>
                            </div>
                            <div class="col-sm-6">
                                <button type="submit" id="modifyMyInfo" class="btn btn-primary btn-block">修&nbsp;&nbsp;改</button>
                            </div>
                        </div>
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
