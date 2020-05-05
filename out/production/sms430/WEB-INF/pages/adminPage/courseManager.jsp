<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 2020/5/2
  Time: 23:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>学生管理系统</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="<%=basePath%>/bootstrap/css/bootstrap.min.css" type="text/css"></link>
    <link rel="stylesheet" href="<%=basePath%>/css/mycss.css" type="text/css"></link>
    <script type="text/javascript" src="<%=basePath%>/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/bootstrap/css/bootstrap-table.css"/>
    <script type="text/javascript" src="<%=basePath%>/bootstrap/js/bootstrap-table.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/bootstrap/css/bootstrapValidator.min.css" type="text/css">
    <script type="text/javascript" src="<%=basePath%>/bootstrap/js/bootstrapValidator.min.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/css/style.css" type="text/css"/>

<script>
    function initTable() {
        //alert(1);
        //先销毁表格
        $('#cusTable').bootstrapTable('destroy');
        //初始化表格,动态从服务器加载数据
        $("#cusTable").bootstrapTable({
            method: 'post',
            contentType: "application/x-www-form-urlencoded",//post请求必须要有！
            url:"<%=basePath%>/course/findAllByPage.do",//要请求数据的文件路径
            striped: true, //是否显示行间隔色
            pageNumber: 1, //初始化加载第一页，默认第一页
            pagination:true,//是否分页
            queryParamsType:'limit',//查询参数组织方式
            queryParams:queryParams,//请求服务器时所传的参数
            sidePagination:'server',//指定服务器端分页
            pageSize:5,//单页记录数
            pageList:[5,10,20,30],//分页步进值
            showRefresh:false,//刷新按钮
            showColumns:false,
            clickToSelect: true,//是否启用点击选中行
            toolbarAlign:'right',//工具栏对齐方式
            buttonsAlign:'right',//按钮对齐方式
            undefinedText: "空",//当数据为 undefined 时显示的字符
            columns:[
                {
                    title:'全选',
                    field:'select',
                    //复选框
                    checkbox:true,
                    width:25,
                    align:'center',
                    valign:'middle'
                },
                {
                    title:'课程名称',
                    field:'cname',
                    align:'center'

                },
                {
                    title:'课程描述',
                    field:'cdesc',
                    align:'center'

                },
                {
                    title:'状态',
                    field:'state',
                    align:'center',
                    formatter:function(value,row,index){
                        return row.state==1?'启用':'禁用';

                    }
                },
                {
                    title:'操作',
                    field:'cid',
                    align:'center',
                    formatter:actionFormatter

                }
            ]
        });
    }
    //操作栏的格式化
    function actionFormatter(value,row,index){
        let id=value;
        let result="";
        result+="<a class=\"btn btn-success btn-xs\" onclick=\"showModalModify("+id+")\">修改</a>";
        result+="<a class=\"btn btn-danger btn-xs\" onclick=\"showModalDelete("+id+")\">删除</a>";
        return result;


    }
    //请求服务数据时所传参数
    function queryParams(params){
        return{
            //页码
            pageNo: (params.offset / params.limit) + 1,
            //页面大小
            pageSize:params.limit
        }
    }

    $(document).ready(function () {
        //调用函数，初始化表格
        initTable();
        //获取服务器端传递过来的msg
        let msg='${msg}';
        if(msg!=''){
            //在这个表格上初始化一个弹出栏
            $('#cusTable').tooltip({
                title:'提示消息',
                template:'<div class="tooltip tooltipMsg">'+msg+'</div>',
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

        $('#frmAddCourse').bootstrapValidator({
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',//成功后输出的图标
                invalid: 'glyphicon glyphicon-remove',//失败后输出的图标
                validating: 'glyphicon glyphicon-refresh'//长时间加载时输出的图标
            },
            fields:{
                cname: {//表单元素name的值
                    validators: {//验证规则
                        notEmpty: {
                            message: '课程名不能为空'//验证失败输出的值
                        },
                        stringLength:{
                            min:3,
                            max:10,
                            message:'课程名长度必须3-10位'

                        },
                        regexp:{
                            regexp:/^[0-9a-zA-Z_\u4e00-\u9fa5]+$/,
                            message:'课程名只能包含数字、字母_'
                        },
                        remote:{//向远程服务器发送请求进行校验
                            type:'post',
                            url:'<%=basePath%>/course/findByCname.do',
                            //message:'用户名已经被占用'
                        }

                    }
                },
                cdesc:{
                    validators:{
                        notEmpty:{
                            message:'课程不能为空'
                        }
                    }
                }
            }
        });
    })

    //显示修改模态对话框
    function showModalModify(cid){
        //alert(sid);
        $.post('<%=basePath%>/course/findById.do',{'cid':cid},function(course){
            console.log(course); //注意：post需要传递第四个参数，值为json,表示返回的值是一个json字符串，否则就是一个普通字符串
            $('#cid').val(course.cid);
            $('#cname').val(course.cname);
            $('#cdesc').val(course.cdesc);
            $('#state').val(course.state);
            $('#modifyCourse').modal('show');
        },'json');
    }

    //显示删除确认框
    function showModalDelete(cid){
        //alert(sid);
        //将sid赋值给隐藏表单域
        $('#del_id').val(cid);
        //显示删除确认框
        $('#deleteCourse').modal('show');

    }

    //删除学员
    function deleteCourse(){
        //alert(1);
        //从隐藏表单域取出sid值
        let cid=$('#del_id').val();
        location.href='<%=basePath%>/course/deleteCourse.do?cid='+cid;
    }
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


</script>
</head>
<body>
<% request.setAttribute("index",3); %>
<jsp:include page="top.jsp"/>
<div class="container margin-top-10">
    <div class="col-sm-8">
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="padding-bottom-3">
                    <a class="btn btn-success btn-sm" data-toggle="modal" data-target="#addCourse" style="display:inline-block;float:right;">添加新课程</a>
                </div>
                <table id="cusTable" data-toggle="tooltip">

                </table>
            </div>
        </div>
    </div>
    <jsp:include page="right.jsp"/>
</div>
<jsp:include page="bottom.jsp"/>
<!-- MODEL结束 -->
<!-- 课程修改model窗口 -->
<div class="modal fade" id="modifyCourse" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">课程信息修改</h4>
            </div>
            <form action="<%=basePath%>/course/modifyCourse.do" class="form-horizontal" method="post">
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">课程编号：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text" id="cid" name="cid" readonly="readonly">
                        </div>
                        <label class="col-sm-3 control-label error-info" style="text-align:left;">*不可修改</label>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">课程名称：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text" id="cname" name="cname">
                        </div>
                        <label class="col-sm-3 control-label error-info" style="text-align:left;">*不能为空</label>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">课程描述：</label>
                        <div class="col-sm-6">
			         <textarea class="form-control" rows="10" style="resize:none;" id="cdesc" name="cdesc">
			         </textarea>
                        </div>
                        <label class="col-sm-3 control-label error-info" style="text-align:left;">*不能为空</label>
                    </div>
                    <input type="hidden" name="state" id="state">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" aria-label="Close">关&nbsp;&nbsp;闭</button>
                    <button type="reset" class="btn btn-default">重&nbsp;&nbsp;置</button>
                    <button type="submit" class="btn btn-default">修&nbsp;&nbsp;改</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- MODEL结束 -->
<!-- 课程添加model窗口 -->
<div class="modal fade" id="addCourse" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">新课程添加</h4>
            </div>
            <form action="<%=basePath%>/course/addCourse.do" id="frmAddCourse" class="form-horizontal" method="post">
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">课程名称：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text" name="cname">
                        </div>
                        <label class="col-sm-3 control-label error-info" style="text-align:left;">*不能为空</label>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">课程描述：</label>
                        <div class="col-sm-6">
                            <textarea class="form-control" rows="10" name="cdesc" style="resize:none;"></textarea>
                        </div>
                        <label class="col-sm-3 control-label error-info" style="text-align:left;">*不能为空</label>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" aria-label="Close">关&nbsp;&nbsp;闭</button>
                    <button type="reset" class="btn btn-default">重&nbsp;&nbsp;置</button>
                    <button type="submit" class="btn btn-default">添&nbsp;&nbsp;加</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="deleteCourse">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">操作提示</h4>
            </div>
            <input type="hidden" id="del_id"/>
            <div class="modal-body">
                <p style="font-size: 25px;font-style: bold;">确认要删除吗？</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="deleteCourse()">确认</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- MODEL结束 -->
</body>
</html>

