<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 2020/5/2
  Time: 23:52
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

<script type="text/javascript">
    function initTable() {
        //alert(1);
        //先销毁表格
        $('#cusTable').bootstrapTable('destroy');
        //初始化表格,动态从服务器加载数据
        $("#cusTable").bootstrapTable({
            method: 'post',
            contentType: "application/x-www-form-urlencoded",//post请求必须要有！
            url:"<%=basePath%>/blog/findAllByPage.do",//要请求数据的文件路径
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
                    title:'日志标题',
                    field:'title',
                    align:'center'

                },
                {
                    title:'更新时间',
                    field:'createDate',
                    align:'center'

                },
                {
                    title:'作者',
                    field:'student',
                    align:'center',
                    formatter:function(value, row, index){
                        //通过formatter属性自定义显示
                        //value:该行的属性
                        //row:该行记录
                        //index:该行下标
                        return value.name;

                    }
                },
                {
                    title:'操作',
                    field:'bid',
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
        result+="<a class=\"btn btn-info btn-xs\" onclick=\"showDetail("+id+")\">查看详情</a>&nbsp;&nbsp;&nbsp;";
        result+="<a class=\"btn btn-danger btn-xs\" onclick=\"showModalDelete("+id+")\">删除</a>";
        return result;


    }
    //跳转到详情页面
    function showDetail(bid){
        location.href='<%=basePath%>/blog/toBlogDetail.do?bid='+bid;

    }

    //显示删除确认框
    function showModalDelete(bid){
        //alert(sid);
        //将sid赋值给隐藏表单域
        $('#del_id').val(bid);
        //显示删除确认框
        $('#deleteBlog').modal('show');

    }

    //删除学员
    function deleteBlog(){
        //alert(1);
        //从隐藏表单域取出sid值
        let bid=$('#del_id').val();
        location.href='<%=basePath%>/blog/deleteBlog.do?bid='+bid;
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
    });

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
<% request.setAttribute("index",4); %>
<jsp:include page="top.jsp"/>
<div class="container margin-top-10">
    <div class="col-sm-8">
        <div class="panel panel-default">
            <div class="panel-body">
                <table id="cusTable">

                </table>
            </div>
        </div>
    </div>
    <jsp:include page="right.jsp"/>
</div>
<jsp:include page="bottom.jsp"/>


<!-- MODEL结束 -->
<div class="modal fade" id="deleteBlog">
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
                <button type="button" class="btn btn-primary" onclick="deleteBlog()">确认</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
</body>
</html>
