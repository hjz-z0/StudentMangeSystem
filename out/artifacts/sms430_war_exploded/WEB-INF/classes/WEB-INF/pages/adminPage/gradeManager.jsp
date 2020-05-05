<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 2020/5/2
  Time: 23:27
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

    <script>
        function initTable() {
            //alert(1);
            //先销毁表格
            $('#cusTable').bootstrapTable('destroy');
            //初始化表格,动态从服务器加载数据
            $("#cusTable").bootstrapTable({
                method: 'post',
                contentType: "application/x-www-form-urlencoded",//post请求必须要有！
                url:"<%=basePath%>/class/findAllByPage.do",//要请求数据的文件路径
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
                        title:'班级名称',
                        field:'gname',
                        align:'center'

                    },
                    {
                        title:'班级详情',
                        field:'gdesc',
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
                        field:'gid',
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
            result+="<a class=\"btn btn-success btn-xs\" onclick=\"showModalKaiqi("+id+")\">启用</a>&nbsp;&nbsp;&nbsp;";
            result+="<a class=\"btn btn-info btn-xs\" onclick=\"showModalModify("+id+")\">修改</a>&nbsp;&nbsp;&nbsp;";
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

            $('#frmAddGrade').bootstrapValidator({
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',//成功后输出的图标
                    invalid: 'glyphicon glyphicon-remove',//失败后输出的图标
                    validating: 'glyphicon glyphicon-refresh'//长时间加载时输出的图标
                },
                fields:{
                    gname: {//表单元素name的值
                        validators: {//验证规则
                            notEmpty: {
                                message: '班级名不能为空'//验证失败输出的值
                            },
                            stringLength:{
                                min:3,
                                max:10,
                                message:'班级长度必须3-10位'

                            },
                            regexp:{
                                regexp:/^[0-9a-zA-Z]+$/,
                                message:'班级只能包含数字、字母'
                            },
                            remote:{//向远程服务器发送请求进行校验
                                type:'post',
                                url:'<%=basePath%>/class/findByGname.do',
                                //message:'用户名已经被占用'
                            }

                        }
                    },
                    gdesc:{
                        validators:{
                            notEmpty:{
                                message:'班级详情不能为空'
                            }
                        }
                    }
                }
            });

        });

        //显示修改模态对话框
        function showModalModify(gid){
            //alert(sid);
            $.post('<%=basePath%>/class/findById.do',{'gid':gid},function(grade){
                console.log(grade); //注意：post需要传递第四个参数，值为json,表示返回的值是一个json字符串，否则就是一个普通字符串
                $('#gid').val(grade.gid);
                $('#qname').val(grade.gname);
                $('#gdesc').val(grade.gdesc);
                $('#state').val(grade.state);
                $('#modifyGrade').modal('show');
            },'json');
        }

        //显示删除确认框
        function showModalDelete(gid){
            //alert(sid);
            //将sid赋值给隐藏表单域
            $('#del_id').val(gid);
            //显示删除确认框
            $('#deleteGrade').modal('show');

        }

        //删除学员
        function deleteGrade(){
            //alert(1);
            //从隐藏表单域取出sid值
            let gid=$('#del_id').val();
            location.href='<%=basePath%>/class/deleteGrade.do?gid='+gid;
        }

        //显示开启确认框
        function showModalKaiqi(gid){
            //alert(sid);
            //将sid赋值给隐藏表单域
            $('#kaiqi_id').val(gid);
            //显示删除确认框
            $('#kaiqiGrade').modal('show');

        }

        //开启确认
        function kaiqiGrade(){
            //alert(1);
            //从隐藏表单域取出sid值
            let gid=$('#kaiqi_id').val();
            location.href='<%=basePath%>/class/kaiqiGrade.do?gid='+gid;
        }
        function queryByCondition(){

            //初始化表格
            initTableByConditon();

        }

        function initTableByConditon(){
            //先销毁表格
            $('#cusTable').bootstrapTable('destroy');
            //初始化表格,动态从服务器加载数据
            //alert(1);
            $("#cusTable").bootstrapTable({
                method: 'post',
                contentType: "application/x-www-form-urlencoded",//post请求必须要有！
                url:"<%=basePath%>/class/findByCondition.do",//要请求数据的文件路径
                striped: true, //是否显示行间隔色
                pageNumber: 1, //初始化加载第一页，默认第一页
                pagination:true,//是否分页
                queryParamsType:'limit',//查询参数组织方式
                queryParams:queryParamsByCondition,//请求服务器时所传的参数
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
                        title:'班级名称',
                        field:'gname',
                        align:'center'

                    },
                    {
                        title:'班级详情',
                        field:'gdesc',
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
                        field:'gid',
                        align:'center',
                        formatter:actionFormatter

                    }
                ]
            });
        }

        function queryParamsByCondition(params){
            return{
                //页码
                pageNo: (params.offset / params.limit) + 1,
                //页面大小
                pageSize:params.limit,
                //查询表单元素中的值
                gname:$('#gname').val()
            }
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
<% request.setAttribute("index",2); %>
<jsp:include page="top.jsp"/>
<div class="container margin-top-10">
    <div class="col-sm-8">
        <div class="panel panel-default">
            <div class="panel-body">
                <form class="form-inline" method="post">
                    <div class="form-group">
                        <label>班级:</label>
                        <input type="text" id="gname"  name="gname" class="form-control input-sm" placeholder="不填查询所有">
                    </div>
                    <button type="reset" class="btn btn-success btn-sm">重&nbsp;&nbsp;置</button>
                    <button type="button" class="btn btn-success btn-sm" onclick="queryByCondition()">查&nbsp;&nbsp;询</button>
                </form>
                <hr/>
                <div class="padding-bottom-3">
                    <a class="btn btn-success btn-sm" data-toggle="modal" data-target="#addGrade" style="display:inline-block;float:right;">添加新班级</a>
                </div>
                <table id="cusTable" data-toggle="tooltip">

                </table>
            </div>
        </div>
    </div>
    <jsp:include page="right.jsp"/>
</div>
<jsp:include page="bottom.jsp"/>

<!-- 班级修改model窗口 -->
<div class="modal fade" id="modifyGrade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">班级信息修改</h4>
            </div>
            <form action="<%=basePath%>/class/modifyGrade.do" class="form-horizontal" method="post">
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">班级编号：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text" id="gid" name="gid" readonly="readonly">
                        </div>
                        <label class="col-sm-3 control-label error-info" style="text-align:left;">*不可修改</label>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">班级名称：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text" id="qname" name="gname">
                        </div>
                        <label class="col-sm-3 control-label error-info" style="text-align:left;">*不能为空</label>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">班级详情：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text" id="gdesc" name="gdesc">
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
<!-- 班级添加model窗口 -->
<div class="modal fade" id="addGrade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">新班级添加</h4>
            </div>
            <form action="<%=basePath%>/class/addGrade.do" id="frmAddGrade" class="form-horizontal" method="post">
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">班级名称：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text" name="gname">
                        </div>
                        <label class="col-sm-3 control-label error-info" style="text-align:left;">*不能为空</label>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">班级详情：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text" name="gdesc">
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
<!-- MODEL结束 -->
<div class="modal fade" id="deleteGrade">
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
                <button type="button" class="btn btn-primary" onclick="deleteGrade()">确认</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>

<div class="modal fade" id="kaiqiGrade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">操作提示</h4>
            </div>
            <input type="hidden" id="kaiqi_id"/>
            <div class="modal-body">
                <p style="font-size: 25px;font-style: bold;">确认要开启吗？</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="kaiqiGrade()">确认</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
</body>
</html>

